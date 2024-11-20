package soup.cream.broccoli.survey.business.survey.dto

data class SurveyInfoDto(
    val id: String,
    val author: String,

    val name: String,
    val description: String,

    val surveyType: SurveyTypeDto = SurveyTypeDto.ANONYMOUS,
)
