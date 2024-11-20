package soup.cream.broccoli.survey.common.exception.response

import soup.cream.broccoli.survey.common.exception.ExceptionDetail
import soup.cream.broccoli.survey.common.response.ResponseEmpty
import org.springframework.http.ResponseEntity

class ResponseError(code: String, status: Int, val detail: String) : ResponseEmpty(code, status) {
    companion object {
        fun of(message: ExceptionDetail, vararg args: Any?) =
            ResponseEntity
                .status(message.status)
                .body(ofRaw(message, *args))
        fun ofRaw(message: ExceptionDetail, vararg args: Any?) =
            ResponseError(message.code, message.status.value(), message.message.format(*args))
    }
}
