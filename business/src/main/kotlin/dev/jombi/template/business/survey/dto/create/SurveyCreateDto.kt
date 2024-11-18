package dev.jombi.template.business.survey.dto.create

import dev.jombi.template.business.survey.dto.SurveyTypeDto

data class SurveyCreateDto(
    val name: String,
    val description: String,

    val surveyType: SurveyTypeDto = SurveyTypeDto.ANONYMOUS,
)
