package com.shengtianyizi.container;

import lombok.extern.slf4j.Slf4j;
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

    private static final ConcurrentHashMap<String, CopyOnWriteArrayList<String>> rooms = new ConcurrentHashMap<>();

    public static final CopyOnWriteArrayList<String> singleRequestNos = new CopyOnWriteArrayList<>();

    public static final CopyOnWriteArrayList<String> errorRequestNos = new CopyOnWriteArrayList<>();

    public static void add(String roomId, String userId) {
        String requestNo = roomId + userId;
        singleRequestNos.add(requestNo);
        CopyOnWriteArrayList<String> userIds = getRoom(roomId);
        userIds.add(userId);
        rooms.put(roomId, userIds);
        log.info("user [{}],加入 room [{}] success.", userId, roomId);
    }

    private static synchronized CopyOnWriteArrayList<String> getRoom(String roomId) {
        CopyOnWriteArrayList<String> userIds = rooms.get(roomId);
        if (userIds == null) {
            userIds = createRoom();
        }
        return userIds;
    }

    private static CopyOnWriteArrayList<String> createRoom() {
        return new CopyOnWriteArrayList<>();
    }

    public static void remove(String roomId, String userId) {
        CopyOnWriteArrayList<String> userIds = rooms.get(roomId);
        if (null != userIds) {
            if (userIds.contains(userId)) {
                userIds.remove(userId);
                String requestNo = roomId + userId;
                singleRequestNos.remove(requestNo);
                log.info("user [{}],离开 room [{}] success.", userId, roomId);
            }
        }
    }

    public static void show(String roomId) {
        CopyOnWriteArrayList<String> userIds = rooms.get(roomId);
        if (null == userIds) {
            log.info("当前房间内无人");
            return;
        }
        log.info("当前房间内人员: [{}]", userIds.toString());
    }
}
