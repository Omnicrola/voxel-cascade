package com.omnicrola.voxel.server.network;

import com.jme3.network.HostedConnection;
import com.jme3.network.MessageListener;
import com.omnicrola.voxel.commands.SelectMultiplayerLevelCommand;
import com.omnicrola.voxel.commands.SelectTeamCommand;
import com.omnicrola.voxel.commands.StartMultiplayerGameCommand;
import com.omnicrola.voxel.data.TeamId;
import com.omnicrola.voxel.engine.IActionQueue;
import com.omnicrola.voxel.network.messages.HandshakeMessage;
import com.omnicrola.voxel.network.messages.JoinLobbyMessage;
import com.omnicrola.voxel.server.main.ActiveMultiplayerGame;
import com.omnicrola.voxel.server.main.ServerLobbyState;
import com.omnicrola.voxel.server.network.listeners.*;
import com.sun.istack.internal.NotNull;

import java.util.*;

/**
 * Created by omnic on 3/25/2016.
 */
public class ServerLobbyManager {
    private List<MessageListener> lobbyListeners;
    private final List<NetworkPlayer> players;
    private INetworkServer server;
    private UUID lobbyKey;
    private int maxPlayers = 4;

    public ServerLobbyManager(INetworkServer server, UUID lobbyKey) {
        this.server = server;
        this.lobbyKey = lobbyKey;
        this.players = new ArrayList<>();
    }

    public void addPlayer(NetworkPlayer networkPlayer) {
        this.players.add(networkPlayer);
    }

    public void startAcceptingPlayers(@NotNull ServerLobbyState serverLobbyState, @NotNull IActionQueue actionQueue) {
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
        ServerSelectLevelListener serverSelectLevelListener = new ServerSelectLevelListener();
        ServerStartGameListener startGameListener = new ServerStartGameListener(this, serverLobbyState, actionQueue);
        ServerSelectTeamListener selectTeamListener = new ServerSelectTeamListener(this);

        server.addMessageListener(handshakeListener, HandshakeMessage.class);
        server.addMessageListener(joinLobbyListener, JoinLobbyMessage.class);
        server.addMessageListener(startGameListener, StartMultiplayerGameCommand.class);
        server.addMessageListener(serverSelectLevelListener, SelectMultiplayerLevelCommand.class);
        server.addMessageListener(selectTeamListener, SelectTeamCommand.class);

        this.lobbyListeners = Arrays.asList(handshakeListener, joinLobbyListener, startGameListener, serverSelectLevelListener, selectTeamListener);
    }

    private void removeMessageListeners() {
        this.lobbyListeners.forEach(l -> this.server.removeMessageListener(l));
    }

    public UUID getLobbyKey() {
        return this.lobbyKey;
    }

    public boolean isHost(@NotNull HostedConnection connection) {
        return this.players
                .stream()
                .filter(p -> p.getConnection().getId() == connection.getId())
                .filter(p -> p.isHost())
                .findFirst()
                .isPresent();
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public int getPlayerCount() {
        return this.players.size();
    }

    public List<NetworkPlayer> getPlayers() {
        return this.players;
    }

    public Optional<NetworkPlayer> getPlayer(HostedConnection connection) {
        Optional<NetworkPlayer> first = this.players
                .stream()
                .filter(p -> p.getConnection().equals(connection))
                .findFirst();
        return first;
    }

    public boolean allPlayersHaveTeams() {
        boolean allPlayersHaveTeams = this.players.stream()
                .map(p -> p.getTeamId())
                .anyMatch(t -> !t.equals(TeamId.NEUTRAL));
        return allPlayersHaveTeams;
    }

    public boolean allPlayersHaveDifferentTeams() {
        HashSet<TeamId> teamIds = new HashSet<>();

        for (NetworkPlayer player : this.players) {
            TeamId teamId = player.getTeamId();
            boolean success = teamIds.add(teamId);
            if (!success) {
                return false;
            }
        }
        return true;
    }
}
