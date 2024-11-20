package soup.cream.broccoli.survey.common.response

sealed interface BaseResponse {
    val code: String
    val status: Int
}
