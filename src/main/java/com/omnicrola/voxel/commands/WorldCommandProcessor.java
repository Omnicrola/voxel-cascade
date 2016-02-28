package com.omnicrola.voxel.commands;

import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.engine.IShutdown;
import com.omnicrola.voxel.network.INetworkManager;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.ui.IUiManager;
import com.omnicrola.voxel.world.CommandPackage;
import com.omnicrola.voxel.world.WorldEntityBuilder;
import com.omnicrola.voxel.world.WorldManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omnic on 2/28/2016.
 */
public class WorldCommandProcessor implements ICommandProcessor {

    private final List<IWorldCommand> commands;
    private final List<IWorldCommand> commandCopy;

    private CommandPackage commandPackage;
    private final INetworkCommandQueue networkCommandQueue;
    private IShutdown shutdown;
    private LevelManager levelManager;
    private INetworkManager networkManager;
    private WorldEntityBuilder entityBuilder;
    private IUiManager uiManager;
    private WorldManager worldManager;
    private ITerrainManager terrainManager;

    public WorldCommandProcessor(INetworkCommandQueue networkCommandQueue,
                                 IShutdown shutdown,
                                 LevelManager levelManager,
                                 INetworkManager networkManager,
                                 WorldEntityBuilder entityBuilder,
                                 IUiManager uiManager,
                                 WorldManager worldManager,
                                 ITerrainManager terrainManager) {
        this.networkCommandQueue = networkCommandQueue;
        this.shutdown = shutdown;
        this.levelManager = levelManager;
        this.networkManager = networkManager;
        this.entityBuilder = entityBuilder;
        this.uiManager = uiManager;
        this.worldManager = worldManager;
        this.terrainManager = terrainManager;
        this.commands = new ArrayList<>();
        this.commandCopy = new ArrayList<>();
    }

    @Override
    public void addCommand(IWorldCommand worldCommand) {
        this.commands.add(worldCommand);
    }

    public void update() {
        this.commandCopy.clear();
        this.commandCopy.addAll(this.commands);
        this.commands.clear();
        this.commandCopy.forEach(c -> processCommand(c));
    }

    private void processCommand(IWorldCommand worldCommand) {
        if (worldCommand.isLocal()) {
            worldCommand.execute(getCommandPackage());
        } else {
            this.networkCommandQueue.add(worldCommand);
        }
    }

    private CommandPackage getCommandPackage() {
        if (this.commandPackage == null) {
            this.commandPackage = new CommandPackage(
                    shutdown,
                    levelManager,
                    networkManager,
                    entityBuilder,
                    uiManager,
                    worldManager,
                    this,
                    terrainManager);
        }
        return this.commandPackage;
    }
}
