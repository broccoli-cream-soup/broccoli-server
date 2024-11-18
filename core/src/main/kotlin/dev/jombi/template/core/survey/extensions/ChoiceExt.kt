package dev.jombi.template.core.survey.extensions

import dev.jombi.template.business.survey.dto.ChoiceDto
import dev.jombi.template.core.survey.entity.Choice

fun Choice.toDto() = ChoiceDto(
    name = name,
    id = id
)
