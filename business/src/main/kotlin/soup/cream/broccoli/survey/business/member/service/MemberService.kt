package soup.cream.broccoli.survey.business.member.service

import soup.cream.broccoli.survey.business.member.dto.MemberDto

interface MemberService {
    fun me(): MemberDto
}
