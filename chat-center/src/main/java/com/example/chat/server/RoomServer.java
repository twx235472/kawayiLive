package com.example.chat.server;

import com.example.chat.domain.entity.RoomEntity;
import com.example.chat.exception.CreateRoomFailException;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 房间服务
 */
@Service
public class RoomServer {
    Logger logger = LoggerFactory.getLogger(RoomServer.class);

    @Resource
    ChatServer chatServer;


    public void createRoom(String roomName, String createUserId) throws Exception{
        //TODO:返回码定义
        //TODO：controller的通用返回类型
        //TODO：定义个工具类，根据配置文件的接口定义，设置server绑定的服务范围，最优化选择绑定的端口，负载均衡？
        //TODO：配置一下logger类，self4j，log的位置
        //TODO：引入sawgger
        String address = chatServer.run(getOneAvaliblePort());
        if (address != null) {
            RoomEntity roomEntity = new RoomEntity();
            roomEntity.setRoomName(roomName);
            roomEntity.setRoomAdress(address);
            roomEntity.setRoomPort(getOneAvaliblePort());
            roomEntity.setRoomOwnerUserId(createUserId);
        } else {
            logger.error("房间创建失败，roomName:{},userId:{}",roomName,createUserId);
            throw new CreateRoomFailException();
        }

    }

    public void getRoomList() {

    }

    public void deleteRoom() {

    }

    public void getRoomInfo() {

    }

    private Integer getOneAvaliblePort() {
        return null;
    }
}
