package dev.jombi.template.business.survey.dto.create

import dev.jombi.template.business.survey.dto.SurveyTypeDto
import jakarta.validation.constraints.NotNull

data class SurveyCreateDto(
    @field:NotNull
    val name: String,
    val description: String,

    @field:NotNull
    val surveyType: SurveyTypeDto = SurveyTypeDto.ANONYMOUS,
)
