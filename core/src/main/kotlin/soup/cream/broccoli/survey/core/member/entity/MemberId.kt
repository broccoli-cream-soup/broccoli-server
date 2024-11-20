package soup.cream.broccoli.survey.core.member.entity

import soup.cream.broccoli.survey.core.member.repository.MemberJpaRepository
import kotlin.jvm.optionals.getOrNull

@JvmInline
value class MemberId(val id: Long) {
    fun fetch(repository: MemberJpaRepository) = repository.findById(id).getOrNull()
}
