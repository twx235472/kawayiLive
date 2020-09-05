# kawayiLive
多端适配的直播平台（直播，聊天室，弹幕，礼物系统）  

***
## 初步思路
  1. 视频模块：直播编解码，弹幕加载  
  2. 聊天室模块：多人了聊天室  
  3. 礼物模块：充值与消费  
  4. 用户中心：  
  4. 总控制服务：负责与前端的所有交互，所有中心为总控制提供能力，总控制负责组织业务，构建VO
 ***
## 逻辑架构图（待完善）
![逻辑视图](http://assets.processon.com/chart_image/5c83a122e4b0afc7440b3f65.png)

***
## 技术架构图

```sequence
Title: Here is a title
A->B: Normal line
B-->C: Dashed line
C->>D: Open arrow
D-->>A: Dashed open arrow
```
  
```flow
st=>start: Start|past:>http://www.baidu.com
e=>end:  Ende|future:>http://www.baidu.com
op1=>operation:  My Operation
op2=>operation:  Stuff|current
sub1=>subroutine:  My Subroutine|invalid
cond=>condition:  Yes or No|approved:>http://www.google.com
c2=>condition:  Good idea|rejected
io=>inputoutput:  catch something...|future
st->op1(right)->cond
cond(yes, right)->c2
cond(no)->sub1(left)->op1
c2(yes)->io->e
c2(no)->op2->e  
```
