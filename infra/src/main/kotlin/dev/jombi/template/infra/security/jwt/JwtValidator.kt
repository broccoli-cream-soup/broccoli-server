package dev.jombi.template.infra.security.jwt

import io.jsonwebtoken.Claims

interface JwtValidator {
   fun getTokenValidate(token: String, requiredType: JwtType): Claims?
}