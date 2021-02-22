package com.shengtianyizi.vo;

import lombok.Data;

import java.util.Date;

/**
 * ClassName    Invocation
 * Package	    com.shengtianyizi.vo
 * Description
 *
 * @author admin
 * @date 2021-02-22 10:33
 */
@Data
public class Invocation {
    /***
     * 类型
     * com.shengtianyizi.constants.HandlerConstant
     */
    private Integer type;
    /***
     * 消息,json格式
     */
    private String message;

}
