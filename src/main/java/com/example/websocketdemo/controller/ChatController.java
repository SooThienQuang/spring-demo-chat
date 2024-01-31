package com.example.websocketdemo.controller;

import com.example.websocketdemo.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

/**
 * Created by rajeevkumarsingh on 24/07/17.
 */
@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
//        if(chatMessage.getSender().equals("quang")||chatMessage.getSender().equals("thong"))
//        {
//            if(!chatMessage.getPassword().equals("123"))
//            {
//                chatMessage.setType(ChatMessage.MessageType.REJECT);
//                chatMessage.setSender("Tài khoản hoặc mật khẩu không chính xác!!");
//            }
//        }else
//        {
//            chatMessage.setType(ChatMessage.MessageType.REJECT);
//            chatMessage.setSender("Bạn không có quyền truy cập !!");
//        }
        return chatMessage;
    }

    @MessageMapping("/chat.typing")
    @SendTo("/topic/public")
    public ChatMessage onTyping(@Payload ChatMessage chatMessage,SimpMessageHeaderAccessor headerAccessor
                              ) {
        chatMessage.setType(ChatMessage.MessageType.TYPING);
        return chatMessage;
    }
}
