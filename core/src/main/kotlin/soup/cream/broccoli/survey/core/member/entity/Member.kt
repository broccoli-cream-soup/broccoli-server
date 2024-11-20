package soup.cream.broccoli.survey.core.member.entity

import jakarta.persistence.*
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: IdLong = IdLong.NULL
) : BaseTimeEntity()
