package soup.cream.broccoli.survey.infra.security.jwt

import soup.cream.broccoli.survey.core.auth.extern.TokenGenerator
import soup.cream.broccoli.survey.core.member.MemberHolder
import soup.cream.broccoli.survey.infra.security.details.MemberDetailsService
import io.jsonwebtoken.*
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import soup.cream.broccoli.survey.common.exception.CustomException
import soup.cream.broccoli.survey.common.exception.GlobalExceptionDetail
import soup.cream.broccoli.survey.core.auth.exception.AuthExceptionDetails
import java.time.Instant
import java.util.*

@Component
class JwtTokenManager(
    private val memberHolder: MemberHolder,
    private val jwtProperties: JwtProperties,
    private val memberDetailsService: MemberDetailsService
) : TokenGenerator, JwtValidator {
    override fun generateAccessToken(): String {
        return generateToken(JwtType.ACCESS)
    }

    override fun generateRefreshToken(): String {
        return generateToken(JwtType.REFRESH)
    }

    override fun refreshToNewToken(refreshToken: String): String {
        // parse the refreshToken
        // Check the token is 'REFRESH' state (if token is 'ACCESS' throw CustomException)
        val payload = getTokenValidate(refreshToken, JwtType.REFRESH)

        val memberDetails = memberDetailsService.loadUserByUsername(payload.subject.toString())

        // authenticate manually
        val auth = JwtAuthToken(memberDetails, listOf())
        SecurityContextHolder.getContext().authentication = auth
        return generateToken(JwtType.ACCESS)
    }

    override fun getTokenValidate(token: String, requiredType: JwtType): Claims {
        // throwing is acceptable: handling exception on both filter and advice
        try {
            val parser = Jwts.parser()
                .verifyWith(jwtProperties.secretKey)
                .build()
                .parse(token)
                .accept(Jws.CLAIMS)
            if (parser.header.type != requiredType.name)
                throw CustomException(AuthExceptionDetails.TOKEN_TYPE_MISMATCH)
            return parser.payload
        } catch (e: ExpiredJwtException) {
            throw CustomException(AuthExceptionDetails.EXPIRED_TOKEN)
        } catch (_: JwtException) {
        } catch (_: IllegalArgumentException) {
        } catch (e: Exception) {
            e.printStackTrace()
            throw CustomException(GlobalExceptionDetail.INTERNAL_SERVER_ERROR)
        }
        throw CustomException(AuthExceptionDetails.INVALID_TOKEN)
    }

    private fun generateToken(type: JwtType): String {
        val now = Instant.now()
        return Jwts.builder()
            .header().type(type.name).and()
            .subject(memberHolder.get().credential)
            .issuer(jwtProperties.issuer)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plusMillis(getExpireTime(type))))
            .signWith(jwtProperties.secretKey)
            .compact()
    }

    private fun getExpireTime(type: JwtType) = when (type) {
        JwtType.ACCESS -> jwtProperties.accessExpire
        JwtType.REFRESH -> jwtProperties.refreshExpire
    }
}
