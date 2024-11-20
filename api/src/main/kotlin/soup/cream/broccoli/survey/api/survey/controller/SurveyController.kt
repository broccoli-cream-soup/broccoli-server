package soup.cream.broccoli.survey.api.survey.controller

import soup.cream.broccoli.survey.business.survey.dto.QuestionDto
import soup.cream.broccoli.survey.business.survey.dto.SurveyDto
import soup.cream.broccoli.survey.business.survey.dto.SurveyEditDto
import soup.cream.broccoli.survey.business.survey.dto.SurveyInfoDto
import soup.cream.broccoli.survey.business.survey.dto.create.QuestionCreateDto
import soup.cream.broccoli.survey.business.survey.dto.create.SurveyCreateDto
import soup.cream.broccoli.survey.business.survey.service.SurveyService
import soup.cream.broccoli.survey.common.response.ResponseData
import soup.cream.broccoli.survey.common.response.ResponseEmpty
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/survey")
class SurveyController(
    private val surveyService: SurveyService,
) {
    @GetMapping
    fun discoverSurvey(): ResponseEntity<ResponseData<List<soup.cream.broccoli.survey.business.survey.dto.SurveyInfoDto>>> {
        val res = surveyService.discoverSurvey()
        return ResponseData.ok(data = res)
    }

    @GetMapping("/{id}")
    fun getSurvey(@PathVariable id: String): ResponseEntity<ResponseData<SurveyDto>> {
        val res = surveyService.getSurvey(id)
        return ResponseData.ok(data = res)
    }

    @PostMapping
    fun createSurvey(@RequestBody @Valid surveyCreateDto: SurveyCreateDto): ResponseEntity<ResponseData<SurveyDto>> {
        val res = surveyService.createSurvey(surveyCreateDto)
        return ResponseData.created(data = res)
    }

    @PatchMapping("/{id}")
    fun getSurvey(
        @RequestBody @Valid surveyEditDto: SurveyEditDto,
        @PathVariable id: String,
    ): ResponseEntity<ResponseData<SurveyDto>> {
        val res = surveyService.editSurvey(id, surveyEditDto)
        return ResponseData.ok(data = res)
    }

    @DeleteMapping("/{id}")
    fun deleteSurvey(@PathVariable id: String): ResponseEntity<ResponseEmpty> {
        surveyService.deleteSurvey(id)
        return ResponseEmpty.ok()
    }


    @PostMapping("/{id}/questions")
    fun addQuestions(
        @PathVariable id: String,
        @RequestBody questionCreateDto: QuestionCreateDto,
    ): ResponseEntity<ResponseData<SurveyDto>> {
        val res = surveyService.addQuestion(id, questionCreateDto)
        return ResponseData.created(data = res)
    }

    @PatchMapping("/{id}/questions")
    fun editQuestions(
        @PathVariable id: String,
        @RequestBody questionDto: QuestionDto,
    ): ResponseEntity<ResponseData<SurveyDto>> {
        val res = surveyService.editQuestion(id, questionDto)
        return ResponseData.ok(data = res)
    }

    @DeleteMapping("/{surveyId}/questions/{questionId}")
    fun deleteQuestions(
        @PathVariable surveyId: String,
        @PathVariable questionId: String,
    ): ResponseEntity<ResponseData<SurveyDto>> {
        val res = surveyService.deleteQuestion(surveyId, questionId)
        return ResponseData.ok(data = res)

    }
}
