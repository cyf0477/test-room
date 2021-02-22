package com.shengtianyizi.controller;

import com.alibaba.fastjson.JSONObject;
import com.shengtianyizi.container.CommandHandlerContainer;
import com.shengtianyizi.dispatcher.HandlerDispatcher;
import com.shengtianyizi.handler.Handler;
import com.shengtianyizi.vo.Invocation;
import com.shengtianyizi.vo.Message;
import com.sun.corba.se.impl.activation.CommandHandler;
import com.sun.media.jfxmedia.logging.Logger;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ClassName    RoomController
 * Package	    com.shengtianyizi.controller
 * Description
 * <p>
 * 题目1:
 * 1、
 * 实现一个房间的功能，单纯的业务逻辑不包含网络层接入
 * 一个加入房间的方法。joinRoom(String roomId,String userId)
 * 一个退出房间的方法。leaveRoom(String roomId,String userId)
 * 要求加入退出房间时打印房间内除了自己的所有成员。
 * 2、
 * 现在基于房间加入退出的功能对外提供了对外的请求接口（QPS 要求为 1W/S）不需要关心中间件，以及网络接入层，如果有想法可以写一下，主要关注怎么保证房间的功能正常。
 * 由于网络的问题加入退出房间的请求（可以自定义请求体）到的先后顺序可能不一样，但是原始顺序是一样的，要求保证加入退出房间在动作上是一致的。
 * 设计接收网络请求的 service 方法。以及对上述问题的解决方案。
 *
 * @author admin
 * @date 2021-02-22 10:10
 */
@RestController
@RequestMapping("room")
@Slf4j
public class RoomController {

    @Autowired
    private HandlerDispatcher handlerDispatcher;

    /***
     * 指令controller
     * @param invocation
     */
    @RequestMapping("/handler")
    public void handler(Invocation invocation) {
        handlerDispatcher.dispatcher(invocation);
    }

}
