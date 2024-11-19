package dev.jombi.template.core.survey.repository

import dev.jombi.template.core.survey.entity.Survey
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SurveyRepository : MongoRepository<Survey, ObjectId> {
    @Query
    fun discover(): List<SurveyInfo>
}
