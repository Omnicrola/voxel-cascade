package com.omnicrola.voxel.commands;

import com.omnicrola.voxel.server.main.init.VoxelServerLauncher;
import com.omnicrola.voxel.world.CommandPackage;

/**
 * Created by Eric on 2/24/2016.
 */
public class StartMultiplayerServerCommand implements ILocalCommand {
    private VoxelServerLauncher serverLauncher;

    public StartMultiplayerServerCommand(VoxelServerLauncher serverLauncher) {
        this.serverLauncher = serverLauncher;
    }

    @Override
    public void execute(CommandPackage commandPackage) {
        this.serverLauncher.launch();
    }
}
