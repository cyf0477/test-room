package com.shengtianyizi.vo;

import lombok.Data;

/**
 * ClassName    JoinMessage
 * Package	    com.shengtianyizi.vo
 * Description
 *
 * @author admin
 * @date 2021-02-22 10:30
 */
@Data
public class JoinMessage implements Message {

    private String roomId;

    private String userId;

}
