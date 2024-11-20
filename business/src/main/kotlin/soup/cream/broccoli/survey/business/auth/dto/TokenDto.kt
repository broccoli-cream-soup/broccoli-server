package soup.cream.broccoli.survey.business.auth.dto

data class TokenDto(
    val accessToken: String,
    val refreshToken: String
)
