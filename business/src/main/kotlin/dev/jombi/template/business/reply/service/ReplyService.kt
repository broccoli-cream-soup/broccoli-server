package dev.jombi.template.business.reply.service

import dev.jombi.template.business.reply.dto.AnswerDto
import dev.jombi.template.business.reply.dto.ReplyCreateDto
import dev.jombi.template.business.reply.dto.ReplyDto

interface ReplyService {
    fun createReply(dto: ReplyCreateDto): ReplyDto
    fun getReplyByMe(): List<ReplyDto> // requires authorize
//    fun getReplyById(replyId: String): ReplyDto // create or not
    fun editReply(replyId: String, answerDto: AnswerDto): ReplyDto
}
