package soup.cream.broccoli.survey.core.survey.extensions

import soup.cream.broccoli.survey.business.survey.dto.ChoiceDto
import soup.cream.broccoli.survey.core.survey.entity.Choice

fun Choice.toDto() = ChoiceDto(
    name = name,
    id = id
)

fun Choice.Companion.from(dto: ChoiceDto) = Choice(
    name = dto.name,
    id = dto.id,
)
