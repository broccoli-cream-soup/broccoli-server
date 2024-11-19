package dev.jombi.template.api.survey.controller

import dev.jombi.template.business.survey.dto.QuestionDto
import dev.jombi.template.business.survey.dto.SurveyEditDto
import dev.jombi.template.business.survey.dto.create.QuestionCreateDto
import dev.jombi.template.business.survey.dto.create.SurveyCreateDto
import dev.jombi.template.business.survey.service.SurveyService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/survey")
class SurveyController(
    private val surveyService: SurveyService,
) {
    @GetMapping
    fun discoverSurvey() = surveyService.discoverSurvey()

    @GetMapping("/{id}")
    fun getSurvey(@PathVariable id: String) = surveyService.getSurvey(id)

    @PostMapping
    fun createSurvey(@RequestBody @Valid surveyCreateDto: SurveyCreateDto) = surveyService.createSurvey(surveyCreateDto)

    @PatchMapping("/{id}")
    fun getSurvey(@RequestBody @Valid surveyEditDto: SurveyEditDto, @PathVariable id: String) =
        surveyService.editSurvey(id, surveyEditDto)

    @DeleteMapping("/{id}")
    fun deleteSurvey(@PathVariable id: String) = surveyService.deleteSurvey(id)


    @PostMapping("/{id}/questions")
    fun addQuestions(@PathVariable id: String, @RequestBody questionCreateDto: QuestionCreateDto) =
        surveyService.addQuestion(id, questionCreateDto)

    @PatchMapping("/{id}/questions")
    fun editQuestions(
        @PathVariable id: String,
        @RequestBody questionDto: QuestionDto,
    ) = surveyService.editQuestion(id, questionDto)

    @DeleteMapping("/{surveyId}/questions/{questionId}")
    fun deleteQuestions(
        @PathVariable surveyId: String,
        @PathVariable questionId: String,
    ) = surveyService.deleteQuestion(surveyId, questionId)
}
