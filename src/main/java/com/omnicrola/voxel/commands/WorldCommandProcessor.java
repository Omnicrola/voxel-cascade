package com.omnicrola.voxel.commands;

import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.engine.IShutdown;
import com.omnicrola.voxel.entities.behavior.ai.NavigationGridDistributor;
import com.omnicrola.voxel.jme.wrappers.IParticleBuilder;
import com.omnicrola.voxel.network.INetworkManager;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.ui.IUiManager;
import com.omnicrola.voxel.world.CommandPackage;
import com.omnicrola.voxel.world.WorldManager;
import com.omnicrola.voxel.world.build.WorldEntityBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by omnic on 2/28/2016.
 */
public class WorldCommandProcessor implements ICommandProcessor {

    private static final Logger LOGGER = Logger.getLogger(WorldCommandProcessor.class.getName());

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
    private AppStateManager stateManager;
    private IParticleBuilder particleBuilder;

    public WorldCommandProcessor(INetworkCommandQueue networkCommandQueue,
                                 IShutdown shutdown,
                                 LevelManager levelManager,
                                 INetworkManager networkManager,
                                 WorldEntityBuilder entityBuilder,
                                 IUiManager uiManager,
                                 WorldManager worldManager,
                                 ITerrainManager terrainManager,
                                 AppStateManager stateManager,
                                 IParticleBuilder particleBuilder) {
        this.networkCommandQueue = networkCommandQueue;
        this.shutdown = shutdown;
        this.levelManager = levelManager;
        this.networkManager = networkManager;
        this.entityBuilder = entityBuilder;
        this.uiManager = uiManager;
        this.worldManager = worldManager;
        this.terrainManager = terrainManager;
        this.stateManager = stateManager;
        this.particleBuilder = particleBuilder;
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
            EntityCommandAdapter entityCommandAdapter = new EntityCommandAdapter(new NavigationGridDistributor());
            this.commandPackage = new CommandPackage(
                    this.shutdown,
                    this.levelManager,
                    this.networkManager,
                    this.entityBuilder,
                    this.uiManager,
                    this.worldManager,
                    this,
                    this.terrainManager,
                    entityCommandAdapter,
                    this.stateManager,
                    this.particleBuilder);
        }
        return this.commandPackage;
    }
}
