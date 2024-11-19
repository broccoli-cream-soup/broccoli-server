package dev.jombi.template.core.reply.entity

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import org.bson.types.ObjectId
import java.time.LocalDate

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
sealed interface Answer {
    val questionId: ObjectId
    @JsonTypeName("USER_PROMPT")
    data class UserPrompt(
        override val questionId: ObjectId,
        val prompt: String,
    ) : Answer { companion object }

    @JsonTypeName("SINGLE_CHOICE")
    data class SingleChoice(
        override val questionId: ObjectId,
        val choice: Long,
    ) : Answer { companion object }

    @JsonTypeName("MULTI_CHOICE")
    data class MultiChoice(
        override val questionId: ObjectId,
        val choice: List<Long>,
    ) : Answer { companion object }

    @JsonTypeName("VALUE")
    data class Value(
        override val questionId: ObjectId,
        val value: Long,
    ) : Answer { companion object }

    @JsonTypeName("VALUE_RANGE")
    data class ValueRange(
        override val questionId: ObjectId,
        val minValue: Long,
        val maxValue: Long,
    ) : Answer { companion object }

    @JsonTypeName("CALENDAR_SINGLE")
    data class CalendarSingle(
        override val questionId: ObjectId,
        val date: LocalDate,
    ) : Answer { companion object }

    @JsonTypeName("CALENDAR_RANGE")
    data class CalendarRange(
        override val questionId: ObjectId,
        val startDate: LocalDate,
        val endDate: LocalDate,
    ) : Answer { companion object }

    @JsonTypeName("RATING")
    data class Rating(
        override val questionId: ObjectId,
        val choices: List<RankedChoice>,
    ) : Answer { companion object }
    companion object
}
