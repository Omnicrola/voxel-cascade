package com.omnicrola.voxel.eventBus.events;

/**
 * Created by omnic on 4/13/2016.
 */
public class PlayerTeamChangedEvent {
    private int playerId;
    private int teamId;

    public PlayerTeamChangedEvent(int playerId, int teamId) {
        this.playerId = playerId;
        this.teamId = teamId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getTeamId() {
        return teamId;
    }
}
