package soup.cream.broccoli.survey.api.auth.dto.request

import com.fasterxml.jackson.annotation.JsonCreator

data class NewTokenRequestDto @JsonCreator constructor(
    val refreshToken: String
)
