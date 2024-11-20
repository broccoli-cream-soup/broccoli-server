package soup.cream.broccoli.survey.business.reply.dto

data class ReplyCreateDto(
    val surveyId: String,
    val answers: Set<soup.cream.broccoli.survey.business.reply.dto.AnswerDto>,
)
