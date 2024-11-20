package soup.cream.broccoli.survey.business.auth.service

import soup.cream.broccoli.survey.business.auth.dto.TokenDto

interface AuthService {
    fun authenticate(credential: String, password: String): TokenDto
    fun createNewMember(name: String, credential: String, password: String): Long
    fun getNewToken(refreshToken: String): TokenDto
}
