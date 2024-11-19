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
import dev.jombi.template.core.reply.extensions.toDto
import dev.jombi.template.core.reply.repository.ReplyRepository
import org.bson.types.ObjectId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ReplyServiceImpl(
    private val replyRepository: ReplyRepository,
    private val holder: MemberHolder,
) : ReplyService {
    override fun createReply(dto: ReplyCreateDto): ReplyDto {
        val me = holder.getOrNull()
        val created = Reply.create(dto, me?.id?.id)

        // TODO: Validation

        val saved = replyRepository.save(created)

        return saved.toDto()
    }

    override fun getReplyByMe(): List<ReplyDto> {
        val me = holder.get()
        val found = replyRepository.findRepliesByUserIdIs(me.id.id)

        return found.map { it.toDto() }
    }

    override fun editReply(surveyId: String, replyId: String, answerDto: AnswerDto): ReplyDto {
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
