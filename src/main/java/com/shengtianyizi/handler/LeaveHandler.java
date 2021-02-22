package com.shengtianyizi.handler;

import com.shengtianyizi.constants.HandlerConstant;
import com.shengtianyizi.container.RoomsContainer;
import com.shengtianyizi.vo.LeaveMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * ClassName    LeaveHandler
 * Package	    com.shengtianyizi.handler
 * Description
 *
 * @author admin
 * @date 2021-02-22 12:38
 */
@Component
@Slf4j
public class LeaveHandler implements Handler<LeaveMessage> {

    @Override
    public void execute(LeaveMessage message) {
        String roomId = message.getRoomId();
        String userId = message.getUserId();
        RoomsContainer.show(roomId);
        String requestNo = roomId + userId;
        if (!(RoomsContainer.singleRequestNos).contains(requestNo)) {
            RoomsContainer.errorRequestNos.remove(requestNo);
            RoomsContainer.errorRequestNos.add(requestNo);
            log.info("当前用户 [{}],由于网络先退出后进入原因 [{}]", userId, roomId);
            return;
        }
        RoomsContainer.remove(roomId, userId);
    }

    @Override
    public Integer getType() {
        return HandlerConstant.LEAVE;
    }
}
