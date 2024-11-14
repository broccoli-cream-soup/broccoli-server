package dev.jombi.template.infra.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws

interface JwtValidator {
   fun getTokenValidate(token: String): Jws<Claims>?
}