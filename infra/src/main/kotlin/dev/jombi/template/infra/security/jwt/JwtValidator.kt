package dev.jombi.template.infra.security.jwt

import io.jsonwebtoken.Jwt

interface JwtValidator {
   fun getTokenValidate(token: String): Jwt<*, *>?
}