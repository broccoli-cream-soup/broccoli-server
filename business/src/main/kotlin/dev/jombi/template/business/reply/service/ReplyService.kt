package dev.jombi.template.business.reply.service

import dev.jombi.template.business.reply.dto.AnswerDto
import dev.jombi.template.business.reply.dto.ReplyCreateDto
import dev.jombi.template.business.reply.dto.ReplyDto

interface ReplyService {
    fun createReply(dto: ReplyCreateDto): ReplyDto
    fun getReplyByMe(): List<ReplyDto> // requires authorize
//    fun getReplyById(surveyId: String, replyId: String): ReplyDto // create or not
    fun editReply(surveyId: String, replyId: String, answerDto: AnswerDto): ReplyDto
}
