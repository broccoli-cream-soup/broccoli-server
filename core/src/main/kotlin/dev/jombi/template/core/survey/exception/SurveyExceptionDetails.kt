package dev.jombi.template.core.survey.exception

import dev.jombi.template.common.exception.ExceptionDetail
import org.springframework.http.HttpStatus

enum class SurveyExceptionDetails(override val message: String, override val status: HttpStatus) : ExceptionDetail {
    SURVEY_NOT_FOUND("아이디가 '%s'인 설문을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_YOUR_SURVEY("아이디가 '%s'인 설문은 당신의 설문이 아닙니다.", HttpStatus.UNAUTHORIZED),

    QUESTION_NOT_FOUND("설문에서 아이디가 '%s'인 질문을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    QUESTION_TYPE_MISMATCH("질문 '%s'의 질문 타입이 기존과 다릅니다. (필요: %s).", HttpStatus.BAD_REQUEST),
    ;

    override val code: String get() = name
}
