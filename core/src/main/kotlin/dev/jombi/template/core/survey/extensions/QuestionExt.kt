package dev.jombi.template.core.survey.extensions

import dev.jombi.template.business.survey.CreateDto.create.QuestionCreateDto
import dev.jombi.template.business.survey.dto.QuestionDto
import dev.jombi.template.core.survey.entity.Choice
import dev.jombi.template.core.survey.entity.Question
import org.bson.types.ObjectId

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

fun Question.Companion.from(dto: QuestionDto) = when (dto) {
    is QuestionDto.CalendarRangeDto -> Question.CalendarRange.from(dto)
    is QuestionDto.CalendarSingleDto -> Question.CalendarSingle.from(dto)
    is QuestionDto.MultiChoiceDto -> Question.MultiChoice.from(dto)
    is QuestionDto.RatingDto -> Question.Rating.from(dto)
    is QuestionDto.SingleChoiceDto -> Question.SingleChoice.from(dto)
    is QuestionDto.UserPromptDto -> Question.UserPrompt.from(dto)
    is QuestionDto.ValueDto -> Question.Value.from(dto)
    is QuestionDto.ValueRangeDto -> Question.ValueRange.from(dto)
}
private fun Question.UserPrompt.Companion.from(dto: QuestionDto.UserPromptDto) = Question.UserPrompt(
    id = ObjectId(dto.id),
    title = dto.title,
    required = dto.required,
    placeholder = dto.placeholder,
    minLength = dto.minLength,
    maxLength = dto.maxLength
)
private fun Question.SingleChoice.Companion.from(dto: QuestionDto.SingleChoiceDto) = Question.SingleChoice(
    id = ObjectId(dto.id),
    title = dto.title,
    required = dto.required,
    choices = dto.choices.map { Choice.from(it) },
)
private fun Question.MultiChoice.Companion.from(dto: QuestionDto.MultiChoiceDto) = Question.MultiChoice(
    id = ObjectId(dto.id),
    title = dto.title,
    required = dto.required,
    choices = dto.choices.map { Choice.from(it) },
    minSelection = dto.minSelection,
    maxSelection = dto.maxSelection,
)
private fun Question.Value.Companion.from(dto: QuestionDto.ValueDto) = Question.Value(
    id = ObjectId(dto.id),
    title = dto.title,
    required = dto.required,
    minNumber = dto.minNumber,
    maxNumber = dto.maxNumber,
)
private fun Question.ValueRange.Companion.from(dto: QuestionDto.ValueRangeDto) = Question.ValueRange(
    id = ObjectId(dto.id),
    title = dto.title,
    required = dto.required,
    minNumber = dto.minNumber,
    maxNumber = dto.maxNumber,
)
private fun Question.CalendarSingle.Companion.from(dto: QuestionDto.CalendarSingleDto) = Question.CalendarSingle(
    id = ObjectId(dto.id),
    title = dto.title,
    required = dto.required,
    startDate = dto.startDate,
    endDate = dto.endDate,
)
private fun Question.CalendarRange.Companion.from(dto: QuestionDto.CalendarRangeDto) = Question.CalendarRange(
    id = ObjectId(dto.id),
    title = dto.title,
    required = dto.required,
    startDate = dto.startDate,
    endDate = dto.endDate,
)
private fun Question.Rating.Companion.from(dto: QuestionDto.RatingDto) = Question.Rating(
    id = ObjectId(dto.id),
    title = dto.title,
    required = dto.required,
    choices = dto.choices.map { Choice.from(it) },
)

fun Question.Companion.create(dto: QuestionCreateDto) = when (dto) {
    is QuestionCreateDto.CalendarRangeCreateDto -> Question.CalendarRange.create(dto)
    is QuestionCreateDto.CalendarSingleCreateDto -> Question.CalendarSingle.create(dto)
    is QuestionCreateDto.MultiChoiceCreateDto -> Question.MultiChoice.create(dto)
    is QuestionCreateDto.RatingCreateDto -> Question.Rating.create(dto)
    is QuestionCreateDto.SingleChoiceCreateDto -> Question.SingleChoice.create(dto)
    is QuestionCreateDto.UserPromptCreateDto -> Question.UserPrompt.create(dto)
    is QuestionCreateDto.ValueCreateDto -> Question.Value.create(dto)
    is QuestionCreateDto.ValueRangeCreateDto -> Question.ValueRange.create(dto)
}
private fun Question.UserPrompt.Companion.create(dto: QuestionCreateDto.UserPromptCreateDto) = Question.UserPrompt(
    title = dto.title,
    required = dto.required,
    placeholder = dto.placeholder,
    minLength = dto.minLength,
    maxLength = dto.maxLength
)
private fun Question.SingleChoice.Companion.create(dto: QuestionCreateDto.SingleChoiceCreateDto) = Question.SingleChoice(
    title = dto.title,
    required = dto.required,
    choices = dto.choices.map { Choice.from(it) },
)
private fun Question.MultiChoice.Companion.create(dto: QuestionCreateDto.MultiChoiceCreateDto) = Question.MultiChoice(
    title = dto.title,
    required = dto.required,
    choices = dto.choices.map { Choice.from(it) },
    minSelection = dto.maxSelection,
    maxSelection = dto.maxSelection,
)
private fun Question.Value.Companion.create(dto: QuestionCreateDto.ValueCreateDto) = Question.Value(
    title = dto.title,
    required = dto.required,
    minNumber = dto.minNumber,
    maxNumber = dto.maxNumber,
)
private fun Question.ValueRange.Companion.create(dto: QuestionCreateDto.ValueRangeCreateDto) = Question.ValueRange(
    title = dto.title,
    required = dto.required,
    minNumber = dto.minNumber,
    maxNumber = dto.maxNumber,
)
private fun Question.CalendarSingle.Companion.create(dto: QuestionCreateDto.CalendarSingleCreateDto) = Question.CalendarSingle(
    title = dto.title,
    required = dto.required,
    startDate = dto.startDate,
    endDate = dto.endDate,
)
private fun Question.CalendarRange.Companion.create(dto: QuestionCreateDto.CalendarRangeCreateDto) = Question.CalendarRange(
    title = dto.title,
    required = dto.required,
    startDate = dto.startDate,
    endDate = dto.endDate,
)
private fun Question.Rating.Companion.create(dto: QuestionCreateDto.RatingCreateDto) = Question.Rating(
    title = dto.title,
    required = dto.required,
    choices = dto.choices.map { Choice.from(it) },
)
