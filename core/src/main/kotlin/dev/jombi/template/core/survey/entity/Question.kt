package dev.jombi.template.core.survey.entity

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.DatabindContext
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver
import dev.jombi.template.core.common.jackson.ObjectIdDeserializer
import dev.jombi.template.core.common.jackson.ObjectIdSerializer
import dev.jombi.template.core.survey.entity.consts.QuestionType
import org.bson.types.ObjectId
import java.time.LocalDate

@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonTypeIdResolver(Question.QuestionTypeResolver::class)
sealed interface Question {
    @get:JsonSerialize(using = ObjectIdSerializer::class)
    @get:JsonDeserialize(using = ObjectIdDeserializer::class)
    val id: ObjectId
    val title: String
    val required: Boolean
    val type: QuestionType

    data class UserPrompt(
        override val title: String,
        override val required: Boolean,
        val placeholder: String?,
        val minLength: Long?,
        val maxLength: Long?,

        override val type: QuestionType = QuestionType.USER_PROMPT,
        override val id: ObjectId = ObjectId.get(),
    ) : Question {
        companion object
    }

    data class SingleChoice(
        override val title: String,
        override val required: Boolean,
        val choices: List<Choice>,

        override val type: QuestionType = QuestionType.SINGLE_CHOICE,
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

        override val type: QuestionType = QuestionType.MULTI_CHOICE,
        override val id: ObjectId = ObjectId.get(),
    ) : Question {
        companion object
    }

    data class Value @JsonCreator constructor(
        override val title: String,
        override val required: Boolean,
        val minNumber: Long,
        val maxNumber: Long,

        override val type: QuestionType = QuestionType.VALUE,
        override val id: ObjectId = ObjectId.get(),
    ) : Question {
        companion object
    }

    data class ValueRange(
        override val title: String,
        override val required: Boolean,
        val minNumber: Long,
        val maxNumber: Long,

        override val type: QuestionType = QuestionType.VALUE_RANGE,
        override val id: ObjectId = ObjectId.get(),
    ) : Question {
        companion object
    }

    data class CalendarSingle(
        override val title: String,
        override val required: Boolean,
        val startDate: LocalDate,
        val endDate: LocalDate,

        override val type: QuestionType = QuestionType.CALENDAR_SINGLE,
        override val id: ObjectId = ObjectId.get(),
    ) : Question {
        companion object
    }

    data class CalendarRange(
        override val title: String,
        override val required: Boolean,
        val startDate: LocalDate,
        val endDate: LocalDate,

        override val type: QuestionType = QuestionType.CALENDAR_RANGE,
        override val id: ObjectId = ObjectId.get(),
    ) : Question {
        companion object
    }

    data class Rating(
        override val title: String,
        override val required: Boolean,
        val choices: List<Choice>,

        override val type: QuestionType = QuestionType.RATING,
        override val id: ObjectId = ObjectId.get(),
    ) : Question {
        companion object
    }

    companion object;

    object QuestionTypeResolver : TypeIdResolver {
        private lateinit var type: JavaType
        override fun init(baseType: JavaType) {
            type = baseType
        }

        override fun idFromValue(value: Any): String = (value as Question).type.name

        override fun idFromValueAndType(value: Any, suggestedType: Class<*>?): String = idFromValue(value)
        override fun idFromBaseType(): String =
            throw UnsupportedOperationException("Question must have a specific type")


        override fun typeFromId(context: DatabindContext, id: String): JavaType {
            val type = when (QuestionType.valueOf(id)) {
                QuestionType.USER_PROMPT -> UserPrompt::class.java
                QuestionType.SINGLE_CHOICE -> SingleChoice::class.java
                QuestionType.MULTI_CHOICE -> MultiChoice::class.java
                QuestionType.VALUE -> Value::class.java
                QuestionType.VALUE_RANGE -> ValueRange::class.java
                QuestionType.CALENDAR_SINGLE -> CalendarSingle::class.java
                QuestionType.CALENDAR_RANGE -> CalendarRange::class.java
                QuestionType.RATING -> Rating::class.java
            }

            return context.constructType(type)
        }

        override fun getDescForKnownTypeIds(): String = "Question"
        override fun getMechanism(): JsonTypeInfo.Id = JsonTypeInfo.Id.CUSTOM
    }
}
