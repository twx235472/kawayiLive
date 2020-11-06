package com.example.chat.controller;

import com.example.chat.domain.dto.RoomDto;
import com.example.chat.server.RoomServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "room")
public class RoomController {
    @Resource
    RoomServer roomServer;

    @RequestMapping(value = "create")
    public void createRoom(@RequestBody RoomDto roomDto){
        try {
            roomServer.createRoom(roomDto.getRoomName(),roomDto.getRoomOwnerUserId());
            return;
        } catch (Exception e) {
            return;
        }
    }
}
