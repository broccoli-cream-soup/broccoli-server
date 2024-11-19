package dev.jombi.template.core.reply.service

import dev.jombi.template.business.reply.dto.AnswerDto
import dev.jombi.template.business.reply.dto.ReplyCreateDto
import dev.jombi.template.business.reply.dto.ReplyDto
import dev.jombi.template.business.reply.service.ReplyService
import dev.jombi.template.common.exception.CustomException
import dev.jombi.template.core.member.MemberHolder
import dev.jombi.template.core.reply.entity.Answer
import dev.jombi.template.core.reply.entity.Reply
import dev.jombi.template.core.reply.exception.ReplyExceptionDetails
import dev.jombi.template.core.reply.extensions.create
import dev.jombi.template.core.reply.extensions.from
import dev.jombi.template.core.reply.extensions.guessType
import dev.jombi.template.core.reply.extensions.toDto
import dev.jombi.template.core.reply.repository.ReplyRepository
import dev.jombi.template.core.survey.entity.consts.SurveyType
import dev.jombi.template.core.survey.extensions.guessType
import dev.jombi.template.core.survey.service.SurveyRepositoryDelegate
import org.bson.types.ObjectId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class ReplyServiceImpl(
    private val replyRepository: ReplyRepository,
    private val surveyDelegate: SurveyRepositoryDelegate,
    private val holder: MemberHolder,
) : ReplyService {
    @Transactional(rollbackFor = [Exception::class])
    override fun createReply(dto: ReplyCreateDto): ReplyDto {
        val me = holder.getOrNull()

        val survey = surveyDelegate.findById(dto.surveyId)

        // VALIDATE: Auth state
        when (survey.surveyType) {
            SurveyType.ANONYMOUS -> {
                // ignore
            }

            SurveyType.AUTHENTICATED -> me
                ?: throw CustomException(ReplyExceptionDetails.REPLY_NOT_ACCEPTABLE_ANONYMOUS)

            SurveyType.AUTHENTICATED_ONCE -> {
                me ?: throw CustomException(ReplyExceptionDetails.REPLY_NOT_ACCEPTABLE_ANONYMOUS)
                val alreadyReplied = replyRepository.existsReplyByUserIdAndSurveyId(me.id.id, survey.id)
                if (alreadyReplied)
                    throw CustomException(ReplyExceptionDetails.REPLY_NOT_ACCEPTABLE_DUPLICATE)
            }
        }
        // VALIDATE END

        val created = Reply.create(dto, me?.id?.id)

        // VALIDATE: each answers
        val idMap = survey.questions.associateBy { it.id }.toMutableMap()

        for (answer in created.answers) {
            if (answer.questionId !in idMap)
                throw CustomException(ReplyExceptionDetails.REPLY_NOT_ACCEPTABLE_ID_NOT_FOUND, answer.questionId)

            val question = idMap[answer.questionId]!!
            if (question.guessType() != answer.guessType())
                throw CustomException(
                    ReplyExceptionDetails.REPLY_NOT_ACCEPTABLE_TYPE_MISMATCH,
                    question.id,
                    answer.guessType(),
                    question.guessType()
                )

            if (!answer.validate(question))
                throw CustomException(ReplyExceptionDetails.REPLY_NOT_ACCEPTABLE, question.id)

            idMap.remove(answer.questionId) // validate finish
        }

        val requiredQuestions = idMap.filter { it.value.required }
        if (requiredQuestions.isNotEmpty()) {
            throw CustomException(
                ReplyExceptionDetails.REPLY_NOT_ACCEPTABLE_UNRESOLVED,
                requiredQuestions.map { "'${it.value.id}'" }.joinToString(", ")
            )
        }
        // VALIDATE END

        val saved = replyRepository.save(created)

        return saved.toDto()
    }

    override fun getReplyByMe(): List<ReplyDto> {
        val me = holder.get()
        val found = replyRepository.findRepliesByUserIdIs(me.id.id)

        return found.map { it.toDto() }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun editReply(replyId: String, answerDto: AnswerDto): ReplyDto {
        val found = replyRepository.findByIdOrNull(ObjectId(replyId))
            ?: throw CustomException(ReplyExceptionDetails.REPLY_NOT_FOUND)

        if (found.userId != holder.get().id.id)
            throw CustomException(ReplyExceptionDetails.NOT_YOUR_REPLY)

        val modified = (found.answers.filter {
            it.questionId.toString() != answerDto.questionId
        } + Answer.from(answerDto)).toSet()

        val final = replyRepository.save(found.copy(answers = modified))
        return final.toDto()
    }
}
