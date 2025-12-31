package com.chatapp.mechat.Repository;

import com.chatapp.mechat.Models.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    ChatRoom findByRoomID(String roomID);
}
