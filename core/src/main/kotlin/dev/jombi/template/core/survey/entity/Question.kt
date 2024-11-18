package dev.jombi.template.core.survey.entity

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import org.bson.types.ObjectId
import java.time.LocalDate

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
sealed interface Question {
    val id: ObjectId
    val title: String
    val required: Boolean

    @JsonTypeName("USER_PROMPT")
    data class UserPrompt(
        override val title: String,
        override val required: Boolean,
        val placeholder: String?,
        val minLength: Long?,
        val maxLength: Long?,

        override val id: ObjectId = ObjectId.get()
    ) : Question

    @JsonTypeName("SINGLE_CHOICE")
    data class SingleChoice(
        override val title: String,
        override val required: Boolean,
        val choices: List<Choice>,

        override val id: ObjectId = ObjectId.get()
    ) : Question

    @JsonTypeName("MULTI_CHOICE")
    data class MultiChoice(
        override val title: String,
        override val required: Boolean,
        val choices: List<Choice>,
        val minSelection: Int?,
        val maxSelection: Int?,

        override val id: ObjectId = ObjectId.get()
    ) : Question

    @JsonTypeName("VALUE")
    data class Value(
        override val title: String,
        override val required: Boolean,
        val minNumber: Long,
        val maxNumber: Long,

        override val id: ObjectId = ObjectId.get()
    ) : Question

    @JsonTypeName("VALUE_RANGE")
    data class ValueRange(
        override val title: String,
        override val required: Boolean,
        val minNumber: Long,
        val maxNumber: Long,

        override val id: ObjectId = ObjectId.get()
    ) : Question

    @JsonTypeName("CALENDAR_SINGLE")
    data class CalendarSingle(
        override val title: String,
        override val required: Boolean,
        val startDate: LocalDate,
        val endDate: LocalDate,

        override val id: ObjectId = ObjectId.get()
    ) : Question

    @JsonTypeName("CALENDAR_RANGE")
    data class CalendarRange(
        override val title: String,
        override val required: Boolean,
        val startDate: LocalDate,
        val endDate: LocalDate,

        override val id: ObjectId = ObjectId.get()
    ) : Question

    @JsonTypeName("RATING")
    data class Rating(
        override val title: String,
        override val required: Boolean,
        val choices: List<Choice>,

        override val id: ObjectId = ObjectId.get()
    ) : Question
}