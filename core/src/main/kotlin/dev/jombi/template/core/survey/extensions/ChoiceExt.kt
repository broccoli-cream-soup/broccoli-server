package dev.jombi.template.core.survey.extensions

import dev.jombi.template.business.survey.dto.ChoiceDto
import dev.jombi.template.core.survey.entity.Choice

fun Choice.toDto() = ChoiceDto(
    name = name,
    id = id
)

fun Choice.Companion.from(dto: ChoiceDto) = Choice(
    name = dto.name,
    id = dto.id,
)
