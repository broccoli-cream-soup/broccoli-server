package soup.cream.broccoli.survey.core.survey.extensions

import soup.cream.broccoli.survey.business.survey.dto.SurveyInfoDto
import soup.cream.broccoli.survey.core.survey.repository.SurveyInfo

fun SurveyInfo.toDto(authorName: String) = SurveyInfoDto(
    id = id,
    name = name,
    author = authorName,
    description = description,
    surveyType = surveyType.toDto(),
)
