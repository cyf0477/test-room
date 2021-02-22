package com.shengtianyizi.container;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ClassName    RoomsContainer
 * Package	    com.shengtianyizi.container
 * Description
 *
 * @author admin
 * @date 2021-02-22 11:56
 */
@Slf4j
public class RoomsContainer {

    private static final ConcurrentHashMap<String, List<String>> rooms = new ConcurrentHashMap<>();

    public static final CopyOnWriteArrayList<String> repeatRequestNos = new CopyOnWriteArrayList<>();

    public static final CopyOnWriteArrayList<String> errorRequestNos = new CopyOnWriteArrayList<>();

    public static synchronized void add(String roomId, String userId) {
        String requestNo = roomId + userId;
        repeatRequestNos.add(requestNo);
        List<String> userIds = rooms.get(roomId);
        if (null != userIds) {
            userIds.add(userId);
            log.info("user [{}],加入 room [{}] success.", userId, roomId);
            return;
        }
        userIds = new ArrayList<>();
        userIds.add(userId);
        rooms.put(roomId, userIds);
        log.info("user [{}],加入 room [{}] success.", userId, roomId);
    }

    public static synchronized void remove(String roomId, String userId) {
        List<String> userIds = rooms.get(roomId);
        if (null != userIds) {
            if (userIds.contains(userId)) {
                userIds.remove(userId);
                String requestNo = roomId + userId;
                repeatRequestNos.remove(requestNo);
                log.info("user [{}],离开 room [{}] success.", userId, roomId);
            }
        }
    }

    public static void show(String roomId) {
        List<String> userIds = rooms.get(roomId);
        if (null == userIds) {
            log.info("当前房间内无人");
            return;
        }
        log.info("当前房间内人员: [{}]", userIds.toString());
    }
}
