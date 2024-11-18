package dev.jombi.template.business.survey.CreateDto.create

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import dev.jombi.template.business.survey.dto.ChoiceDto
import java.time.LocalDate

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
sealed interface QuestionCreateDto {
    val title: String
    val required: Boolean

    @JsonTypeName("USER_PROMPT")
    data class UserPromptCreateDto(
        override val title: String,
        override val required: Boolean,
        val placeholder: String?,
        val minLength: Long?,
        val maxLength: Long?,
    ) : QuestionCreateDto

    @JsonTypeName("SINGLE_CHOICE")
    data class SingleChoiceCreateDto(
        override val title: String,
        override val required: Boolean,
        val choices: List<ChoiceDto>,
    ) : QuestionCreateDto

    @JsonTypeName("MULTI_CHOICE")
    data class MultiChoiceCreateDto(
        override val title: String,
        override val required: Boolean,
        val choices: List<ChoiceDto>,
        val maxSelection: Int,
    ) : QuestionCreateDto

    @JsonTypeName("VALUE")
    data class ValueCreateDto(
        override val title: String,
        override val required: Boolean,
        val minNumber: Long,
        val maxNumber: Long,
    ) : QuestionCreateDto

    @JsonTypeName("VALUE_RANGE")
    data class ValueRangeCreateDto(
        override val title: String,
        override val required: Boolean,
        val minNumber: Long,
        val maxNumber: Long,
    ) : QuestionCreateDto

    @JsonTypeName("CALENDAR_SINGLE")
    data class CalendarSingleCreateDto(
        override val title: String,
        override val required: Boolean,
        val startDate: LocalDate,
        val endDate: LocalDate,
    ) : QuestionCreateDto

    @JsonTypeName("CALENDAR_RANGE")
    data class CalendarRangeCreateDto(
        override val title: String,
        override val required: Boolean,
        val startDate: LocalDate,
        val endDate: LocalDate,
    ) : QuestionCreateDto

    @JsonTypeName("RATING")
    data class RatingCreateDto(
        override val title: String,
        override val required: Boolean,
        val choices: List<ChoiceDto>,
    ) : QuestionCreateDto
}
