package com.omnicrola.voxel.eventBus.events;

/**
 * Created by omnic on 4/4/2016.
 */
public class JoinLobbyEvent {
    private boolean succeeded;

    public JoinLobbyEvent(boolean succeeded) {
        this.succeeded = succeeded;
    }

    public boolean succeeded() {
        return succeeded;
    }

    public static JoinLobbyEvent fail() {
        return new JoinLobbyEvent(false);
    }

    public static JoinLobbyEvent success() {
        return new JoinLobbyEvent(true);
    }
}
