package soup.cream.broccoli.survey.core.survey.service

import soup.cream.broccoli.survey.business.survey.dto.QuestionDto
import soup.cream.broccoli.survey.business.survey.dto.SurveyDto
import soup.cream.broccoli.survey.business.survey.dto.SurveyEditDto
import soup.cream.broccoli.survey.business.survey.dto.SurveyInfoDto
import soup.cream.broccoli.survey.business.survey.dto.create.QuestionCreateDto
import soup.cream.broccoli.survey.business.survey.dto.create.SurveyCreateDto
import soup.cream.broccoli.survey.business.survey.service.SurveyService
import soup.cream.broccoli.survey.common.exception.CustomException
import soup.cream.broccoli.survey.core.common.entity.FetchableId
import soup.cream.broccoli.survey.core.member.MemberHolder
import soup.cream.broccoli.survey.core.member.repository.MemberJpaRepository
import soup.cream.broccoli.survey.core.survey.entity.Question
import soup.cream.broccoli.survey.core.survey.entity.Survey
import soup.cream.broccoli.survey.core.survey.exception.SurveyExceptionDetails
import soup.cream.broccoli.survey.core.survey.extensions.*
import soup.cream.broccoli.survey.core.survey.repository.SurveyRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class SurveyServiceImpl(
    private val surveyDelegate: SurveyRepositoryDelegate,
    private val surveyRepository: SurveyRepository,
    private val memberRepository: MemberJpaRepository,
    private val memberHolder: MemberHolder,
) : SurveyService {
    override fun discoverSurvey(): List<SurveyInfoDto> {
        val survey = surveyRepository.findAllBy()  // TODO: Pagination
        return survey.map { it.toDto(getAuthorById(it.authorId)) }
    }

    @Transactional(rollbackFor = [Exception::class])
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

    @Transactional(rollbackFor = [Exception::class])
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

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteSurvey(surveyId: String) {
        val ent = surveyDelegate.findWithOwnerCheck(surveyId)
        surveyRepository.delete(ent)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun addQuestion(surveyId: String, questionCreateDto: QuestionCreateDto): SurveyDto {
        val survey = surveyDelegate.findWithOwnerCheck(surveyId)

        val created = Question.create(questionCreateDto)

        if (!created.validate())
            throw CustomException(SurveyExceptionDetails.QUESTION_VALIDATION_FAILED)

        val questions = survey.questions + created
        val final = surveyRepository.save(survey.copy(questions = questions))

        return final.toDto(memberHolder.get().name)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun editQuestion(surveyId: String, questionDto: QuestionDto): SurveyDto {
        val survey = surveyDelegate.findWithOwnerCheck(surveyId)

        val questionId = questionDto.id
        val question = survey.questions.find { it.id.toString() == questionId }
            ?: throw CustomException(SurveyExceptionDetails.QUESTION_NOT_FOUND, questionId)
        val editQuestion = Question.from(questionDto)

        if (question::class != editQuestion::class)
            throw CustomException(SurveyExceptionDetails.QUESTION_TYPE_MISMATCH, questionId, question.guessType())

        if (!editQuestion.validate())
            throw CustomException(SurveyExceptionDetails.QUESTION_VALIDATION_FAILED)

        val final = surveyRepository.save(
            survey.copy(
                questions = (survey.questions.filter {
                    it.id.toString() != questionId
                } + editQuestion).toSet()
            )
        )

        return final.toDto(memberHolder.get().name)
    }

    @Transactional(rollbackFor = [Exception::class])
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
