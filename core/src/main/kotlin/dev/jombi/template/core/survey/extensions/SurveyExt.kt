package dev.jombi.template.core.survey.extensions

import dev.jombi.template.business.survey.dto.SurveyDto
import dev.jombi.template.business.survey.dto.create.SurveyCreateDto
import dev.jombi.template.core.survey.entity.Survey

fun Survey.Companion.create(dto: SurveyCreateDto, authorId: Long) = Survey(
    name = dto.name,
    authorId = authorId,
    description = dto.description,
    questions = setOf(),
    surveyType = dto.surveyType.toConst(),
)

fun Survey.toDto(author: String) = SurveyDto(
    id = id.toString(),
    author = author,
    name = name,
    description = description,
    questions = questions.map { it.toDto() },
    surveyType = surveyType.toDto()
)
