package com.omnicrola.voxel.commands;

import com.jme3.network.serializing.Serializable;
import com.omnicrola.voxel.data.level.LevelDefinition;
import com.omnicrola.voxel.eventBus.VoxelEventBus;
import com.omnicrola.voxel.eventBus.events.MultiplayerLobbyMapEvent;
import com.omnicrola.voxel.world.CommandPackage;

import java.util.UUID;

/**
 * Created by Eric on 4/10/2016.
 */
@Serializable
public class SelectMultiplayerLevelCommand extends AbstractWorldCommand {
    private String uuid;

    public SelectMultiplayerLevelCommand() {
    }

    public SelectMultiplayerLevelCommand(UUID uuid) {
        this.uuid = uuid.toString();
    }

    @Override
    public void execute(CommandPackage commandPackage) {
        LevelDefinition levelDefinition = commandPackage.getLevelManager().getLevel(UUID.fromString(this.uuid));
        VoxelEventBus.INSTANCE().post(new MultiplayerLobbyMapEvent(levelDefinition));
    }
}
