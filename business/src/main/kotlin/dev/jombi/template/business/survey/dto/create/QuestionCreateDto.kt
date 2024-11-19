package dev.jombi.template.business.survey.CreateDto.create

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver
import dev.jombi.template.business.survey.dto.ChoiceDto
import dev.jombi.template.business.survey.dto.QuestionDto
import dev.jombi.template.business.survey.dto.QuestionType
import java.time.LocalDate

@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonTypeIdResolver(QuestionDto.QuestionTypeResolver::class)
sealed interface QuestionCreateDto {
    val title: String
    val required: Boolean
    val type: QuestionType

    @JsonTypeName("USER_PROMPT")
    data class UserPromptCreateDto(
        override val title: String,
        override val required: Boolean,
        val placeholder: String?,
        val minLength: Long?,
        val maxLength: Long?,

        override val type: QuestionType = QuestionType.USER_PROMPT,
    ) : QuestionCreateDto

    @JsonTypeName("SINGLE_CHOICE")
    data class SingleChoiceCreateDto(
        override val title: String,
        override val required: Boolean,
        val choices: List<ChoiceDto>,

        override val type: QuestionType = QuestionType.SINGLE_CHOICE,
    ) : QuestionCreateDto

    @JsonTypeName("MULTI_CHOICE")
    data class MultiChoiceCreateDto(
        override val title: String,
        override val required: Boolean,
        val choices: List<ChoiceDto>,
        val minSelection: Int,
        val maxSelection: Int,

        override val type: QuestionType = QuestionType.MULTI_CHOICE,
    ) : QuestionCreateDto

    @JsonTypeName("VALUE")
    data class ValueCreateDto(
        override val title: String,
        override val required: Boolean,
        val minNumber: Long,
        val maxNumber: Long,

        override val type: QuestionType = QuestionType.VALUE,
    ) : QuestionCreateDto

    @JsonTypeName("VALUE_RANGE")
    data class ValueRangeCreateDto(
        override val title: String,
        override val required: Boolean,
        val minNumber: Long,
        val maxNumber: Long,

        override val type: QuestionType = QuestionType.VALUE_RANGE,
    ) : QuestionCreateDto

    @JsonTypeName("CALENDAR_SINGLE")
    data class CalendarSingleCreateDto(
        override val title: String,
        override val required: Boolean,
        val startDate: LocalDate,
        val endDate: LocalDate,

        override val type: QuestionType = QuestionType.CALENDAR_SINGLE,
    ) : QuestionCreateDto

    @JsonTypeName("CALENDAR_RANGE")
    data class CalendarRangeCreateDto(
        override val title: String,
        override val required: Boolean,
        val startDate: LocalDate,
        val endDate: LocalDate,

        override val type: QuestionType = QuestionType.CALENDAR_RANGE,
    ) : QuestionCreateDto

    @JsonTypeName("RATING")
    data class RatingCreateDto(
        override val title: String,
        override val required: Boolean,
        val choices: List<ChoiceDto>,

        override val type: QuestionType = QuestionType.RATING,
    ) : QuestionCreateDto
}
