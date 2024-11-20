package soup.cream.broccoli.survey.core.member.service

import soup.cream.broccoli.survey.core.member.MemberHolder
import soup.cream.broccoli.survey.business.member.dto.MemberDto
import soup.cream.broccoli.survey.business.member.service.MemberService
import org.springframework.stereotype.Service

@Service
class MemberServiceImpl(
    private val memberHolder: MemberHolder
) : MemberService {
    override fun me(): MemberDto {
        val member = memberHolder.get()
        return MemberDto(member.name)
    }
}
