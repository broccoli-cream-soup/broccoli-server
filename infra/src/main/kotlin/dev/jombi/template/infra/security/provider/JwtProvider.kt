package dev.jombi.template.infra.security.provider

import dev.jombi.template.core.auth.exception.AuthExceptionDetails
import dev.jombi.template.infra.security.details.MemberDetailsService
import dev.jombi.template.infra.security.jwt.JwtAuthToken
import dev.jombi.template.infra.security.jwt.JwtType
import dev.jombi.template.infra.security.jwt.JwtValidator
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class JwtProvider(
    private val jwtValidator: JwtValidator,
    private val memberDetailsService: MemberDetailsService
) : AuthenticationProvider {

    //여기서는 우리 jwt 가 맞는지 확인 후 맞으면 userDetails/ authorities return
    //else -> auth error throw
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