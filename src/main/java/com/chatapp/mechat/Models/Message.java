package com.chatapp.mechat.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private String userName;
    private String actualMessage;

    private LocalDateTime currentTimeStamp;

    public Message(String userName, String actualMessage) {
        this.userName = userName;
        this.actualMessage = actualMessage;

        this.currentTimeStamp = LocalDateTime.now();
    }
}
