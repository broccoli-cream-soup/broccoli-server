package soup.cream.broccoli.survey.business.reply.dto

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.DatabindContext
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver
import soup.cream.broccoli.survey.business.survey.dto.QuestionType
import java.time.LocalDate

@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonTypeIdResolver(AnswerDto.AnswerDtoTypeResolver::class)
sealed interface AnswerDto {
    val questionId: String
    val type: QuestionType

    data class UserPromptDto(
        override val questionId: String,
        val prompt: String,

        override val type: QuestionType = QuestionType.USER_PROMPT,
    ) : AnswerDto

    data class SingleChoiceDto(
        override val questionId: String,
        val choice: Int,

        override val type: QuestionType = QuestionType.SINGLE_CHOICE,
    ) : AnswerDto

    data class MultiChoiceDto(
        override val questionId: String,
        val choice: List<Int>,

        override val type: QuestionType = QuestionType.MULTI_CHOICE,
    ) : AnswerDto

    data class ValueDto(
        override val questionId: String,
        val value: Long,

        override val type: QuestionType = QuestionType.VALUE,
    ) : AnswerDto

    data class ValueRangeDto(
        override val questionId: String,
        val minValue: Long,
        val maxValue: Long,

        override val type: QuestionType = QuestionType.VALUE_RANGE,
    ) : AnswerDto

    data class CalendarSingleDto(
        override val questionId: String,
        val date: LocalDate,

        override val type: QuestionType = QuestionType.CALENDAR_SINGLE,
    ) : AnswerDto

    data class CalendarRangeDto(
        override val questionId: String,
        val startDate: LocalDate,
        val endDate: LocalDate,

        override val type: QuestionType = QuestionType.CALENDAR_RANGE,
    ) : AnswerDto

    data class RatingDto(
        override val questionId: String,
        val choices: List<RankedChoiceDto>,

        override val type: QuestionType = QuestionType.RATING,
    ) : AnswerDto

    class AnswerDtoTypeResolver : TypeIdResolver {
        private lateinit var type: JavaType
        override fun init(baseType: JavaType) {
            type = baseType
        }

        override fun idFromValue(value: Any): String = (value as AnswerDto).type.name
        override fun idFromValueAndType(value: Any, suggestedType: Class<*>?): String = idFromValue(value)
        override fun idFromBaseType(): String =
            throw UnsupportedOperationException("Question must have a specific type")


        override fun typeFromId(context: DatabindContext, id: String): JavaType {
            val type = when (QuestionType.valueOf(id)) {
                QuestionType.USER_PROMPT -> UserPromptDto::class.java
                QuestionType.SINGLE_CHOICE -> SingleChoiceDto::class.java
                QuestionType.MULTI_CHOICE -> MultiChoiceDto::class.java
                QuestionType.VALUE -> ValueDto::class.java
                QuestionType.VALUE_RANGE -> ValueRangeDto::class.java
                QuestionType.CALENDAR_SINGLE -> CalendarSingleDto::class.java
                QuestionType.CALENDAR_RANGE -> CalendarRangeDto::class.java
                QuestionType.RATING -> RatingDto::class.java
            }

            return context.constructType(type)
        }

        override fun getDescForKnownTypeIds(): String = "QuestionDto"
        override fun getMechanism(): JsonTypeInfo.Id = JsonTypeInfo.Id.CUSTOM
    }
}
