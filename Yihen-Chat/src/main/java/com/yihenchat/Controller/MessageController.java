package com.yihenchat.Controller;


import com.yihenchat.bean.Message;
import com.yihenchat.dto.Result;
import com.yihenchat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/record")
    public Result getChatRecords(@RequestBody Message message) {
        Result chatRecords = messageService.getChatRecords(message);

        return chatRecords;
    }
}
