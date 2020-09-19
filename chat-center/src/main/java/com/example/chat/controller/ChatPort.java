package com.example.chat.controller;

import com.example.chat.common.BaseResponse;
import com.example.chat.domain.vo.MsgVO;

/**
 * @Author: Wttang
 * @Date: 2020/9/19 21:03
 * @Desciption: 对外接口
 */

//TODO:确定对外的交互接口，

public interface ChatPort {

    /**
     * 接收用户发送的消息
     * @return
     */
    public BaseResponse reciveMsg(MsgVO msgVO);


    /**
     * 对外广播接口
     * @return
     */
    public BaseResponse broadcast(MsgVO msgVO);
}
