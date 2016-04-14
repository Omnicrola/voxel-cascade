package com.omnicrola.voxel.network.listeners;

import com.jme3.network.Client;
import com.omnicrola.voxel.eventBus.VoxelEventBus;
import com.omnicrola.voxel.eventBus.events.JoinLobbyEvent;
import com.omnicrola.voxel.network.AbstractMessageListener;
import com.omnicrola.voxel.network.INetworkManager;
import com.omnicrola.voxel.network.messages.JoinLobbyMessage;

import java.util.logging.Logger;

/**
 * Created by omnic on 4/4/2016.
 */
public class ClientLobbyJoinMessageListener extends AbstractMessageListener<JoinLobbyMessage, Client> {

    private static final Logger LOGGER = Logger.getLogger(ClientLobbyJoinMessageListener.class.getName());
    private INetworkManager networkManager;

    public ClientLobbyJoinMessageListener(INetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    @Override
    protected void processMessage(Client connection, JoinLobbyMessage message) {
        if (message.joinWasSuccessful) {
            VoxelEventBus.INSTANCE().post(JoinLobbyEvent.fail());
        } else {
            VoxelEventBus.INSTANCE().post(JoinLobbyEvent.success(message.getPlayerId()));
            networkManager.setPlayerServerId(message.getPlayerId());
        }
    }
}
