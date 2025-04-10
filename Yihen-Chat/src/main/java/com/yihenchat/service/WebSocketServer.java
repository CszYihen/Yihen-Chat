package com.yihenchat.service;


import com.alibaba.fastjson.JSON;
import com.yihenchat.bean.Message;
import com.yihenchat.bean.OnlineUser;
import com.yihenchat.service.impl.LoginServiceImpl;
import com.yihenchat.service.impl.UserServiceImpl;
import com.yihenchat.utils.MessageUtil;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.One;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

// 定义WebSocket服务端点
@ServerEndpoint("/webSocket/{user-id}") // url路径/路径变量
@Component
@Slf4j
public class WebSocketServer {
    // 统计在线人数
    private static AtomicInteger onlineNum = new AtomicInteger();
    // 存放每个用户对应的WebSocketServer对象，使用线程安全Set
    private static ConcurrentHashMap<Long, Session> sessionPools = new ConcurrentHashMap<>();

    // Spring管理的Bean都是单例的，而WebSocket是多对象的，所以无法使用@Autowired直接注入
    // 将需要注入的变量改为静态的,通过下面的方式注入
    private static   MessageUtil messageUtil;
    @Autowired
    public void setMessageUtil(MessageUtil messageUtil) {
        WebSocketServer.messageUtil = messageUtil;
    }

    @OnOpen //建立连接成功调用
    public void onOpen(Session session, @PathParam(value = "user-id") Long userId) {
        sessionPools.put(userId, session);
        // 在线人数+1
        onlineNum.incrementAndGet();
        // 广播在线信息
        broadcastOnlineMessage();
        log.error("用户:"+userId+"上线!");
    }

    @OnMessage //接受客户端消息
    public void onMessage(String message) {
        log.error("接受到消息:"+message);
        Message msg = JSON.parseObject(message, Message.class);
        msg.setDate(new Date());

        if (msg.getTo().equals(-1)) {
            // TODO 群发
        }else {
            // 存储聊天记录
            // TODO 异步实现
            messageUtil.saveMessage(msg);
            // 转发信息
            sendInfo(msg.getTo(), JSON.toJSONString(msg,true));
        }
    }

    //关闭连接时调用
    @OnClose
    public void onClose(@PathParam(value = "user-id") Long userId){
        // 移除下线用户连接信息
        sessionPools.remove(userId);
        // 在线人数-1
        onlineNum.decrementAndGet();

        broadcastOnlineMessage();

        log.error("用户:"+userId+"下线!");
    }


    // 连接出错时调用
    @OnError
    public void OnError(Session session, Throwable throwable) {
        log.error("websocket 错误:"+throwable.getMessage());
    }


    // 给指定用户发送信息
    public void sendInfo(Long userId, String message) {
        Session session = sessionPools.get(userId);
        try {
            sendMessage(session,message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // 发送消息
    public void sendMessage(Session session, String message) throws IOException {
        if (session != null) {
            synchronized (session) {
                log.debug("发送数据：{}",message);
                session.getBasicRemote().sendText(message);
            }
        }
    }

    // 广播在线消息
    public void broadcastOnlineMessage() {
        Collection<OnlineUser> onlineUsers = LoginServiceImpl.onlineUsersPools.values();
        String text = JSON.toJSONString(onlineUsers, true);
        Message message = new Message();
        message.setTo(0L);
        message.setDate(new Date());
        message.setText(text);
        String s = JSON.toJSONString(message, true);
        broadcast(s);
    }


    // 群发消息
    public void broadcast(String message){
        for (Session session: sessionPools.values()) {
            try {
                sendMessage(session, message);
            } catch(Exception e){
                e.printStackTrace();
                continue;
            }
        }
    }
}
