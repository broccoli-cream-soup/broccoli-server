package dev.jombi.template.core.survey.service

import dev.jombi.template.common.exception.CustomException
import dev.jombi.template.core.common.id.ne
import dev.jombi.template.core.member.MemberHolder
import dev.jombi.template.core.survey.entity.Survey
import dev.jombi.template.core.survey.exception.SurveyExceptionDetails
import dev.jombi.template.core.survey.repository.SurveyRepository
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
