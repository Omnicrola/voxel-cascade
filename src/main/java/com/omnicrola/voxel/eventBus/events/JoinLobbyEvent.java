package com.omnicrola.voxel.eventBus.events;

/**
 * Created by omnic on 4/4/2016.
 */
public class JoinLobbyEvent {
    private boolean succeeded;
    private int playerId;

    public JoinLobbyEvent(boolean succeeded, int playerId) {
        this.succeeded = succeeded;
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public boolean succeeded() {
        return succeeded;
    }

    public static JoinLobbyEvent fail() {
        return new JoinLobbyEvent(false, -1);
    }

    public static JoinLobbyEvent success(int playerId) {
        return new JoinLobbyEvent(true, playerId);
    }
}
