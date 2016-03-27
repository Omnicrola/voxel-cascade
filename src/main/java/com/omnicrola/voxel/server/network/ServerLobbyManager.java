package com.omnicrola.voxel.server.network;

import com.jme3.network.MessageListener;
import com.omnicrola.voxel.engine.IActionQueue;
import com.omnicrola.voxel.network.messages.HandshakeMessage;
import com.omnicrola.voxel.network.messages.JoinLobbyMessage;
import com.omnicrola.voxel.network.messages.StartGameMessage;
import com.omnicrola.voxel.server.main.ActiveMultiplayerGame;
import com.omnicrola.voxel.server.main.ServerLobbyState;
import com.omnicrola.voxel.server.network.listeners.ServerHandshakeListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by omnic on 3/25/2016.
 */
public class ServerLobbyManager {
    private List<MessageListener> lobbyListeners;
    private final List<NetworkPlayer> players;
    private INetworkServer server;

    public ServerLobbyManager(INetworkServer server) {
        this.server = server;
        this.players = new ArrayList<>();
    }

    public void addPlayer(NetworkPlayer networkPlayer) {
        this.players.add(networkPlayer);
    }

    public void startAcceptingPlayers(ServerLobbyState serverLobbyState, IActionQueue actionQueue) {
        this.server.start();
        addMessageListeners(serverLobbyState, actionQueue);
    }

    public ActiveMultiplayerGame transitionToActiveGame() {
        ActiveMultiplayerGame activeMultiplayerGame = new ActiveMultiplayerGame(this.server, this.players);
        removeMessageListeners();
        return activeMultiplayerGame;
    }


    private void addMessageListeners(ServerLobbyState serverLobbyState, IActionQueue actionQueue) {
        ServerHandshakeListener handshakeListener = new ServerHandshakeListener();
        ServerJoinLobbyListener joinLobbyListener = new ServerJoinLobbyListener(this);
        ServerStartGameListener startGameListener = new ServerStartGameListener(serverLobbyState, actionQueue);

        server.addMessageListener(handshakeListener, HandshakeMessage.class);
        server.addMessageListener(joinLobbyListener, JoinLobbyMessage.class);
        server.addMessageListener(startGameListener, StartGameMessage.class);

        this.lobbyListeners = Arrays.asList(handshakeListener, joinLobbyListener, startGameListener);
    }

    private void removeMessageListeners() {
        this.lobbyListeners.forEach(l -> this.server.removeMessageListener(l));
    }
}
