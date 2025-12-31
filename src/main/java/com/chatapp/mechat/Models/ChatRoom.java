package com.chatapp.mechat.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "ChatRoom")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatRoom {

    @Id
    private String id;
    private String roomID;

    private List<Message> messages = new ArrayList<>();

}
