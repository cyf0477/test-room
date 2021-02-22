package com.shengtianyizi.handler;

import com.shengtianyizi.vo.Message;

/**
 * ClassName    Handler
 * Package	    com.shengtianyizi.handler
 * Description
 *
 * @author admin
 * @date 2021-02-22 10:17
 */
public interface Handler<T extends Message> {

    /***
     * 执行处理消息
     * @param message
     */
    void execute(T message);

    /***
     * 消息类型，即每个 Message 实现类上的 TYPE 静态字段
     * @return
     */
    Integer getType();

}
