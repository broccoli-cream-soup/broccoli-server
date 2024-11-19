package dev.jombi.template.core.common.id

import dev.jombi.template.core.common.entity.FetchableId

infix fun FetchableId.eq(other: Long) = id == other
infix fun FetchableId.eq(other: FetchableId) = id == other.id
infix fun FetchableId.ne(other: Long) = id != other
infix fun FetchableId.ne(other: FetchableId) = id != other.id
