package dev.jombi.template.core.survey.extensions

import dev.jombi.template.business.survey.dto.SurveyTypeDto
import dev.jombi.template.core.survey.entity.consts.SurveyType

fun SurveyTypeDto.toConst() = SurveyType.valueOf(name)
fun SurveyType.toDto() = SurveyTypeDto.valueOf(name)
