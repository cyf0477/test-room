package com.shengtianyizi.handler;

import com.shengtianyizi.constants.HandlerConstant;
import com.shengtianyizi.container.RoomsContainer;
import com.shengtianyizi.vo.JoinMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * ClassName    JoinHandler
 * Package	    com.shengtianyizi.handler
 * Description
 *
 * @author admin
 * @date 2021-02-22 10:17
 */
@Slf4j
@Component
public class JoinHandler implements Handler<JoinMessage> {

    @Override
    public void execute(JoinMessage message) {
        String roomId = message.getRoomId();
        String userId = message.getUserId();
        RoomsContainer.show(roomId);
        String requestNo = roomId + userId;

        if (RoomsContainer.errorRequestNos.contains(requestNo)) {
            RoomsContainer.errorRequestNos.remove(requestNo);
            log.info("忽略指令,user [{}],早已退出 room [{}]", userId, roomId);
            return;
        }
        if (RoomsContainer.repeatRequestNos.contains(requestNo)) {
            log.info("忽略指令,user [{}],重复加入 room [{}]", userId, roomId);
            return;
        }
        RoomsContainer.add(roomId, userId);
    }

    @Override
    public Integer getType() {
        return HandlerConstant.JOIN;
    }
}
