package soup.cream.broccoli.survey.core.survey.service

import soup.cream.broccoli.survey.common.exception.CustomException
import soup.cream.broccoli.survey.core.common.id.ne
import soup.cream.broccoli.survey.core.member.MemberHolder
import soup.cream.broccoli.survey.core.survey.entity.Survey
import soup.cream.broccoli.survey.core.survey.exception.SurveyExceptionDetails
import soup.cream.broccoli.survey.core.survey.repository.SurveyRepository
import org.bson.types.ObjectId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class SurveyRepositoryDelegate(
    private val surveyRepository: SurveyRepository,
    private val memberHolder: MemberHolder,
) {
    fun findById(surveyId: String) = surveyRepository.findByIdOrNull(ObjectId(surveyId))
        ?: throw CustomException(SurveyExceptionDetails.SURVEY_NOT_FOUND, surveyId)

    fun findWithOwnerCheck(surveyId: String): Survey {
        val survey = findById(surveyId)

        if (memberHolder.get().id ne survey.authorId)
            throw CustomException(SurveyExceptionDetails.NOT_YOUR_SURVEY, survey.id.toString())

        return survey
    }
}
