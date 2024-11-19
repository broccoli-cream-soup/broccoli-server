package dev.jombi.template.core.survey.repository

import dev.jombi.template.core.survey.entity.consts.SurveyType

interface SurveyInfo {
    val id: String
    val author: String

    val name: String
    val description: String

    val surveyType: SurveyType
}
