package com.example.chat.server;

import com.example.chat.handler.ServerHandler;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ChatServer {
    //TODo:考虑https
    //static final boolean SSL = System.getProperty("ssl") != null;

    private NioEventLoopGroup boss;
    private NioEventLoopGroup woker;
    private ServerBootstrap bootstrap;

    Logger logger = LoggerFactory.getLogger(ChatServer.class);

    public ChatServer() {
        boss = new NioEventLoopGroup();
        woker = new NioEventLoopGroup();
        bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(boss, woker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline channelPipeline = socketChannel.pipeline();

                            channelPipeline.addLast(new HttpServerCodec());

                            channelPipeline.addLast(new ChunkedWriteHandler());
                            //
                            channelPipeline.addLast(new HttpObjectAggregator(8192));
                            //定义URL   ws://ip:port/chatService
                            channelPipeline.addLast(new WebSocketServerProtocolHandler("/chatService"));
                            //自定义的handler，处理业务逻辑
                            socketChannel.pipeline().addLast(new ServerHandler());
                        }
                    });
        } catch (Exception e) {
            logger.error("连接失败,error:{}", e.getMessage());
        } finally {
            boss.shutdownGracefully();
            woker.shutdownGracefully();
        }
    }

    public String run(Integer port) {
        if (StringUtils.isEmpty(port)) {
            return null;
        }
        try {
            Channel channel = bootstrap.bind(port).sync().channel();
            channel.closeFuture().sync();
            return channel.remoteAddress().toString();
        } catch (Exception e) {
            logger.error("连接失败,error:{}", e.getMessage());
        } finally {
            boss.shutdownGracefully();
            woker.shutdownGracefully();
            return null;
        }
    }
}
