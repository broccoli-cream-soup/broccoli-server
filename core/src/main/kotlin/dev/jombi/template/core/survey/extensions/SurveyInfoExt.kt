package dev.jombi.template.core.survey.extensions

import dev.jombi.template.business.survey.dto.SurveyInfoDto
import dev.jombi.template.core.survey.repository.SurveyInfo

fun SurveyInfo.toDto() = SurveyInfoDto(
    id = id,
    name = name,
    author = author,
    description = description,
    surveyType = surveyType.toDto(),
)
