package com.yihenchat.service;

import com.yihenchat.bean.Message;
import com.yihenchat.dto.Result;

import java.util.List;

public interface MessageService {

    // 获取聊天记录
    public Result getChatRecords(Message message);
}
