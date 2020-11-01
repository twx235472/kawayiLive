package com.example.chat.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Wttang
 * @Date: 2020/9/19 21:27
 * @Desciption: 用户实体
 */
@Data
@NoArgsConstructor
public class UserEntity extends BaseEntity{

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 账号信息（待扩展）
     */
    private String account;

    /**
     * 临时连接地址
     */
    private String remoteAddress;

}
