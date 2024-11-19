package dev.jombi.template.core.reply.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Reply(
    @Id
    val id: ObjectId,
    val surveyId: ObjectId,
    val userId: Long?,
    val answers: Set<Answer>
) { companion object }
