package com.omnicrola.voxel.server.network.listeners;

import com.jme3.network.HostedConnection;
import com.omnicrola.voxel.engine.states.CurrentLevelState;
import com.omnicrola.voxel.network.AbstractMessageListener;
import com.omnicrola.voxel.network.messages.LoadLevelMessage;
import com.omnicrola.voxel.server.main.VoxelServerEngine;
import com.omnicrola.voxel.server.network.RebroadcastFilter;
import com.omnicrola.voxel.server.network.ServerNetworkState;

/**
 * Created by Eric on 2/21/2016.
 */
public class ServerLoadLevelListener extends AbstractMessageListener<LoadLevelMessage, HostedConnection> {

    private ServerNetworkState serverNetworkState;
    private VoxelServerEngine voxelServerEngine;

    public ServerLoadLevelListener(ServerNetworkState serverNetworkState, VoxelServerEngine voxelServerEngine) {
        this.serverNetworkState = serverNetworkState;
        this.voxelServerEngine = voxelServerEngine;
    }

    @Override
    protected void processMessage(HostedConnection connection, LoadLevelMessage message) {
        CurrentLevelState currentLevelState = this.voxelServerEngine.getStateManager().getState(CurrentLevelState.class);
        currentLevelState.loadLevel(message.getLevelId());
        connection.getServer().broadcast(new RebroadcastFilter(connection),message);

    }
}
