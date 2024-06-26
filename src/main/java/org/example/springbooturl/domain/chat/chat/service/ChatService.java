package org.example.springbooturl.domain.chat.chat.service;

import lombok.RequiredArgsConstructor;
import org.example.springbooturl.domain.chat.chat.entity.ChatMessage;
import org.example.springbooturl.domain.chat.chat.entity.ChatRoom;
import org.example.springbooturl.domain.chat.chat.repository.ChatMessageRepository;
import org.example.springbooturl.domain.chat.chat.repository.ChatRoomRepository;
import org.example.springbooturl.domain.member.member.entity.Member;
import org.example.springbooturl.standard.base.KwTypeV2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    public Optional<ChatRoom> findRoomById(long roomId) {
        return chatRoomRepository.findById(roomId);
    }

    @Transactional
    public ChatRoom createRoom(Member owner, String name) {
        ChatRoom chatRoom = ChatRoom
                .builder()
                .owner(owner)
                .name(name)
                .published(true)
                .build();

        return chatRoomRepository.save(chatRoom);
    }

    @Transactional
    public ChatMessage writeMessage(ChatRoom room, Member writer, String body) {
        ChatMessage chatMessage = ChatMessage
                .builder()
                .chatRoom(room)
                .writer(writer)
                .body(body)
                .build();

        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> findMessagesByRoomId(long roomId) {
        return chatMessageRepository.findByChatRoomId(roomId);
    }

    public int count() {
        return (int) chatRoomRepository.count();
    }

    public Page<ChatRoom> findRoomByKw(KwTypeV2 kwType, String kw, Member owner, Boolean published, Pageable pageable) {
        return chatRoomRepository.findByKw(kwType, kw, owner, published, pageable);
    }
}
