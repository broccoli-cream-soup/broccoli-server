package soup.cream.broccoli.survey.core.common.entity

import org.springframework.data.jpa.repository.JpaRepository
import kotlin.jvm.optionals.getOrNull

@JvmInline
value class FetchableId(private val _id: Long? = null) {
    val id get() = _id!!
    fun <T : Any> fetch(repository: JpaRepository<T, Long>) = repository.findById(id).getOrNull()

    companion object {
        val NULL = FetchableId(null)
    }
}
