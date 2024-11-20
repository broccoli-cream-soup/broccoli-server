package soup.cream.broccoli.survey.core.reply.exception

import soup.cream.broccoli.survey.common.exception.ExceptionDetail
import org.springframework.http.HttpStatus

enum class ReplyExceptionDetails(override val message: String, override val status: HttpStatus) : ExceptionDetail {
    REPLY_NOT_FOUND("응답을 찾을 수 없어요.", HttpStatus.NOT_FOUND),
    NOT_YOUR_REPLY("이 응답은 당신의 것이 아닙니다.", HttpStatus.UNAUTHORIZED),

    REPLY_NOT_ACCEPTABLE("질문 '%s'에 상응하는 대답을 처리할 수 없습니다.", HttpStatus.BAD_REQUEST),
    REPLY_NOT_ACCEPTABLE_ANONYMOUS("응답을 처리할 수 없습니다: 로그인이 필요합니다.", HttpStatus.BAD_REQUEST),
    REPLY_NOT_ACCEPTABLE_DUPLICATE("응답을 처리할 수 없습니다: 이미 응답한 설문지 입니다.", HttpStatus.BAD_REQUEST),

    REPLY_NOT_ACCEPTABLE_UNRESOLVED("응답을 처리할 수 없습니다: 처리되지 않은 응답 %s이 있습니다.", HttpStatus.BAD_REQUEST),

    REPLY_NOT_ACCEPTABLE_ID_NOT_FOUND("응답을 처리할 수 없습니다: 질문 '%s'(은)는 설문지에 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    REPLY_NOT_ACCEPTABLE_TYPE_MISMATCH("응답을 처리할 수 없습니다: 질문 '%s'에 상응하는 대답의 타입이 서로 일치 하지 않습니다. (받은 타입: %s, 필요한 타입: %s)", HttpStatus.BAD_REQUEST),
    ;

    override val code: String = name
}
