package soup.cream.broccoli.survey.infra.security.jwt

import soup.cream.broccoli.survey.common.exception.CustomException
import soup.cream.broccoli.survey.core.auth.exception.AuthExceptionDetails
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val authenticationManager: AuthenticationManager,
    ) : OncePerRequestFilter() {
    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BEARER_PREFIX = "Bearer "
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt = resolveToken(request)

        if (!jwt.isNullOrEmpty()) {
            try {
                val authToken = JwtAuthToken(jwt)
                val authentication = authenticationManager.authenticate(authToken)
                SecurityContextHolder.getContext().authentication = authentication
            } catch (e: AuthenticationException) {
                SecurityContextHolder.clearContext()
                throw CustomException(AuthExceptionDetails.INVALID_TOKEN)
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(AUTHORIZATION_HEADER)
        return if (!bearerToken.isNullOrEmpty() && bearerToken.startsWith(BEARER_PREFIX)) {
            bearerToken.substring(BEARER_PREFIX.length)
        } else null
    }
}
