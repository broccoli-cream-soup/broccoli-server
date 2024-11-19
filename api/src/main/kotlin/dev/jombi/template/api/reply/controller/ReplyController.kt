package dev.jombi.template.api.reply.controller

import dev.jombi.template.business.reply.dto.AnswerDto
import dev.jombi.template.business.reply.dto.ReplyCreateDto
import dev.jombi.template.business.reply.dto.ReplyDto
import dev.jombi.template.business.reply.service.ReplyService
import dev.jombi.template.common.response.ResponseData
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
        @RequestBody dto: AnswerDto,
    ): ResponseEntity<ResponseData<ReplyDto>> {
        val res = replyService.editReply(id, dto)
        return ResponseData.ok(data = res)
    }
}
