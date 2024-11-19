package dev.jombi.template.core.reply.entity

data class RankedChoice(
    val rank: Int,
    val choice: Int,
) { companion object }
