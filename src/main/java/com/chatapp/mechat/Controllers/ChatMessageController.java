package com.chatapp.mechat.Controllers;


import com.chatapp.mechat.DAO.ChatMessageRequest;
import com.chatapp.mechat.Models.ChatRoom;
import com.chatapp.mechat.Models.Message;
import com.chatapp.mechat.Repository.ChatRoomRepository;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;

@Controller
@CrossOrigin(
        origins = "*",
        allowedHeaders = "*",
        methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE }
)
public class ChatMessageController {

    private ChatRoomRepository chatRoomRepository;

    public ChatMessageController(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    @MessageMapping("/getMessage/{roomID}")
    @SendTo("/topic/chats/{roomID}")
    public Message getMessage(
            @DestinationVariable String roomID,
            @RequestBody ChatMessageRequest request) {

        ChatRoom room = chatRoomRepository.findByRoomID(roomID);

        Message message = new Message();
        message.setUserName(request.getUserName());
        message.setActualMessage(request.getMessageContent());
        message.setCurrentTimeStamp(LocalDateTime.now());

        if (room != null) {
            room.getMessages().add(message);
            chatRoomRepository.save(room);

        } else {
            RuntimeException runtimeException = new RuntimeException("Room doesn't exist!");
            return null;
        }

        return message;
    }

}
