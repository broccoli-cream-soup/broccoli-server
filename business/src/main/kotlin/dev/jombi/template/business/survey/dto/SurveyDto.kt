package dev.jombi.template.business.survey.dto

data class SurveyDto(
    val id: String,
    val author: String,

    val name: String,
    val description: String,

    val questions: List<QuestionDto>,
    val surveyType: SurveyTypeDto = SurveyTypeDto.ANONYMOUS,
)
