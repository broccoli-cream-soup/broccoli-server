package soup.cream.broccoli.survey.core.common.entity

@JvmInline
value class IdLong(private val _id: Long?) : BaseId<Long> {
    override val get get() = _id!!

    companion object {
        val NULL = IdLong(null)
    }
}
