package org.example.springbooturl.domain.chat.chat.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbooturl.domain.chat.chat.dto.ChatRoomDto;
import org.example.springbooturl.domain.chat.chat.entity.ChatRoom;
import org.example.springbooturl.domain.chat.chat.service.ChatService;
import org.example.springbooturl.global.app.AppConfig;
import org.example.springbooturl.global.rsData.RsData;
import org.example.springbooturl.standard.base.KwTypeV2;
import org.example.springbooturl.standard.base.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/chatRooms")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1ChatRoomController {
    private final ChatService chatService;

    public record GetChatRoomsResponseBody(@NonNull PageDto<ChatRoomDto> itemPage) {
    }

    @GetMapping("")
    public RsData<GetChatRoomsResponseBody> getChatRooms(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String kw,
            @RequestParam(defaultValue = "ALL") KwTypeV2 kwType
    ) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page - 1, AppConfig.getBasePageSize(), Sort.by(sorts));
        Page<ChatRoom> itemPage = chatService.findRoomByKw(kwType, kw, null, true, pageable);

        Page<ChatRoomDto> _itemPage = itemPage.map(ChatRoomDto::new);

        return RsData.of(
                new GetChatRoomsResponseBody(
                        new PageDto<>(_itemPage)
                )
        );
    }

    public record GetChatRoomResponseBody(@NonNull ChatRoomDto item) {
    }

    @GetMapping("/{id}")
    public RsData<GetChatRoomResponseBody> getChatRoom(
            @PathVariable long id
    ) {
        ChatRoom chatRoom = chatService.findRoomById(id).get();

        return RsData.of(
                new GetChatRoomResponseBody(
                        new ChatRoomDto(chatRoom)
                )
        );
    }
}
