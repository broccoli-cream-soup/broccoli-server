package dev.jombi.template.api.reply.controller

import dev.jombi.template.business.reply.dto.AnswerDto
import dev.jombi.template.business.reply.dto.ReplyCreateDto
import dev.jombi.template.business.reply.service.ReplyService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/reply")
class ReplyController(
    private val replyService: ReplyService,
) {
    @GetMapping("/me")
    fun getMyReplies() = replyService.getReplyByMe()

    @PostMapping
    fun addReply(@RequestBody createDto: ReplyCreateDto) = replyService.createReply(createDto)

    @PatchMapping("/{id}")
    fun editAnswerOnReply(@PathVariable id: String, @RequestBody dto: AnswerDto) = replyService.editReply(id, dto)
}
