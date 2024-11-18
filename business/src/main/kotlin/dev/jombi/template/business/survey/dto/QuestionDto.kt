package dev.jombi.template.business.survey.dto

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import java.time.LocalDate

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
sealed interface QuestionDto {
    val id: String
    val title: String
    val required: Boolean

    @JsonTypeName("USER_PROMPT")
    data class UserPromptDto(
        override val id: String,
        override val title: String,
        override val required: Boolean,
        val placeholder: String?,
        val minLength: Long?,
        val maxLength: Long?,
    ) : QuestionDto

    @JsonTypeName("SINGLE_CHOICE")
    data class SingleChoiceDto(
        override val id: String,
        override val title: String,
        override val required: Boolean,
        val choices: List<ChoiceDto>,
    ) : QuestionDto

    @JsonTypeName("MULTI_CHOICE")
    data class MultiChoiceDto(
        override val id: String,
        override val title: String,
        override val required: Boolean,
        val choices: List<ChoiceDto>,
        val minSelection: Int?,
        val maxSelection: Int?,
    ) : QuestionDto

    @JsonTypeName("VALUE")
    data class ValueDto(
        override val id: String,
        override val title: String,
        override val required: Boolean,
        val minNumber: Long,
        val maxNumber: Long,
    ) : QuestionDto

    @JsonTypeName("VALUE_RANGE")
    data class ValueRangeDto(
        override val id: String,
        override val title: String,
        override val required: Boolean,
        val minNumber: Long,
        val maxNumber: Long,
    ) : QuestionDto

    @JsonTypeName("CALENDAR_SINGLE")
    data class CalendarSingleDto(
        override val id: String,
        override val title: String,
        override val required: Boolean,
        val startDate: LocalDate,
        val endDate: LocalDate,
    ) : QuestionDto

    @JsonTypeName("CALENDAR_RANGE")
    data class CalendarRangeDto(
        override val id: String,
        override val title: String,
        override val required: Boolean,
        val startDate: LocalDate,
        val endDate: LocalDate,
    ) : QuestionDto

    @JsonTypeName("RATING")
    data class RatingDto(
        override val id: String,
        override val title: String,
        override val required: Boolean,
        val choices: List<ChoiceDto>,
    ) : QuestionDto
}
