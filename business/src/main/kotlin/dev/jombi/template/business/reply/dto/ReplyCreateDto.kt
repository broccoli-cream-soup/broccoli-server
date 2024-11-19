package dev.jombi.template.business.reply.dto

data class ReplyCreateDto(
    val surveyId: String,
    val answers: Set<AnswerDto>,
)
