package org.example.springbooturl.domain.chat.chat.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbooturl.domain.chat.chat.dto.ChatMessageDto;
import org.example.springbooturl.domain.chat.chat.entity.ChatRoom;
import org.example.springbooturl.domain.chat.chat.service.ChatService;
import org.example.springbooturl.global.rsData.RsData;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chatMessages")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1ChatMessageController {
    private final ChatService chatService;

    public record GetChatMessagesResponseBody(@NonNull List<ChatMessageDto> items) {
    }

    @GetMapping("/{roomId}")
    public RsData<GetChatMessagesResponseBody> getChatMessages(
            @PathVariable long roomId
    ) {
        ChatRoom chatRoom = chatService.findRoomById(roomId).get();
        List<ChatMessageDto> items = chatService.findMessagesByRoomId(roomId)
                .stream()
                .map(ChatMessageDto::new)
                .toList();

        return RsData.of(
                new GetChatMessagesResponseBody(items)
        );
    }
}
