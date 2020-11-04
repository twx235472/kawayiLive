package com.example.chat.server;

import com.example.chat.domain.entity.RoomEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 房间服务
 */
@Service
public class RoomServer {
    @Resource
    ChatServer chatServer;


    public void createRoom(Integer port,String roomName,String createUserId){
        String adress = chatServer.run(port);
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setRoomAdress(adress);
        roomEntity.setRoomPort(port);
        roomEntity.setRoomOwnerUserId(createUserId);
    }

    public void getRoomList(){

    }

    public void deleteRoom(){

    }

    public void getRoomInfo(){

    }
}
