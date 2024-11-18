package dev.jombi.template.core.survey.extensions

import dev.jombi.template.business.survey.dto.QuestionDto
import dev.jombi.template.core.survey.entity.Question

fun Question.toDto() {
    when (this) {
        is Question.UserPrompt -> toDto()

        is Question.SingleChoice -> toDto()
        is Question.MultiChoice -> toDto()

        is Question.Value -> toDto()
        is Question.ValueRange -> toDto()

        is Question.CalendarRange -> toDto()
        is Question.CalendarSingle -> toDto()

        is Question.Rating -> toDto()
    }
}

private fun Question.UserPrompt.toDto() = QuestionDto.UserPromptDto(
    id = id.toString(),
    title = title,
    required = required,
    placeholder = placeholder,
    minLength = minLength,
    maxLength = maxLength
)

private fun Question.SingleChoice.toDto() = QuestionDto.SingleChoiceDto(
    id = id.toString(),
    title = title,
    required = required,
    choices = choices.map { it.toDto() },
)

private fun Question.MultiChoice.toDto() = QuestionDto.MultiChoiceDto(
    id = id.toString(),
    title = title,
    required = required,
    choices = choices.map { it.toDto() },
    minSelection = minSelection,
    maxSelection = maxSelection,
)

private fun Question.Value.toDto() = QuestionDto.ValueDto(
    id = id.toString(),
    title = title,
    required = required,
    minNumber = minNumber,
    maxNumber = maxNumber,
)

private fun Question.ValueRange.toDto() = QuestionDto.ValueRangeDto(
    id = id.toString(),
    title = title,
    required = required,
    minNumber = minNumber,
    maxNumber = maxNumber,
)

private fun Question.CalendarSingle.toDto() = QuestionDto.CalendarSingleDto(
    id = id.toString(),
    title = title,
    required = required,
    startDate = startDate,
    endDate = endDate,
)

private fun Question.CalendarRange.toDto() = QuestionDto.CalendarRangeDto(
    id = id.toString(),
    title = title,
    required = required,
    startDate = startDate,
    endDate = endDate,
)

private fun Question.Rating.toDto() = QuestionDto.RatingDto(
    id = id.toString(),
    title = title,
    required = required,
    choices = choices.map { it.toDto() },
)
