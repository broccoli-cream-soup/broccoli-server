package dev.jombi.template.business.survey.service

import dev.jombi.template.business.survey.dto.create.QuestionCreateDto
import dev.jombi.template.business.survey.dto.QuestionDto
import dev.jombi.template.business.survey.dto.create.SurveyCreateDto
import dev.jombi.template.business.survey.dto.SurveyDto
import dev.jombi.template.business.survey.dto.SurveyEditDto
import dev.jombi.template.business.survey.dto.SurveyInfoDto
import org.springframework.stereotype.Service

@Service
interface SurveyService {
    fun discoverSurvey(): List<SurveyInfoDto>

    fun createSurvey(dto: SurveyCreateDto): SurveyDto
    fun getSurvey(surveyId: String): SurveyDto
    fun editSurvey(surveyId: String, surveyEditDto: SurveyEditDto): SurveyDto
    fun deleteSurvey(surveyId: String)

    fun addQuestion(surveyId: String, questionCreateDto: QuestionCreateDto): SurveyDto
//    fun getQuestion(surveyId: String, questionId: String): QuestionDto
    fun editQuestion(surveyId: String, questionDto: QuestionDto): SurveyDto
    fun deleteQuestion(surveyId: String, questionId: String): SurveyDto
}
