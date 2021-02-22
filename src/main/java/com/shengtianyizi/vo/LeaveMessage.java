package com.shengtianyizi.vo;

import lombok.Data;

/**
 * ClassName    LeaveMessage
 * Package	    com.shengtianyizi.vo
 * Description
 *
 * @author admin
 * @date 2021-02-22 12:38
 */
@Data
public class LeaveMessage implements Message {

    private String roomId;

    private String userId;
}
