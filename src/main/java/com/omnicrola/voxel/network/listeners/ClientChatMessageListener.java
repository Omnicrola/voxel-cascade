package com.omnicrola.voxel.network.listeners;

import com.jme3.network.Client;
import com.omnicrola.voxel.commands.ICommandProcessor;
import com.omnicrola.voxel.eventBus.VoxelEventBus;
import com.omnicrola.voxel.eventBus.events.ChatMessageEvent;
import com.omnicrola.voxel.network.AbstractMessageListener;
import com.omnicrola.voxel.network.messages.ChatMessage;

/**
 * Created by omnic on 4/13/2016.
 */
public class ClientChatMessageListener extends AbstractMessageListener<ChatMessage, Client> {
    private ICommandProcessor commandProcessor;

    public ClientChatMessageListener(ICommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
    }

    @Override
    protected void processMessage(Client connection, ChatMessage message) {
        VoxelEventBus.INSTANCE().post(new ChatMessageEvent(message));
    }
}
