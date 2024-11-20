package soup.cream.broccoli.survey.core.common.id

import soup.cream.broccoli.survey.core.common.entity.BaseId

infix fun <ID : Any> BaseId<ID>.eq(other: ID) = get == other
infix fun <ID : Any> BaseId<ID>.eq(other: BaseId<ID>) = get == other.get
infix fun <ID : Any> BaseId<ID>.ne(other: ID) = get != other
infix fun <ID : Any> BaseId<ID>.ne(other: BaseId<ID>) = get != other.get
