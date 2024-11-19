package dev.jombi.template.core.reply.exception

import dev.jombi.template.common.exception.ExceptionDetail
import org.springframework.http.HttpStatus

enum class ReplyExceptionDetails(override val message: String, override val status: HttpStatus) : ExceptionDetail {
    REPLY_NOT_FOUND("응답을 찾을 수 없어요.", HttpStatus.NOT_FOUND),
    NOT_YOUR_REPLY("이 응답은 당신의 것이 아닙니다.", HttpStatus.UNAUTHORIZED),
    ;
    override val code: String = name
}
