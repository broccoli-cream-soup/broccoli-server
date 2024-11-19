package dev.jombi.template.core.reply.extensions

import dev.jombi.template.business.reply.dto.RankedChoiceDto
import dev.jombi.template.core.reply.entity.RankedChoice

fun RankedChoice.Companion.from(dto: RankedChoiceDto) = RankedChoice(
    rank = dto.rank,
    choice = dto.choice
)
fun RankedChoice.toDto() = RankedChoiceDto(
    rank = rank,
    choice = choice
)
