package dev.jombi.template.core.reply.extensions

import dev.jombi.template.business.reply.dto.AnswerDto
import dev.jombi.template.business.survey.dto.QuestionType
import dev.jombi.template.core.reply.entity.Answer
import dev.jombi.template.core.reply.entity.RankedChoice
import org.bson.types.ObjectId

fun Answer.guessType() = when (this) {
    is Answer.CalendarRange -> QuestionType.CALENDAR_RANGE
    is Answer.CalendarSingle -> QuestionType.CALENDAR_SINGLE
    is Answer.MultiChoice -> QuestionType.MULTI_CHOICE
    is Answer.Rating -> QuestionType.RATING
    is Answer.SingleChoice -> QuestionType.SINGLE_CHOICE
    is Answer.UserPrompt -> QuestionType.USER_PROMPT
    is Answer.Value -> QuestionType.VALUE
    is Answer.ValueRange -> QuestionType.VALUE_RANGE
}

fun Answer.Companion.from(dto: AnswerDto) = when (dto) {
    is AnswerDto.CalendarRangeDto -> Answer.CalendarRange.from(dto)
    is AnswerDto.CalendarSingleDto -> Answer.CalendarSingle.from(dto)
    is AnswerDto.MultiChoiceDto -> Answer.MultiChoice.from(dto)
    is AnswerDto.RatingDto -> Answer.Rating.from(dto)
    is AnswerDto.SingleChoiceDto -> Answer.SingleChoice.from(dto)
    is AnswerDto.UserPromptDto -> Answer.UserPrompt.from(dto)
    is AnswerDto.ValueDto -> Answer.Value.from(dto)
    is AnswerDto.ValueRangeDto -> Answer.ValueRange.from(dto)
}
private fun Answer.CalendarRange.Companion.from(dto: AnswerDto.CalendarRangeDto) = Answer.CalendarRange(
    questionId = ObjectId(dto.questionId),
    startDate = dto.startDate,
    endDate = dto.endDate,
)
private fun Answer.CalendarSingle.Companion.from(dto: AnswerDto.CalendarSingleDto) = Answer.CalendarSingle(
    questionId = ObjectId(dto.questionId),
    date = dto.date
)
private fun Answer.MultiChoice.Companion.from(dto: AnswerDto.MultiChoiceDto) = Answer.MultiChoice(
    questionId = ObjectId(dto.questionId),
    choice = dto.choice
)
private fun Answer.Rating.Companion.from(dto: AnswerDto.RatingDto) = Answer.Rating(
    questionId = ObjectId(dto.questionId),
    choices = dto.choices.map { RankedChoice.from(it) },
)
private fun Answer.SingleChoice.Companion.from(dto: AnswerDto.SingleChoiceDto) = Answer.SingleChoice(
    questionId = ObjectId(dto.questionId),
    choice = dto.choice,
)
private fun Answer.UserPrompt.Companion.from(dto: AnswerDto.UserPromptDto) = Answer.UserPrompt(
    questionId = ObjectId(dto.questionId),
    prompt = dto.prompt,
)
private fun Answer.Value.Companion.from(dto: AnswerDto.ValueDto) = Answer.Value(
    questionId = ObjectId(dto.questionId),
    value = dto.value,
)
private fun Answer.ValueRange.Companion.from(dto: AnswerDto.ValueRangeDto) = Answer.ValueRange(
    questionId = ObjectId(dto.questionId),
    minValue = dto.minValue,
    maxValue = dto.maxValue,
)

fun Answer.toDto() = when(this) {
    is Answer.CalendarRange -> toDto()
    is Answer.CalendarSingle -> toDto()
    is Answer.MultiChoice -> toDto()
    is Answer.Rating -> toDto()
    is Answer.SingleChoice -> toDto()
    is Answer.UserPrompt -> toDto()
    is Answer.Value -> toDto()
    is Answer.ValueRange -> toDto()
}
private fun Answer.CalendarRange.toDto() = AnswerDto.CalendarRangeDto(
    questionId = questionId.toString(),
    startDate = startDate,
    endDate = endDate,
)
private fun Answer.CalendarSingle.toDto() = AnswerDto.CalendarSingleDto(
    questionId = questionId.toString(),
    date = date
)
private fun Answer.MultiChoice.toDto() = AnswerDto.MultiChoiceDto(
    questionId = questionId.toString(),
    choice = choice
)
private fun Answer.Rating.toDto() = AnswerDto.RatingDto(
    questionId = questionId.toString(),
    choices = choices.map { it.toDto() },
)
private fun Answer.SingleChoice.toDto() = AnswerDto.SingleChoiceDto(
    questionId = questionId.toString(),
    choice = choice,
)
private fun Answer.UserPrompt.toDto() = AnswerDto.UserPromptDto(
    questionId = questionId.toString(),
    prompt = prompt,
)
private fun Answer.Value.toDto() = AnswerDto.ValueDto(
    questionId = questionId.toString(),
    value = value,
)
private fun Answer.ValueRange.toDto() = AnswerDto.ValueRangeDto(
    questionId = questionId.toString(),
    minValue = minValue,
    maxValue = maxValue,
)
