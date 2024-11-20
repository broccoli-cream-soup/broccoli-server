package soup.cream.broccoli.survey.core.reply.entity

import soup.cream.broccoli.survey.core.survey.entity.Question
import org.bson.types.ObjectId
import java.time.LocalDate

sealed interface Answer {
    val questionId: ObjectId

    fun validate(question: Question): Boolean

    data class UserPrompt(
        override val questionId: ObjectId,
        val prompt: String,
    ) : Answer {
        companion object;

        override fun validate(question: Question): Boolean {
            if (question !is Question.UserPrompt) return false
            return prompt.length in (question.minLength ?: 1)..(question.maxLength ?: Long.MAX_VALUE)
        }
    }

    data class SingleChoice(
        override val questionId: ObjectId,
        val choice: Int,
    ) : Answer {
        companion object;

        override fun validate(question: Question): Boolean {
            if (question !is Question.SingleChoice) return false
            return question.choices.any { it.id == choice }
        }
    }

    data class MultiChoice(
        override val questionId: ObjectId,
        val choice: List<Int>,
    ) : Answer {
        companion object;

        override fun validate(question: Question): Boolean {
            if (question !is Question.MultiChoice) return false
            val ids = question.choices.map { it.id }
            if (choice.size !in (question.minSelection ?: 1)..(question.maxSelection ?: Int.MAX_VALUE))
                return false
            return choice.any { it !in ids }
        }

    }

    data class Value(
        override val questionId: ObjectId,
        val value: Long,
    ) : Answer {
        companion object;

        override fun validate(question: Question): Boolean {
            if (question !is Question.Value) return false
            return value in question.minNumber .. question.maxNumber
        }
    }

    data class ValueRange(
        override val questionId: ObjectId,
        val minValue: Long,
        val maxValue: Long,
    ) : Answer {
        companion object;

        override fun validate(question: Question): Boolean {
            if (question !is Question.ValueRange) return false

            if (minValue !in question.minNumber .. question.maxNumber)
                return false
            if (maxValue !in question.minNumber .. question.maxNumber)
                return false

            return minValue <= maxValue
        }
    }

    data class CalendarSingle(
        override val questionId: ObjectId,
        val date: LocalDate,
    ) : Answer {
        companion object;

        override fun validate(question: Question): Boolean {
            if (question !is Question.CalendarSingle) return false
            return date in question.startDate..question.endDate
        }
    }

    data class CalendarRange(
        override val questionId: ObjectId,
        val startDate: LocalDate,
        val endDate: LocalDate,
    ) : Answer {
        companion object;

        override fun validate(question: Question): Boolean {
            if (question !is Question.CalendarSingle) return false
            if (startDate !in question.startDate..question.endDate) return false
            if (endDate !in question.startDate..question.endDate) return false
            return startDate <= endDate
        }
    }

    data class Rating(
        override val questionId: ObjectId,
        val choices: List<RankedChoice>,
    ) : Answer {
        companion object;
        override fun validate(question: Question): Boolean {
            if (question !is Question.Rating) return false
            val ids = question.choices.map { it.id }
            if (choices.any { it.choice !in ids }) return false

            val associate = choices.associate { it.rank to it.choice }
            if (associate.size != choices.size) return false

            for (i in choices.indices)
                if (associate[i] == null) return false
            return true
        }
    }

    companion object
}
