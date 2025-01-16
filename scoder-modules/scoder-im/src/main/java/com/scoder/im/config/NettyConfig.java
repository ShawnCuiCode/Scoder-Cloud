package com.scoder.im.config;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;

public class NettyConfig {

    /**
     * Manages a group of all connected channels.
     */
    private static final ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * Manages the mapping between user IDs and their associated channels.
     */
    private static final ConcurrentHashMap<String, Channel> USER_CHANNEL_MAP = new ConcurrentHashMap<>();

    private NettyConfig() {
        // Prevent instantiation
    }

    /**
     * Retrieves the group of all connected channels.
     *
     * @return ChannelGroup containing all connected channels.
     */
    public static ChannelGroup getChannelGroup() {
        return CHANNEL_GROUP;
    }

    /**
     * Retrieves the mapping of user IDs to their associated channels.
     *
     * @return A ConcurrentHashMap containing user ID to channel mappings.
     */
    public static ConcurrentHashMap<String, Channel> getUserChannelMap() {
        return USER_CHANNEL_MAP;
    }
}