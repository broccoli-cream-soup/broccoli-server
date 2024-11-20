package soup.cream.broccoli.survey.core.member.repository

import soup.cream.broccoli.survey.core.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberJpaRepository : JpaRepository<Member, Long> {
    fun existsByCredential(credential: String): Boolean
    fun findMemberByCredential(credential: String): Member? // uniq
}
