package soup.cream.broccoli.survey.core.survey.extensions

import soup.cream.broccoli.survey.business.survey.dto.SurveyTypeDto
import soup.cream.broccoli.survey.core.survey.entity.consts.SurveyType

fun SurveyTypeDto.toConst() = SurveyType.valueOf(name)
fun SurveyType.toDto() = SurveyTypeDto.valueOf(name)
