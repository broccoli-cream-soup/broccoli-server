package soup.cream.broccoli.survey.business.reply.service

import soup.cream.broccoli.survey.business.reply.dto.AnswerDto
import soup.cream.broccoli.survey.business.reply.dto.ReplyCreateDto
import soup.cream.broccoli.survey.business.reply.dto.ReplyDto

interface ReplyService {
    fun createReply(dto: ReplyCreateDto): ReplyDto
    fun getReplyByMe(): List<ReplyDto> // requires authorize
//    fun getReplyById(replyId: String): ReplyDto // create or not
    fun editReply(replyId: String, answerDto: soup.cream.broccoli.survey.business.reply.dto.AnswerDto): ReplyDto
}
