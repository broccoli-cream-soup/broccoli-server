package dev.jombi.template.core.survey.service

import dev.jombi.template.business.survey.dto.create.QuestionCreateDto
import dev.jombi.template.business.survey.dto.QuestionDto
import dev.jombi.template.business.survey.dto.SurveyDto
import dev.jombi.template.business.survey.dto.SurveyEditDto
import dev.jombi.template.business.survey.dto.create.SurveyCreateDto
import dev.jombi.template.business.survey.service.SurveyService
import dev.jombi.template.common.exception.CustomException
import dev.jombi.template.core.common.entity.FetchableId
import dev.jombi.template.core.member.MemberHolder
import dev.jombi.template.core.member.repository.MemberJpaRepository
import dev.jombi.template.core.survey.entity.Question
import dev.jombi.template.core.survey.entity.Survey
import dev.jombi.template.core.survey.exception.SurveyExceptionDetails
import dev.jombi.template.core.survey.extensions.*
import dev.jombi.template.core.survey.repository.SurveyRepository
import org.springframework.stereotype.Service

@Service
class SurveyServiceImpl(
    private val surveyDelegate: SurveyRepositoryDelegate,
    private val surveyRepository: SurveyRepository,
    private val memberRepository: MemberJpaRepository,
    private val memberHolder: MemberHolder,
) : SurveyService {
    override fun createSurvey(dto: SurveyCreateDto): SurveyDto {
        val me = memberHolder.get()

        val created = surveyRepository.save(Survey.create(dto, me.id.id))

        return created.toDto(me.name)
    }

    override fun getSurvey(surveyId: String): SurveyDto {
        val ent = surveyDelegate.findById(surveyId)
        val authorName = getAuthorById(ent.authorId)

        return ent.toDto(authorName)
    }

    override fun editSurvey(surveyId: String, surveyEditDto: SurveyEditDto): SurveyDto {
        val ent = surveyDelegate.findWithOwnerCheck(surveyId)

        val (name, description, surveyType) = surveyEditDto

        val edited = surveyRepository.save(
            ent.copy(
                name = name ?: ent.name,
                description = description ?: ent.description,
                surveyType = surveyType?.toConst() ?: ent.surveyType
            )
        )

        return edited.toDto(memberHolder.get().name)
    }

    override fun deleteSurvey(surveyId: String) {
        val ent = surveyDelegate.findWithOwnerCheck(surveyId)
        surveyRepository.delete(ent)
    }

    override fun addQuestion(surveyId: String, questionCreateDto: QuestionCreateDto): SurveyDto {
        val survey = surveyDelegate.findWithOwnerCheck(surveyId)

        val questions = survey.questions + Question.create(questionCreateDto)
        val final = surveyRepository.save(survey.copy(questions = questions))

        return final.toDto(memberHolder.get().name)
    }

    override fun editQuestion(surveyId: String, questionDto: QuestionDto): SurveyDto {
        val survey = surveyDelegate.findWithOwnerCheck(surveyId)

        val questionId = questionDto.id
        val question = survey.questions.find { it.id.toString() == questionId }
            ?: throw CustomException(SurveyExceptionDetails.QUESTION_NOT_FOUND, questionId)
        val editQuestion = Question.from(questionDto)

        if (question::class != editQuestion::class)
            throw CustomException(SurveyExceptionDetails.QUESTION_TYPE_MISMATCH, questionId, question.guessType())

        val final = surveyRepository.save(
            survey.copy(
                questions = (survey.questions.filter {
                    it.id.toString() != questionId
                } + editQuestion).toSet()
            )
        )

        return final.toDto(memberHolder.get().name)
    }

    override fun deleteQuestion(surveyId: String, questionId: String): SurveyDto {
        val survey = surveyDelegate.findWithOwnerCheck(surveyId)

        val question = survey.questions.find { it.id.toString() == questionId }
            ?: throw CustomException(SurveyExceptionDetails.QUESTION_NOT_FOUND, questionId)

        val final = surveyRepository.save(
            survey.copy(
                questions = (survey.questions - question).toSet()
            )
        )

        return final.toDto(memberHolder.get().name)
    }

    private fun getAuthorById(memberId: Long) = FetchableId(memberId).fetch(memberRepository)?.name ?: "사라진 유저"
}
