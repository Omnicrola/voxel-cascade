package com.omnicrola.voxel.commands;

import com.omnicrola.voxel.world.CommandPackage;

/**
 * Created by Eric on 2/27/2016.
 */
public class ShutdownAndExitCommand extends AbstractWorldCommand {
    @Override
    public void execute(CommandPackage commandPackage) {
        commandPackage.shutdownAndExit();
    }

    @Override
    public boolean isLocal() {
        return true;
    }
}
