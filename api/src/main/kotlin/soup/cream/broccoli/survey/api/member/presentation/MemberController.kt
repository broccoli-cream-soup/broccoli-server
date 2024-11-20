package soup.cream.broccoli.survey.api.member.presentation

import soup.cream.broccoli.survey.api.member.dto.response.MemberInfoResponseDto
import soup.cream.broccoli.survey.business.member.service.MemberService
import soup.cream.broccoli.survey.common.response.ResponseData
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
class MemberController(
    private val memberService: MemberService
) {
    @GetMapping("/me")
    fun me(): ResponseEntity<ResponseData<MemberInfoResponseDto>> {
        val me = memberService.me()

        return ResponseData.ok(data = MemberInfoResponseDto(me.name))
    }
}
