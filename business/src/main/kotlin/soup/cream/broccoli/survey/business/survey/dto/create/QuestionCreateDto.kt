package soup.cream.broccoli.survey.business.survey.dto.create

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.DatabindContext
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver
import soup.cream.broccoli.survey.business.survey.dto.ChoiceDto
import soup.cream.broccoli.survey.business.survey.dto.QuestionType
import java.time.LocalDate

@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonTypeIdResolver(QuestionCreateDto.QuestionCreateDtoTypeResolver::class)
sealed interface QuestionCreateDto {
    val title: String
    val required: Boolean
    val type: QuestionType

    data class UserPromptCreateDto(
        override val title: String,
        override val required: Boolean,
        val placeholder: String?,
        val minLength: Long?,
        val maxLength: Long?,

        override val type: QuestionType = QuestionType.USER_PROMPT,
    ) : QuestionCreateDto

    data class SingleChoiceCreateDto(
        override val title: String,
        override val required: Boolean,
        val choices: List<ChoiceDto>,

        override val type: QuestionType = QuestionType.SINGLE_CHOICE,
    ) : QuestionCreateDto

    data class MultiChoiceCreateDto(
        override val title: String,
        override val required: Boolean,
        val choices: List<ChoiceDto>,
        val minSelection: Int,
        val maxSelection: Int,

        override val type: QuestionType = QuestionType.MULTI_CHOICE,
    ) : QuestionCreateDto

    data class ValueCreateDto(
        override val title: String,
        override val required: Boolean,
        val minNumber: Long,
        val maxNumber: Long,

        override val type: QuestionType = QuestionType.VALUE,
    ) : QuestionCreateDto

    data class ValueRangeCreateDto(
        override val title: String,
        override val required: Boolean,
        val minNumber: Long,
        val maxNumber: Long,

        override val type: QuestionType = QuestionType.VALUE_RANGE,
    ) : QuestionCreateDto

    data class CalendarSingleCreateDto(
        override val title: String,
        override val required: Boolean,
        val startDate: LocalDate,
        val endDate: LocalDate,

        override val type: QuestionType = QuestionType.CALENDAR_SINGLE,
    ) : QuestionCreateDto

    data class CalendarRangeCreateDto(
        override val title: String,
        override val required: Boolean,
        val startDate: LocalDate,
        val endDate: LocalDate,

        override val type: QuestionType = QuestionType.CALENDAR_RANGE,
    ) : QuestionCreateDto

    data class RatingCreateDto(
        override val title: String,
        override val required: Boolean,
        val choices: List<ChoiceDto>,

        override val type: QuestionType = QuestionType.RATING,
    ) : QuestionCreateDto

    class QuestionCreateDtoTypeResolver : TypeIdResolver {
        private lateinit var type: JavaType
        override fun init(baseType: JavaType) {
            type = baseType
        }

        override fun idFromValue(value: Any): String = (value as QuestionCreateDto).type.name
        override fun idFromValueAndType(value: Any, suggestedType: Class<*>?): String = idFromValue(value)
        override fun idFromBaseType(): String =
            throw UnsupportedOperationException("Question must have a specific type")


        override fun typeFromId(context: DatabindContext, id: String): JavaType {
            val type = when (QuestionType.valueOf(id)) {
                QuestionType.USER_PROMPT -> UserPromptCreateDto::class.java
                QuestionType.SINGLE_CHOICE -> SingleChoiceCreateDto::class.java
                QuestionType.MULTI_CHOICE -> MultiChoiceCreateDto::class.java
                QuestionType.VALUE -> ValueCreateDto::class.java
                QuestionType.VALUE_RANGE -> ValueRangeCreateDto::class.java
                QuestionType.CALENDAR_SINGLE -> CalendarSingleCreateDto::class.java
                QuestionType.CALENDAR_RANGE -> CalendarRangeCreateDto::class.java
                QuestionType.RATING -> RatingCreateDto::class.java
            }

            return context.constructType(type)
        }

        override fun getDescForKnownTypeIds(): String = "QuestionCreateDto"
        override fun getMechanism(): JsonTypeInfo.Id = JsonTypeInfo.Id.CUSTOM
    }
}
