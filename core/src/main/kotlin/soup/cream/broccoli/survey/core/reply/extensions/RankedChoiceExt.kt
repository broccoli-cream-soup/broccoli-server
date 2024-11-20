package soup.cream.broccoli.survey.core.reply.extensions

import soup.cream.broccoli.survey.business.reply.dto.RankedChoiceDto
import soup.cream.broccoli.survey.core.reply.entity.RankedChoice

fun RankedChoice.Companion.from(dto: RankedChoiceDto) = RankedChoice(
    rank = dto.rank,
    choice = dto.choice
)
fun RankedChoice.toDto() = RankedChoiceDto(
    rank = rank,
    choice = choice
)
