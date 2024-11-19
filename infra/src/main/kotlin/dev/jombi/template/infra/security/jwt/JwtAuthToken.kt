package dev.jombi.template.infra.security.jwt

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class JwtAuthToken : AbstractAuthenticationToken {
    private val principal: Any

    constructor(details: UserDetails, authorities: List<GrantedAuthority>) : super(authorities) {
        this.principal = details
        super.setAuthenticated(true)
    }

    constructor(jwt: String) : super(null) {
        this.principal = jwt
        super.setAuthenticated(false)
    }

    override fun setAuthenticated(authenticated: Boolean) {

    }

    override fun getCredentials(): Any? = null
    override fun getPrincipal(): Any = principal
}