package soup.cream.broccoli.survey.business.reply.dto

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.DatabindContext
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver
import soup.cream.broccoli.survey.business.survey.dto.QuestionType
import java.time.LocalDate

@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonTypeIdResolver(soup.cream.broccoli.survey.business.reply.dto.AnswerDto.AnswerDtoTypeResolver::class)
sealed interface AnswerDto {
    val questionId: String
    val type: QuestionType

    data class UserPromptDto(
        override val questionId: String,
        val prompt: String,

        override val type: QuestionType = QuestionType.USER_PROMPT,
    ) : soup.cream.broccoli.survey.business.reply.dto.AnswerDto

    data class SingleChoiceDto(
        override val questionId: String,
        val choice: Int,

        override val type: QuestionType = QuestionType.SINGLE_CHOICE,
    ) : soup.cream.broccoli.survey.business.reply.dto.AnswerDto

    data class MultiChoiceDto(
        override val questionId: String,
        val choice: List<Int>,

        override val type: QuestionType = QuestionType.MULTI_CHOICE,
    ) : soup.cream.broccoli.survey.business.reply.dto.AnswerDto

    data class ValueDto(
        override val questionId: String,
        val value: Long,

        override val type: QuestionType = QuestionType.VALUE,
    ) : soup.cream.broccoli.survey.business.reply.dto.AnswerDto

    data class ValueRangeDto(
        override val questionId: String,
        val minValue: Long,
        val maxValue: Long,

        override val type: QuestionType = QuestionType.VALUE_RANGE,
    ) : soup.cream.broccoli.survey.business.reply.dto.AnswerDto

    data class CalendarSingleDto(
        override val questionId: String,
        val date: LocalDate,

        override val type: QuestionType = QuestionType.CALENDAR_SINGLE,
    ) : soup.cream.broccoli.survey.business.reply.dto.AnswerDto

    data class CalendarRangeDto(
        override val questionId: String,
        val startDate: LocalDate,
        val endDate: LocalDate,

        override val type: QuestionType = QuestionType.CALENDAR_RANGE,
    ) : soup.cream.broccoli.survey.business.reply.dto.AnswerDto

    data class RatingDto(
        override val questionId: String,
        val choices: List<soup.cream.broccoli.survey.business.reply.dto.RankedChoiceDto>,

        override val type: QuestionType = QuestionType.RATING,
    ) : soup.cream.broccoli.survey.business.reply.dto.AnswerDto

    class AnswerDtoTypeResolver : TypeIdResolver {
        private lateinit var type: JavaType
        override fun init(baseType: JavaType) {
            type = baseType
        }

        override fun idFromValue(value: Any): String = (value as soup.cream.broccoli.survey.business.reply.dto.AnswerDto).type.name
        override fun idFromValueAndType(value: Any, suggestedType: Class<*>?): String = idFromValue(value)
        override fun idFromBaseType(): String =
            throw UnsupportedOperationException("Question must have a specific type")


        override fun typeFromId(context: DatabindContext, id: String): JavaType {
            val type = when (QuestionType.valueOf(id)) {
                QuestionType.USER_PROMPT -> soup.cream.broccoli.survey.business.reply.dto.AnswerDto.UserPromptDto::class.java
                QuestionType.SINGLE_CHOICE -> soup.cream.broccoli.survey.business.reply.dto.AnswerDto.SingleChoiceDto::class.java
                QuestionType.MULTI_CHOICE -> soup.cream.broccoli.survey.business.reply.dto.AnswerDto.MultiChoiceDto::class.java
                QuestionType.VALUE -> soup.cream.broccoli.survey.business.reply.dto.AnswerDto.ValueDto::class.java
                QuestionType.VALUE_RANGE -> soup.cream.broccoli.survey.business.reply.dto.AnswerDto.ValueRangeDto::class.java
                QuestionType.CALENDAR_SINGLE -> soup.cream.broccoli.survey.business.reply.dto.AnswerDto.CalendarSingleDto::class.java
                QuestionType.CALENDAR_RANGE -> soup.cream.broccoli.survey.business.reply.dto.AnswerDto.CalendarRangeDto::class.java
                QuestionType.RATING -> soup.cream.broccoli.survey.business.reply.dto.AnswerDto.RatingDto::class.java
            }

            return context.constructType(type)
        }

        override fun getDescForKnownTypeIds(): String = "QuestionDto"
        override fun getMechanism(): JsonTypeInfo.Id = JsonTypeInfo.Id.CUSTOM
    }
}
