package com.yihenchat.service.impl;

import com.yihenchat.bean.Message;
import com.yihenchat.dto.Result;
import com.yihenchat.service.MessageService;
import com.yihenchat.utils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageUtil messageUtil;
    
    @Override
    public Result getChatRecords(Message message) {
        List<Object> message1 = messageUtil.getMessage(message);
        return Result.ok(null,message1);
    }
}
