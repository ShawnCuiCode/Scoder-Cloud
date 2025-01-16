package com.scoder.im.server;

import com.scoder.im.handler.WebSocketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * NettyServer sets up and manages the WebSocket server using Netty.
 * It handles WebSocket connections, upgrades HTTP protocols, and manages the server lifecycle.
 * <p>
 * This server processes WebSocket requests and routes them to the custom business logic handler.
 *
 * @author : Shawn Cui
 */
@Component
public class NettyServer {
    private static final Logger log = LoggerFactory.getLogger(NettyServer.class);

    /**
     * WebSocket protocol name.
     */
    private static final String WEBSOCKET_PROTOCOL = "WebSocket";

    /**
     * Port on which the WebSocket server listens.
     */
    @Value("${webSocket.netty.port:58080}")
    private int port;

    /**
     * WebSocket endpoint path.
     */
    @Value("${webSocket.netty.path:/webSocket}")
    private String webSocketPath;

    @Autowired
    private WebSocketHandler webSocketHandler;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;

    /**
     * Starts the Netty WebSocket server.
     *
     * @throws InterruptedException If the server thread is interrupted.
     */
    private void start() throws InterruptedException {
        bossGroup = new NioEventLoopGroup(); // Handles incoming connection requests
        workGroup = new NioEventLoopGroup(); // Handles read/write operations for established connections
        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class) // Specifies NIO-based server socket
                .localAddress(new InetSocketAddress(port)) // Binds the server to the configured port
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // Pipeline configuration for handling WebSocket communication
                        ch.pipeline().addLast(new HttpServerCodec()); // HTTP request/response codec
                        ch.pipeline().addLast(new ObjectEncoder()); // Encodes objects for transmission
                        ch.pipeline().addLast(new ChunkedWriteHandler()); // Handles large data streams

                        /*
                         * HttpObjectAggregator aggregates HTTP message fragments into a single FullHttpRequest or FullHttpResponse.
                         * This is required because WebSocket handshake requests may be fragmented.
                         */
                        ch.pipeline().addLast(new HttpObjectAggregator(8192));

                        /*
                         * WebSocketServerProtocolHandler upgrades HTTP requests to WebSocket and manages WebSocket frames.
                         * - ws://127.0.0.1:58080/xxx is the endpoint URI
                         * - Maintains persistent WebSocket connections
                         */
                        ch.pipeline().addLast(new WebSocketServerProtocolHandler(webSocketPath, WEBSOCKET_PROTOCOL, true, 65536 * 10));

                        // Custom handler for business logic
                        ch.pipeline().addLast(webSocketHandler);
                    }
                });

        // Bind the server and start listening
        ChannelFuture channelFuture = bootstrap.bind().sync();
        log.info("Server started and listening on: {}", channelFuture.channel().localAddress());

        // Block and wait until the server channel is closed
        channelFuture.channel().closeFuture().sync();
    }

    /**
     * Releases server resources (boss and worker threads).
     *
     * @throws InterruptedException If shutdown is interrupted.
     */
    @PreDestroy
    public void destroy() throws InterruptedException {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully().sync();
        }
        if (workGroup != null) {
            workGroup.shutdownGracefully().sync();
        }
    }

    /**
     * Initializes and starts the WebSocket server in a separate thread.
     */
    @PostConstruct
    public void init() {
        // Start the server in a new thread
        new Thread(() -> {
            try {
                start();
            } catch (InterruptedException e) {
                log.error("Error starting Netty server", e);
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}