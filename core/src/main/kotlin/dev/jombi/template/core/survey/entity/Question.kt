package dev.jombi.template.core.survey.entity

import com.fasterxml.jackson.annotation.JsonCreator
import org.bson.types.ObjectId
import java.time.LocalDate

sealed interface Question {
    val id: ObjectId
    val title: String
    val required: Boolean

    data class UserPrompt(
        override val title: String,
        override val required: Boolean,
        val placeholder: String?,
        val minLength: Long?,
        val maxLength: Long?,

        override val id: ObjectId = ObjectId.get(),
    ) : Question {
        companion object
    }

    data class SingleChoice(
        override val title: String,
        override val required: Boolean,
        val choices: List<Choice>,

        override val id: ObjectId = ObjectId.get(),
    ) : Question {
        companion object
    }

    data class MultiChoice(
        override val title: String,
        override val required: Boolean,
        val choices: List<Choice>,
        val minSelection: Int?,
        val maxSelection: Int?,

        override val id: ObjectId = ObjectId.get(),
    ) : Question {
        companion object
    }

    data class Value @JsonCreator constructor(
        override val title: String,
        override val required: Boolean,
        val minNumber: Long,
        val maxNumber: Long,

        override val id: ObjectId = ObjectId.get(),
    ) : Question {
        companion object
    }

    data class ValueRange(
        override val title: String,
        override val required: Boolean,
        val minNumber: Long,
        val maxNumber: Long,

        override val id: ObjectId = ObjectId.get(),
    ) : Question {
        companion object
    }

    data class CalendarSingle(
        override val title: String,
        override val required: Boolean,
        val startDate: LocalDate,
        val endDate: LocalDate,

        override val id: ObjectId = ObjectId.get(),
    ) : Question {
        companion object
    }

    data class CalendarRange(
        override val title: String,
        override val required: Boolean,
        val startDate: LocalDate,
        val endDate: LocalDate,

        override val id: ObjectId = ObjectId.get(),
    ) : Question {
        companion object
    }

    data class Rating(
        override val title: String,
        override val required: Boolean,
        val choices: List<Choice>,

        override val id: ObjectId = ObjectId.get(),
    ) : Question {
        companion object
    }

    companion object;
}
