package soup.cream.broccoli.survey.core.member

import soup.cream.broccoli.survey.core.member.details.MemberDetails
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class MemberHolder {
    fun get() = (SecurityContextHolder.getContext().authentication.principal as MemberDetails).member
    fun getOrNull() = (SecurityContextHolder.getContext().authentication.principal as? MemberDetails)?.member
}
