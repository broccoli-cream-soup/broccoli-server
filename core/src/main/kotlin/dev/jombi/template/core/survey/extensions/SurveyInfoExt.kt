package dev.jombi.template.core.survey.extensions

import dev.jombi.template.business.survey.dto.SurveyInfoDto
import dev.jombi.template.core.survey.repository.SurveyInfo

fun SurveyInfo.toDto(authorName: String) = SurveyInfoDto(
    id = id,
    name = name,
    author = authorName,
    description = description,
    surveyType = surveyType.toDto(),
)
