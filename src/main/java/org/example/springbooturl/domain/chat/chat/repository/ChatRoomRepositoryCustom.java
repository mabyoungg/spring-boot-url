package org.example.springbooturl.domain.chat.chat.repository;

import org.example.springbooturl.domain.chat.chat.entity.ChatRoom;
import org.example.springbooturl.domain.member.member.entity.Member;
import org.example.springbooturl.standard.base.KwTypeV2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatRoomRepositoryCustom {
    Page<ChatRoom> findByKw(KwTypeV2 kwType, String kw, Member owner, Boolean published, Pageable pageable);
}
