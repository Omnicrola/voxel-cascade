package com.omnicrola.voxel.engine.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.commands.ICommandProcessor;
import com.omnicrola.voxel.commands.ILocalCommand;
import com.omnicrola.voxel.data.GameXmlDataParser;
import com.omnicrola.voxel.data.level.LevelDefinitionRepository;
import com.omnicrola.voxel.engine.ITickProvider;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.network.ClientNetworkState;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.world.*;

/**
 * Created by Eric on 2/22/2016.
 */
public class WorldManagerState extends AbstractAppState implements ICommandProcessor {

    private WorldMessageProcessor worldMessageProcessor;
    private ITickProvider ticProvider;
    private GameXmlDataParser gameDataParser;
    private MessagePackage messagePackage;

    public WorldManagerState(GameXmlDataParser gameDataParser) {
        this.gameDataParser = gameDataParser;
    }

    public void addCommand(IWorldMessage worldCommand) {
        this.worldMessageProcessor.addMessage(worldCommand);
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        VoxelGameEngine voxelGameEngine = (VoxelGameEngine) app;
        LevelDefinitionRepository levelDefinitionRepository = this.gameDataParser.loadLevels(GameConstants.LEVEL_DEFINITIONS);

        VoxelTerrainState voxelTerrainState = stateManager.getState(VoxelTerrainState.class);
        UiState uiState = stateManager.getState(UiState.class);
        ClientNetworkState clientNetworkState = stateManager.getState(ClientNetworkState.class);
        WorldEntityBuilder worldEntityBuilder = new WorldEntityBuilder(voxelGameEngine);
        WorldEntityManager worldEntityManager = new WorldEntityManager();
        this.messagePackage = new MessagePackage(
                worldEntityBuilder,
                worldEntityManager,
                voxelTerrainState,
                levelDefinitionRepository,
                uiState,
                clientNetworkState);
        this.worldMessageProcessor = new WorldMessageProcessor(this.messagePackage);
        this.ticProvider = voxelGameEngine.getTicProvider();
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

}
