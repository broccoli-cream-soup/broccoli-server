package soup.cream.broccoli.survey.core.reply.extensions

import soup.cream.broccoli.survey.business.reply.dto.ReplyCreateDto
import soup.cream.broccoli.survey.business.reply.dto.ReplyDto
import soup.cream.broccoli.survey.core.reply.entity.Answer
import soup.cream.broccoli.survey.core.reply.entity.Reply
import org.bson.types.ObjectId

fun Reply.Companion.create(replyDto: ReplyCreateDto, userId: Long?) = Reply(
    userId = userId,
    surveyId = ObjectId(replyDto.surveyId),
    answers = replyDto.answers.map { Answer.from(it) }.toSet(),
    id = ObjectId.get()
)

fun Reply.toDto() = ReplyDto(
    id = id.toString(),
    userId = userId,
    surveyId = surveyId.toString(),
    answers = answers.map { it.toDto() }.toSet()
)
