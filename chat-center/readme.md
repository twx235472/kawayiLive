# 群聊服务
作用：负责独立运行的房间群聊服务，与其他服务之间没有强关联关系，

### 思路
* 用户登陆直播系统以后，选择想要观看的直播间
* 每个房间对应一个id号，这个作为房间的唯一标识，每一个房间绑定一个端口号，保存房间信息
* 每个房间都已经建立了一个独立的Serever
* chat-center提供接口，接收一个房间id和userId，通过webSocket建立长连接
* 服务端获取该用户的信息和房间信息，为房间提供群聊功能



```sequence
title: 聊天服务逻辑时序图

Note left of 客户端: 无需验是否登陆
客户端 ->> ChatService: 请求房间列表
ChatService --> ChatService: 查询当前可用房间
ChatService->> 客户端:返回房间列表
客户端 ->> ChatService: 建立连接

```


