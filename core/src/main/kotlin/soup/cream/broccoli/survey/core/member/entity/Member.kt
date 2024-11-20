package soup.cream.broccoli.survey.core.member.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import soup.cream.broccoli.survey.core.common.entity.BaseTimeEntity
import soup.cream.broccoli.survey.core.common.entity.IdLong

@Entity(name = "tb_member")
data class Member(
    @Column(unique = true)
    val credential: String,

    @Column
    val password: String, // bcrypt

    @Column
    val name: String,

    @Id
    val id: IdLong = IdLong.NULL
) : BaseTimeEntity()
