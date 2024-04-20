package org.example.springbooturl.domain.chat.chat.repository;

import org.example.springbooturl.domain.chat.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomRepositoryCustom {
}
