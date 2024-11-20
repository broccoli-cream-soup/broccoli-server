package soup.cream.broccoli.survey.core.survey.repository

import soup.cream.broccoli.survey.core.survey.entity.consts.SurveyType
import org.springframework.beans.factory.annotation.Value

interface SurveyInfo {
    @get:Value("#{target.id.toString()}")
    val id: String
    val authorId: Long

    val name: String
    val description: String

    val surveyType: SurveyType
}
