package com.omnicrola.voxel.commands;

import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.engine.IShutdown;
import com.omnicrola.voxel.network.INetworkManager;
import com.omnicrola.voxel.ui.IUiManager;
import com.omnicrola.voxel.world.CommandPackage;
import com.omnicrola.voxel.world.WorldEntityBuilder;
import com.omnicrola.voxel.world.WorldManager;

/**
 * Created by omnic on 2/28/2016.
 */
public class WorldCommandProcessor implements ICommandProcessor {

    private CommandPackage commandPackage;
    private final INetworkCommandQueue networkCommandQueue;
    private IShutdown shutdown;
    private LevelManager levelManager;
    private INetworkManager networkManager;
    private WorldEntityBuilder entityBuilder;
    private IUiManager uiManager;
    private WorldManager worldManager;

    public WorldCommandProcessor(INetworkCommandQueue networkCommandQueue,
                                 IShutdown shutdown,
                                 LevelManager levelManager,
                                 INetworkManager networkManager,
                                 WorldEntityBuilder entityBuilder,
                                 IUiManager uiManager,
                                 WorldManager worldManager) {
        this.networkCommandQueue = networkCommandQueue;
        this.shutdown = shutdown;
        this.levelManager = levelManager;
        this.networkManager = networkManager;
        this.entityBuilder = entityBuilder;
        this.uiManager = uiManager;
        this.worldManager = worldManager;
    }

    @Override
    public void executeCommand(IWorldCommand worldCommand) {
        if (worldCommand.isLocal()) {
            worldCommand.execute(getCommandPackage());
        } else {
            this.networkCommandQueue.add(worldCommand);
        }
    }

    private CommandPackage getCommandPackage() {
        if (this.commandPackage == null) {
            this.commandPackage = new CommandPackage(shutdown, levelManager, networkManager, entityBuilder, uiManager, worldManager);
        }
        return this.commandPackage;
    }
}
