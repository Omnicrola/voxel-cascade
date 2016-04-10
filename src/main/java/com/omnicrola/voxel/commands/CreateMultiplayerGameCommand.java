package com.omnicrola.voxel.commands;

import com.omnicrola.voxel.data.level.LevelDefinition;
import com.omnicrola.voxel.eventBus.VoxelEventBus;
import com.omnicrola.voxel.eventBus.events.UpdateAvailableLevelsEvent;
import com.omnicrola.voxel.network.INetworkManager;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.world.CommandPackage;

import java.util.List;

/**
 * Created by Eric on 3/27/2016.
 */
public class CreateMultiplayerGameCommand extends AbstractWorldCommand {
    public CreateMultiplayerGameCommand() {
    }

    @Override
    public void execute(CommandPackage commandPackage) {
        INetworkManager networkManager = commandPackage.getNetworkManager();
        networkManager.startLocalMultiplayerServer();
        commandPackage.getUiManager().changeScreen(UiScreen.MULTIPLAYER_CREATE);
        
        List<LevelDefinition> levels = commandPackage.getLevelManager().getAllLevels();
        VoxelEventBus.INSTANCE().post(new UpdateAvailableLevelsEvent(levels));
    }

    @Override
    public boolean isLocal() {
        return true;
    }
}
