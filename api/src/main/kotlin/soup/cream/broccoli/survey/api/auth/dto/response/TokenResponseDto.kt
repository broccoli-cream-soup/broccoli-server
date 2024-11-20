package soup.cream.broccoli.survey.api.auth.dto.response

data class TokenResponseDto(
    val accessToken: String,
    val refreshToken: String
)
