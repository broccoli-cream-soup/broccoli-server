package soup.cream.broccoli.survey.infra.security.details

import soup.cream.broccoli.survey.core.member.details.MemberDetails
import soup.cream.broccoli.survey.core.member.repository.MemberJpaRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class MemberDetailsService(
    private val memberRepository: MemberJpaRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val member = memberRepository.findMemberByCredential(username)
            ?: throw UsernameNotFoundException(username)

        return MemberDetails(member)
    }
}
