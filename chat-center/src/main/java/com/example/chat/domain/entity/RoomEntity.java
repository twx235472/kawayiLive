package com.example.chat.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomEntity extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /**
     * 房间名称
     */
    private String roomName;

    /**
     * 房间所有人的用户id
     */
    private String roomOwnerUserId;

    /**
     * 房间的ip地址
     */
    private String roomIp;

    /**
     * 房间的端口号
     */
    private String roomPort;

    /**
     * 备注
     */
    private String roomRemark;
}
