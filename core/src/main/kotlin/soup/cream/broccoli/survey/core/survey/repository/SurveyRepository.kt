package soup.cream.broccoli.survey.core.survey.repository

import soup.cream.broccoli.survey.core.survey.entity.Survey
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface SurveyRepository : MongoRepository<Survey, ObjectId> {
    fun findAllBy(): List<SurveyInfo>
}
