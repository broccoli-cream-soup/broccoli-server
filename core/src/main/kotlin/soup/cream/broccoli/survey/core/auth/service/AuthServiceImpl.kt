package soup.cream.broccoli.survey.core.auth.service

import soup.cream.broccoli.survey.business.auth.dto.TokenDto
import soup.cream.broccoli.survey.core.auth.extern.TokenGenerator
import soup.cream.broccoli.survey.business.auth.service.AuthService
import soup.cream.broccoli.survey.common.exception.CustomException
import soup.cream.broccoli.survey.core.auth.exception.AuthExceptionDetails
import soup.cream.broccoli.survey.core.member.entity.Member
import soup.cream.broccoli.survey.core.member.repository.MemberJpaRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val memberRepository: MemberJpaRepository,
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder,
    private val tokenGenerator: TokenGenerator,
) : AuthService {
    override fun authenticate(credential: String, password: String): TokenDto {
        val token = UsernamePasswordAuthenticationToken(credential, password)

        val auth = authenticationManager.authenticate(token)
        SecurityContextHolder.getContext().authentication = auth

        val access = tokenGenerator.generateAccessToken()
        val refresh = tokenGenerator.generateRefreshToken()

        return TokenDto(access, refresh)
    }

    override fun createNewMember(name: String, credential: String, password: String): Long {
        if (memberRepository.existsByCredential(credential))
            throw CustomException(AuthExceptionDetails.USER_ALREADY_EXISTS, credential)

        return memberRepository.save(Member(credential, passwordEncoder.encode(password), name))
            .id.get
    }

    override fun getNewToken(refreshToken: String): TokenDto {
        val newAccessToken = tokenGenerator.refreshToNewToken(refreshToken)
        return TokenDto(
            newAccessToken,
            refreshToken // no changes ;)
        )
    }
}
