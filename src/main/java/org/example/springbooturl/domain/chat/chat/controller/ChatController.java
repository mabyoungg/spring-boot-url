package org.example.springbooturl.domain.chat.chat.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbooturl.domain.chat.chat.dto.ChatMessageDto;
import org.example.springbooturl.domain.chat.chat.entity.ChatMessage;
import org.example.springbooturl.domain.chat.chat.entity.ChatRoom;
import org.example.springbooturl.domain.chat.chat.service.ChatService;
import org.example.springbooturl.domain.member.member.entity.Member;
import org.example.springbooturl.domain.member.member.service.MemberService;
import org.example.springbooturl.global.security.SecurityUser;
import org.example.springbooturl.global.stomp.StompMessageTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatController {
    private final ChatService chatService;
    private final StompMessageTemplate template;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{roomId}")
    public String showRoom(
            @PathVariable long roomId,
            Model model
    ) {
        ChatRoom chatRoom = chatService.findRoomById(roomId).get();
        model.addAttribute("chatRoom", chatRoom);

        return "domain/chat/chat/room";
    }

    @GetMapping("/{roomId}/messages")
    @ResponseBody
    public List<ChatMessageDto> getRoomMessages(
            @PathVariable long roomId
    ) {
        List<ChatMessage> messages = chatService.findMessagesByRoomId(roomId);

        return messages
                .stream()
                .map(ChatMessageDto::new)
                .toList();
    }

    public record CreateMessageReqBody(String body) {
    }

    @MessageMapping("/chat/{roomId}/messages/create")
    @Transactional
    public void createMessage(
            CreateMessageReqBody createMessageReqBody,
            @DestinationVariable long roomId,
            Authentication authentication
    ) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        Member member = memberService.getReferenceById(securityUser.getId());

        ChatRoom chatRoom = chatService.findRoomById(roomId).get();
        ChatMessage chatMessage = chatService.writeMessage(chatRoom, member, createMessageReqBody.body());
        ChatMessageDto chatMessageDto = new ChatMessageDto(chatMessage);

        template.convertAndSend("topic", "chat" + roomId + "MessageCreated", chatMessageDto);
    }
}
