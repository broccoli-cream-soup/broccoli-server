package dev.jombi.template.infra.security.jwt

import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import javax.crypto.SecretKey


@Configuration
class JwtProperties(
    @Value("\${app.jwt.issuer}")
    val issuer: String,
    @Value("\${app.jwt.access_expires_after}")
    val accessExpire: Long,
    @Value("\${app.jwt.refresh_expires_after}")
    val refreshExpire: Long,
    @Value("\${app.jwt.secret}")
    private val key: String
) {
    var secretKey: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key))
}