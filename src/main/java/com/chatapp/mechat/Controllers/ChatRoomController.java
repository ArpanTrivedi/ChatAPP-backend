package com.chatapp.mechat.Controllers;


import com.chatapp.mechat.Models.ChatRoom;
import com.chatapp.mechat.Models.Message;
import com.chatapp.mechat.Repository.ChatRoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin(
        origins = "*",
        allowedHeaders = "*",
        methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE }
)
@RequestMapping("/api/v1/rooms")
public class ChatRoomController {


    private ChatRoomRepository chatRoomRepository;

    public ChatRoomController(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    // Create chat room
    @PostMapping
    public ResponseEntity<?> createChatRoom(@RequestBody String roomID) {

        if (chatRoomRepository.findByRoomID(roomID) != null) {
            // Room already exist
            return ResponseEntity.badRequest().body("Room is already created");
        }

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomID(roomID);

        chatRoomRepository.save(chatRoom);

        return ResponseEntity.status(HttpStatus.CREATED).body(chatRoom);
    }

    // Join chat room
    @GetMapping("/{roomID}")
    public ResponseEntity<?> getChatRooms(@PathVariable String roomID) {

        ChatRoom room = chatRoomRepository.findByRoomID(roomID);

        if (room == null) {
            return ResponseEntity.badRequest().body("Room doesn't exist!");
        }

        return ResponseEntity.ok(room);
    }

    //get all room Message
    @GetMapping("/{roomID}/messages")
    public ResponseEntity<List<Message>> getALLMessages(
            @PathVariable String roomID,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "20", required = false) int size
    ) {

        ChatRoom room = chatRoomRepository.findByRoomID(roomID);

        List<Message> messages = room.getMessages();

        int start = Math.max(0, messages.size() - ((page + 1)*size));
        int end =  Math.min(messages.size(), start + size);

        List<Message> filteredMessages = messages.subList(start, end);


        return ResponseEntity.ok(filteredMessages);

    }

}
