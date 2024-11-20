package soup.cream.broccoli.survey.api.reply.controller

import soup.cream.broccoli.survey.business.reply.dto.AnswerDto
import soup.cream.broccoli.survey.business.reply.dto.ReplyCreateDto
import soup.cream.broccoli.survey.business.reply.dto.ReplyDto
import soup.cream.broccoli.survey.business.reply.service.ReplyService
import soup.cream.broccoli.survey.common.response.ResponseData
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/reply")
class ReplyController(
    private val replyService: ReplyService,
) {
    @GetMapping("/me")
    fun getMyReplies(): ResponseEntity<ResponseData<List<ReplyDto>>> {
        val res = replyService.getReplyByMe()
        return ResponseData.ok(data = res)
    }

    @PostMapping
    fun addReply(@RequestBody createDto: ReplyCreateDto): ResponseEntity<ResponseData<ReplyDto>> {
        val res = replyService.createReply(createDto)
        return ResponseData.ok(data = res)
    }

    @PatchMapping("/{id}")
    fun editAnswerOnReply(
        @PathVariable id: String,
        @RequestBody dto: soup.cream.broccoli.survey.business.reply.dto.AnswerDto,
    ): ResponseEntity<ResponseData<ReplyDto>> {
        val res = replyService.editReply(id, dto)
        return ResponseData.ok(data = res)
    }
}
