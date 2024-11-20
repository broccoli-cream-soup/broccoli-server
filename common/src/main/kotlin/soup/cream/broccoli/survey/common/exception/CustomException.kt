package soup.cream.broccoli.survey.common.exception

class CustomException(val detail: ExceptionDetail, vararg val formats: Any?) : RuntimeException()
