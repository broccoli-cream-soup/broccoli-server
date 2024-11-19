package dev.jombi.template.core.survey.repository

import dev.jombi.template.core.survey.entity.consts.SurveyType
import org.springframework.beans.factory.annotation.Value

interface SurveyInfo {
    @get:Value("#{target.id.toString()}")
    val id: String
    val authorId: Long

    val name: String
    val description: String

    val surveyType: SurveyType
}
