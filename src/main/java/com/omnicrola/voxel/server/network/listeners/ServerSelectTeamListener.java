package com.omnicrola.voxel.server.network.listeners;

import com.jme3.network.HostedConnection;
import com.omnicrola.voxel.commands.SelectTeamCommand;
import com.omnicrola.voxel.network.AbstractMessageListener;
import com.omnicrola.voxel.server.network.NetworkPlayer;
import com.omnicrola.voxel.server.network.ServerLobbyManager;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by omnic on 4/13/2016.
 */
public class ServerSelectTeamListener extends AbstractMessageListener<SelectTeamCommand, HostedConnection> {
    private static final Logger LOGGER = Logger.getLogger(ServerSelectTeamListener.class.getName());

    private ServerLobbyManager serverLobbyManager;

    public ServerSelectTeamListener(ServerLobbyManager serverLobbyManager) {
        this.serverLobbyManager = serverLobbyManager;
    }

    @Override
    protected void processMessage(HostedConnection connection, SelectTeamCommand message) {
        message.setIsLocal(true);
        Optional<NetworkPlayer> possiblePlayer = this.serverLobbyManager.getPlayer(connection);
        if (possiblePlayer.isPresent()) {
            NetworkPlayer networkPlayer = possiblePlayer.get();

            networkPlayer.setTeamId(message.getTeamId());
            message.setPlayerId(networkPlayer.getId());
            connection.getServer().broadcast(message);

            LOGGER.log(Level.INFO, "Player " + networkPlayer.getId() + " at " + networkPlayer.getConnection().getAddress() + " chose team ID " + message.getTeamId());
        }
    }
}
