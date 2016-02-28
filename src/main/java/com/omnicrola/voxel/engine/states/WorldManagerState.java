package com.omnicrola.voxel.engine.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.commands.ICommandProcessor;
import com.omnicrola.voxel.commands.ILocalCommand;
import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.engine.ITickProvider;
import com.omnicrola.voxel.engine.ShutdownHandler;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.entities.Unit;
import com.omnicrola.voxel.network.ClientNetworkState;
import com.omnicrola.voxel.ui.UiManager;
import com.omnicrola.voxel.world.*;

import java.util.List;

/**
 * Created by Eric on 2/22/2016.
 */
public class WorldManagerState extends AbstractAppState implements ICommandProcessor {

    private WorldMessageProcessor worldMessageProcessor;
    private ITickProvider ticProvider;
    private MessagePackage messagePackage;
    private LevelManager levelManager;
    private WorldManager worldManager;
    private WorldEntityBuilder entityBuilder;

    public WorldManagerState(ITickProvider ticProvider,
                             LevelManager levelManager,
                             WorldManager worldManager,
                             WorldEntityBuilder entityBuilder) {
        this.ticProvider = ticProvider;
        this.levelManager = levelManager;
        this.worldManager = worldManager;
        this.entityBuilder = entityBuilder;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        VoxelGameEngine  voxelGameEngine = (VoxelGameEngine) app;

        ClientNetworkState clientNetworkState = stateManager.getState(ClientNetworkState.class);
        UiManager uiManager = new UiManager(voxelGameEngine.getNiftyGui());
        ShutdownHandler shutdownHandler = new ShutdownHandler(voxelGameEngine);

        this.messagePackage = new MessagePackage(
                shutdownHandler,
                this.levelManager,
                clientNetworkState,
                entityBuilder,
                uiManager,
                this.worldManager);
        this.worldMessageProcessor = new WorldMessageProcessor(this.messagePackage);
    }

    public void addCommand(IWorldMessage worldCommand) {
        this.worldMessageProcessor.addMessage(worldCommand);
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        this.worldMessageProcessor.handleMessages(ticProvider.getTic());
    }

    @Override
    public void executeCommand(ILocalCommand localCommand) {
        System.out.println("handleMessages: " + localCommand);
        localCommand.execute(this.messagePackage);
    }

    public List<Unit> getAllUnits() {
        return this.worldManager.getAllUnits();
    }

    public LevelManager getLevelManager() {
        return this.levelManager;
    }

    public WorldManager getWorldManager() {
        return this.worldManager;
    }
}
