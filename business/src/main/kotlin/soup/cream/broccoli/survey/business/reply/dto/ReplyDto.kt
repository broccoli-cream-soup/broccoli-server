package soup.cream.broccoli.survey.business.reply.dto

data class ReplyDto(
    val id: String,
    val surveyId: String,
    val userId: Long?,
    val answers: Set<AnswerDto>,
)
