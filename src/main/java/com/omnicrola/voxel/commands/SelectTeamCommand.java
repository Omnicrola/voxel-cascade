package com.omnicrola.voxel.commands;

import com.jme3.network.serializing.Serializable;
import com.omnicrola.voxel.eventBus.VoxelEventBus;
import com.omnicrola.voxel.eventBus.events.PlayerTeamChangedEvent;
import com.omnicrola.voxel.world.CommandPackage;

/**
 * Created by omnic on 4/13/2016.
 */
@Serializable
public class SelectTeamCommand extends AbstractWorldCommand {

    private int teamId;
    private int playerId;

    public SelectTeamCommand() {
    }

    public SelectTeamCommand(int teamId) {
        this.teamId = teamId;
    }

    @Override
    public void execute(CommandPackage commandPackage) {
        VoxelEventBus.INSTANCE().post(new PlayerTeamChangedEvent(this.playerId, this.teamId));
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getTeamId() {
        return teamId;
    }
}
