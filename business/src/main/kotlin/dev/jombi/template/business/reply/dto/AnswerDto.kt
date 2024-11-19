package dev.jombi.template.business.reply.dto

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import java.time.LocalDate

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
sealed interface AnswerDto {
    val questionId: String
    @JsonTypeName("USER_PROMPT")
    data class UserPromptDto(
        override val questionId: String,
        val prompt: String,
    ) : AnswerDto

    @JsonTypeName("SINGLE_CHOICE")
    data class SingleChoiceDto(
        override val questionId: String,
        val choice: Long,
    ) : AnswerDto

    @JsonTypeName("MULTI_CHOICE")
    data class MultiChoiceDto(
        override val questionId: String,
        val choice: List<Long>,
    ) : AnswerDto

    @JsonTypeName("VALUE")
    data class ValueDto(
        override val questionId: String,
        val value: Long,
    ) : AnswerDto

    @JsonTypeName("VALUE_RANGE")
    data class ValueRangeDto(
        override val questionId: String,
        val minValue: Long,
        val maxValue: Long,
    ) : AnswerDto

    @JsonTypeName("CALENDAR_SINGLE")
    data class CalendarSingleDto(
        override val questionId: String,
        val date: LocalDate,
    ) : AnswerDto

    @JsonTypeName("CALENDAR_RANGE")
    data class CalendarRangeDto(
        override val questionId: String,
        val startDate: LocalDate,
        val endDate: LocalDate,
    ) : AnswerDto

    @JsonTypeName("RATING")
    data class RatingDto(
        override val questionId: String,
        val choices: List<RankedChoiceDto>,
    ) : AnswerDto
}
