package soup.cream.broccoli.survey.core.reply.service

import soup.cream.broccoli.survey.business.reply.dto.AnswerDto
import soup.cream.broccoli.survey.business.reply.dto.ReplyCreateDto
import soup.cream.broccoli.survey.business.reply.dto.ReplyDto
import soup.cream.broccoli.survey.business.reply.service.ReplyService
import soup.cream.broccoli.survey.common.exception.CustomException
import soup.cream.broccoli.survey.core.member.MemberHolder
import soup.cream.broccoli.survey.core.reply.entity.Answer
import soup.cream.broccoli.survey.core.reply.entity.Reply
import soup.cream.broccoli.survey.core.reply.exception.ReplyExceptionDetails
import soup.cream.broccoli.survey.core.reply.extensions.create
import soup.cream.broccoli.survey.core.reply.extensions.from
import soup.cream.broccoli.survey.core.reply.extensions.guessType
import soup.cream.broccoli.survey.core.reply.extensions.toDto
import soup.cream.broccoli.survey.core.reply.repository.ReplyRepository
import soup.cream.broccoli.survey.core.survey.entity.consts.SurveyType
import soup.cream.broccoli.survey.core.survey.extensions.guessType
import soup.cream.broccoli.survey.core.survey.service.SurveyRepositoryDelegate
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
                val alreadyReplied = replyRepository.existsReplyByUserIdAndSurveyId(me.id.get, survey.id)
                if (alreadyReplied)
                    throw CustomException(ReplyExceptionDetails.REPLY_NOT_ACCEPTABLE_DUPLICATE)
            }
        }
        // VALIDATE END

        val created = Reply.create(dto, me?.id?.get)

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
        val found = replyRepository.findRepliesByUserIdIs(me.id.get)

        return found.map { it.toDto() }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun editReply(replyId: String, answerDto: AnswerDto): ReplyDto {
        val found = replyRepository.findByIdOrNull(ObjectId(replyId))
            ?: throw CustomException(ReplyExceptionDetails.REPLY_NOT_FOUND)

        if (found.userId != holder.get().id.get)
            throw CustomException(ReplyExceptionDetails.NOT_YOUR_REPLY)

        val modified = (found.answers.filter {
            it.questionId.toString() != answerDto.questionId
        } + Answer.from(answerDto)).toSet()

        val final = replyRepository.save(found.copy(answers = modified))
        return final.toDto()
    }
}
