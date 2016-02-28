package com.omnicrola.voxel.commands;

import com.omnicrola.voxel.world.CommandPackage;

/**
 * Created by omnic on 2/28/2016.
 */
public class WorldCommandProcessor implements ICommandProcessor {

    private final CommandPackage commandPackage;
    private final INetworkCommandQueue messageQueue;

    public WorldCommandProcessor(CommandPackage commandPackage, INetworkCommandQueue messageQueue) {
        this.commandPackage = commandPackage;
        this.messageQueue = messageQueue;
    }

    @Override
    public void executeCommand(IWorldCommand worldCommand) {
        if (worldCommand.isLocal()) {
            worldCommand.execute(this.commandPackage);
        } else {
            this.messageQueue.add(worldCommand);
        }
    }
}
