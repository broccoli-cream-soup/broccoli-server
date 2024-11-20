package soup.cream.broccoli.survey.business.survey.service

import soup.cream.broccoli.survey.business.survey.dto.create.QuestionCreateDto
import soup.cream.broccoli.survey.business.survey.dto.QuestionDto
import soup.cream.broccoli.survey.business.survey.dto.create.SurveyCreateDto
import soup.cream.broccoli.survey.business.survey.dto.SurveyDto
import soup.cream.broccoli.survey.business.survey.dto.SurveyEditDto
import soup.cream.broccoli.survey.business.survey.dto.SurveyInfoDto
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
