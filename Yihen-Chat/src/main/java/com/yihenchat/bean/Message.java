package com.yihenchat.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class Message {

    private Long from; // 发送人
    private Long to; // 接收人
    private String text;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    public Date date;

}
