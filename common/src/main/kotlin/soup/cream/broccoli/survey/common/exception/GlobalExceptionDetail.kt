package soup.cream.broccoli.survey.common.exception

import org.springframework.http.HttpStatus

enum class GlobalExceptionDetail(override val message: String, override val status: HttpStatus) : ExceptionDetail {
    INTERNAL_SERVER_ERROR("내부 서버 에러", HttpStatus.INTERNAL_SERVER_ERROR),

    PARAMETER_NOT_MATCH("파라미터 불일치: %s (%s)", HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND("리소스 '/%s' 를 찾을 수 없음", HttpStatus.NOT_FOUND),
    UNPROCESSABLE_BODY("요청 바디가 없거나 읽을 수 없음", HttpStatus.BAD_REQUEST),
    UNSUPPORTED_MEDIA_TYPE("미디어 '%s'는 지원되지 않음. (지원하는 미디어: '%s')", HttpStatus.BAD_REQUEST),
    METHOD_NOT_SUPPORTED("메소드 '%s'(은)는 지원되지 않음. (지원하는 메소드: '%s')", HttpStatus.NOT_FOUND),
    UNPROCESSABLE_REQUEST("요청 헤더 또는 데이터가 잘못되었거나 처리할 수 없음", HttpStatus.BAD_REQUEST),
    ACCESS_DENIED("권한이 없음", HttpStatus.FORBIDDEN),
    ;

    override val code = name

}
