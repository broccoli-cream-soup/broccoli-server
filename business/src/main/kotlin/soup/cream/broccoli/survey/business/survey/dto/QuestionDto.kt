package soup.cream.broccoli.survey.business.survey.dto

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.DatabindContext
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver
import java.time.LocalDate

@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonTypeIdResolver(QuestionDto.QuestionDtoTypeResolver::class)
sealed interface QuestionDto {
    val id: String
    val title: String
    val type: QuestionType
    val required: Boolean

    data class UserPromptDto(
        override val id: String,
        override val title: String,
        override val required: Boolean,
        val placeholder: String?,
        val minLength: Long?,
        val maxLength: Long?,

        override val type: QuestionType = QuestionType.USER_PROMPT,
        ) : QuestionDto

    data class SingleChoiceDto(
        override val id: String,
        override val title: String,
        override val required: Boolean,
        val choices: List<ChoiceDto>,

        override val type: QuestionType = QuestionType.SINGLE_CHOICE,
    ) : QuestionDto

    data class MultiChoiceDto(
        override val id: String,
        override val title: String,
        override val required: Boolean,
        val choices: List<ChoiceDto>,
        val minSelection: Int?,
        val maxSelection: Int?,

        override val type: QuestionType = QuestionType.MULTI_CHOICE,
    ) : QuestionDto

    data class ValueDto(
        override val id: String,
        override val title: String,
        override val required: Boolean,
        val minNumber: Long,
        val maxNumber: Long,

        override val type: QuestionType = QuestionType.VALUE,
    ) : QuestionDto

    data class ValueRangeDto(
        override val id: String,
        override val title: String,
        override val required: Boolean,
        val minNumber: Long,
        val maxNumber: Long,

        override val type: QuestionType = QuestionType.VALUE_RANGE,
    ) : QuestionDto

    data class CalendarSingleDto(
        override val id: String,
        override val title: String,
        override val required: Boolean,
        val startDate: LocalDate,
        val endDate: LocalDate,

        override val type: QuestionType = QuestionType.CALENDAR_SINGLE,
    ) : QuestionDto

    data class CalendarRangeDto(
        override val id: String,
        override val title: String,
        override val required: Boolean,
        val startDate: LocalDate,
        val endDate: LocalDate,

        override val type: QuestionType = QuestionType.CALENDAR_RANGE,
    ) : QuestionDto

    data class RatingDto(
        override val id: String,
        override val title: String,
        override val required: Boolean,
        val choices: List<ChoiceDto>,

        override val type: QuestionType = QuestionType.RATING,
    ) : QuestionDto

    class QuestionDtoTypeResolver : TypeIdResolver {
        private lateinit var type: JavaType
        override fun init(baseType: JavaType) {
            type = baseType
        }

        override fun idFromValue(value: Any): String = (value as QuestionDto).type.name
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
