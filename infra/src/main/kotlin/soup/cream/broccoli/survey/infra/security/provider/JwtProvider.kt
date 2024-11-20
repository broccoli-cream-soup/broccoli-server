package soup.cream.broccoli.survey.infra.security.provider

import soup.cream.broccoli.survey.core.auth.exception.AuthExceptionDetails
import soup.cream.broccoli.survey.infra.security.details.MemberDetailsService
import soup.cream.broccoli.survey.infra.security.jwt.JwtAuthToken
import soup.cream.broccoli.survey.infra.security.jwt.JwtType
import soup.cream.broccoli.survey.infra.security.jwt.JwtValidator
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class JwtProvider(
    private val jwtValidator: JwtValidator,
    private val memberDetailsService: MemberDetailsService
) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication): Authentication {
        // JwtAuthToken
        if (authentication.isAuthenticated) return authentication

        val jwt = authentication.principal as String
        val validated = jwtValidator.getTokenValidate(jwt, JwtType.ACCESS)
            ?: throw BadCredentialsException(AuthExceptionDetails.INVALID_TOKEN.message)

        val username = validated.subject
        val memberDetails = memberDetailsService.loadUserByUsername(username)

        return JwtAuthToken(memberDetails, listOf())
    }

    override fun supports(authentication: Class<*>?): Boolean {
        val clazz = authentication ?: return false
        return JwtAuthToken::class.java == clazz
    }

}
