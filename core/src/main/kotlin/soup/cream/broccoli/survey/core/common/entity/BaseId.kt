package soup.cream.broccoli.survey.core.common.entity

import org.springframework.data.jpa.repository.JpaRepository
import kotlin.jvm.optionals.getOrNull

sealed interface BaseId<ID : Any> {
    val get: ID
    fun <T : Any> fetch(repository: JpaRepository<T, ID>) = repository.findById(get).getOrNull()
}
