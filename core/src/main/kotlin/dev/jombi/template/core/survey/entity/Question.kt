package dev.jombi.template.core.survey.entity

import com.fasterxml.jackson.annotation.JsonCreator
import org.bson.types.ObjectId
import java.time.LocalDate

sealed interface Question {
    val id: ObjectId
    val title: String
    val required: Boolean

    fun validate(): Boolean

    data class UserPrompt(
        override val title: String,
        override val required: Boolean,
        val placeholder: String?,
        val minLength: Long?,
        val maxLength: Long?,

        override val id: ObjectId = ObjectId.get(),
    ) : Question {
        companion object;
        override fun validate(): Boolean {
            if (minLength != null && minLength < 1) return false
            if (maxLength != null && maxLength < 1) return false
            if (minLength != null && maxLength != null)
                return minLength <= maxLength
            return true
        }
    }

    data class SingleChoice(
        override val title: String,
        override val required: Boolean,
        val choices: List<Choice>,

        override val id: ObjectId = ObjectId.get(),
    ) : Question {
        companion object;
        override fun validate(): Boolean {
            return choices.distinctBy { it.id }.size == choices.size
        }
    }

    data class MultiChoice(
        override val title: String,
        override val required: Boolean,
        val choices: List<Choice>,
        val minSelection: Int?,
        val maxSelection: Int?,

        override val id: ObjectId = ObjectId.get(),
    ) : Question {
        companion object;
        override fun validate(): Boolean {
            if (choices.distinctBy { it.id }.size != choices.size) return false
            if (maxSelection?.let { it !in choices.indices } == true) return false
            if (minSelection?.let { it !in choices.indices } == true) return false
            if (minSelection != null && maxSelection != null)
                return minSelection <= maxSelection
            return true
        }
    }

    data class Value @JsonCreator constructor(
        override val title: String,
        override val required: Boolean,
        val minNumber: Long,
        val maxNumber: Long,

        override val id: ObjectId = ObjectId.get(),
    ) : Question {
        companion object;
        override fun validate(): Boolean {
            if (minNumber < 0 || maxNumber < 0) return false
            return minNumber <= maxNumber
        }
    }

    data class ValueRange(
        override val title: String,
        override val required: Boolean,
        val minNumber: Long,
        val maxNumber: Long,

        override val id: ObjectId = ObjectId.get(),
    ) : Question {
        companion object;
        override fun validate(): Boolean {
            if (minNumber < 0 || maxNumber < 0) return false
            return minNumber <= maxNumber
        }
    }

    data class CalendarSingle(
        override val title: String,
        override val required: Boolean,
        val startDate: LocalDate,
        val endDate: LocalDate,

        override val id: ObjectId = ObjectId.get(),
    ) : Question {
        companion object;
        override fun validate(): Boolean {
            return startDate <= endDate
        }
    }

    data class CalendarRange(
        override val title: String,
        override val required: Boolean,
        val startDate: LocalDate,
        val endDate: LocalDate,

        override val id: ObjectId = ObjectId.get(),
    ) : Question {
        companion object;
        override fun validate(): Boolean {
            return startDate <= endDate
        }
    }

    data class Rating(
        override val title: String,
        override val required: Boolean,
        val choices: List<Choice>,

        override val id: ObjectId = ObjectId.get(),
    ) : Question {
        companion object;
        override fun validate(): Boolean {
            return choices.distinctBy { it.id }.size == choices.size
        }
    }

    companion object;
}
