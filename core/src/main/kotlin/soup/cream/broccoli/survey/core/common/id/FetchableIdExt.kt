package soup.cream.broccoli.survey.core.common.id

import soup.cream.broccoli.survey.core.common.entity.FetchableId

infix fun FetchableId.eq(other: Long) = id == other
infix fun FetchableId.eq(other: FetchableId) = id == other.id
infix fun FetchableId.ne(other: Long) = id != other
infix fun FetchableId.ne(other: FetchableId) = id != other.id
