package soup.cream.broccoli.survey.infra.exception

import com.fasterxml.jackson.databind.ObjectMapper
import soup.cream.broccoli.survey.common.exception.CustomException
import soup.cream.broccoli.survey.common.exception.ExceptionDetail
import soup.cream.broccoli.survey.common.exception.GlobalExceptionDetail
import soup.cream.broccoli.survey.common.exception.response.ResponseError
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.web.firewall.RequestRejectedException
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class AuthExceptionHandleFilter(private val mapper: ObjectMapper) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        try {
            chain.doFilter(request, response)
        } catch (e: RequestRejectedException){
            response.writeJson(GlobalExceptionDetail.UNPROCESSABLE_REQUEST) //header or data exception
        } catch (e: CustomException) {
            response.writeJson(e.detail, *e.formats)
        } catch (e: Exception) {
            e.printStackTrace()
            response.writeJson(GlobalExceptionDetail.INTERNAL_SERVER_ERROR)
        }
    }

    private fun HttpServletResponse.writeJson(detail: ExceptionDetail, vararg formats: Any?) {
        status = detail.status.value()
        val body = mapper.writeValueAsBytes(
            ResponseError(
                detail.code,
                detail.status.value(),
                detail.message.format(*formats)
            )
        )
        return outputStream.use {
            it.write(body)
            it.flush()
        }
    }
}
