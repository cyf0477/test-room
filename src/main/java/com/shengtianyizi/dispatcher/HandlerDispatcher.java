package com.shengtianyizi.dispatcher;

import com.alibaba.fastjson.JSONObject;
import com.shengtianyizi.container.CommandHandlerContainer;
import com.shengtianyizi.handler.Handler;
import com.shengtianyizi.vo.Invocation;
import com.shengtianyizi.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ClassName    HandlerDispatcher
 * Package	    com.shengtianyizi.dispatcher
 * Description
 *
 * @author admin
 * @date 2021-02-22 12:08
 */
@Component
public class HandlerDispatcher {

    @Autowired
    private CommandHandlerContainer commandHandlerContainer;

    /***
     * 整体思路:
     * 类似指令模式,c端发送指令,s端解析对应执行
     * 保证顺序,个人不建议在s端保证顺序,浪费时间,浪费生命
     * 多线程也没必要用, 业务逻辑都不够切换上下文的时间
     *
     * 在用户退出房间请求先执行,进入房间请求后执行的情况,
     * 可以依据房间号+用户id 作为唯一标识存储集合中,
     * 当唯一标识不存在,则用户退出行为触发与加入行为之前,不予理会,
     * 同时记录到异常行为集合, 当进入房间请求再次进来时,查询异常集合是否存在当前唯一标识,存在则忽略,代表之前已退出,不允许进入
     *
     *
     * @param invocation
     */
    public void dispatcher(Invocation invocation) {
        //获取 type对应的 commandHandler处理器
        Handler commandHandler = commandHandlerContainer.getCommandHandler(invocation.getType());
        // 获取  MessageHandler 处理器的消息类
        Class<? extends Message> messageClass = CommandHandlerContainer.getHandlerClass(commandHandler);
        //解析消息
        String context = invocation.getMessage();
        Message message = JSONObject.parseObject(context, messageClass);
        commandHandler.execute(message);
    }
}
