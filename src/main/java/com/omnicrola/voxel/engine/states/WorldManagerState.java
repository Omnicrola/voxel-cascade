package com.omnicrola.voxel.engine.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.commands.ICommandProcessor;
import com.omnicrola.voxel.commands.ILocalCommand;
import com.omnicrola.voxel.data.GameXmlDataParser;
import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.data.level.LevelDefinitionRepository;
import com.omnicrola.voxel.data.level.LevelStateLoader;
import com.omnicrola.voxel.engine.ITickProvider;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.entities.Unit;
import com.omnicrola.voxel.network.ClientNetworkState;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.ui.Cursor2dProvider;
import com.omnicrola.voxel.world.*;

import java.util.List;

/**
 * Created by Eric on 2/22/2016.
 */
public class WorldManagerState extends AbstractAppState implements ICommandProcessor {

    private WorldMessageProcessor worldMessageProcessor;
    private ITickProvider ticProvider;
    private GameXmlDataParser gameDataParser;
    private Cursor2dProvider cursor2dProvider;
    private MessagePackage messagePackage;
    private LevelManager levelManager;
    private WorldManager worldManager;

    public WorldManagerState(GameXmlDataParser gameDataParser, Cursor2dProvider cursor2dProvider) {
        this.gameDataParser = gameDataParser;
        this.cursor2dProvider = cursor2dProvider;
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
        this.worldManager = new WorldManager(voxelGameEngine.getWorldNode());
        WorldEntityBuilder entityBuilder = new WorldEntityBuilder();

        LevelStateLoader levelStateLoader = new LevelStateLoader(
                voxelGameEngine,
                clientNetworkState,
                voxelTerrainState,
                this.worldManager,
                entityBuilder,
                this.cursor2dProvider);
        this.levelManager = new LevelManager(levelDefinitionRepository, levelStateLoader);
        this.messagePackage = new MessagePackage(
                this.levelManager,
                uiState,
                clientNetworkState,
                entityBuilder,
                this.worldManager);
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

    public List<Unit> getAllUnits() {
        return this.worldManager.getAllUnits();
    }
}
