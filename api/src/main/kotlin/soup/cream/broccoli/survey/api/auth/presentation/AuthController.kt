package soup.cream.broccoli.survey.api.auth.presentation

import soup.cream.broccoli.survey.api.auth.dto.request.AuthenticateRequestDto
import soup.cream.broccoli.survey.api.auth.dto.request.CreateMemberRequestDto
import soup.cream.broccoli.survey.api.auth.dto.request.NewTokenRequestDto
import soup.cream.broccoli.survey.api.auth.dto.response.TokenResponseDto
import soup.cream.broccoli.survey.business.auth.service.AuthService
import soup.cream.broccoli.survey.common.response.ResponseData
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/login")
    fun authenticate(@RequestBody @Valid request: AuthenticateRequestDto): ResponseEntity<ResponseData<TokenResponseDto>> {
        val dto = authService.authenticate(request.credential, request.password)
        return ResponseData.ok(data = TokenResponseDto(dto.accessToken, dto.refreshToken))
    }

    @PostMapping("/register")
    fun createMember(@RequestBody @Valid request: CreateMemberRequestDto): ResponseEntity<ResponseData<Long>> {
        val userId = authService.createNewMember(request.name, request.credential, request.password)
        return ResponseData.ok(data = userId)
    }

    @PostMapping("/refresh")
    fun refreshAccessToken(@RequestBody @Valid request: NewTokenRequestDto): ResponseEntity<ResponseData<TokenResponseDto>> {
        val dto = authService.getNewToken(request.refreshToken)
        return ResponseData.ok(data = TokenResponseDto(dto.accessToken, dto.refreshToken))
    }
}
