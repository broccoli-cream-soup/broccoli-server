package dev.jombi.template.business.survey.dto

data class SurveyEditDto(
    val name: String?,
    val description: String?,
    val surveyType: SurveyTypeDto?,
)
