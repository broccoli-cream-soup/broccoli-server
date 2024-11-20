package soup.cream.broccoli.survey.core.survey.entity

import soup.cream.broccoli.survey.core.survey.entity.consts.SurveyType
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("survey")
data class Survey(
    @Id
    val id: ObjectId = ObjectId.get(),
    val authorId: Long,

    val name: String,
    val description: String,

    val questions: Set<Question>,
    val surveyType: SurveyType = SurveyType.ANONYMOUS,
) { companion object }
