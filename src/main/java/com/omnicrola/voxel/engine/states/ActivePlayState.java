package com.omnicrola.voxel.engine.states;

import com.omnicrola.voxel.data.GameXmlDataParser;
import com.omnicrola.voxel.data.level.LevelDefinition;
import com.omnicrola.voxel.data.level.LevelDefinitionRepository;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.data.level.LevelStateFactory;
import com.omnicrola.voxel.input.GameInputAction;
import com.omnicrola.voxel.input.listeners.ClearSelectionListener;
import com.omnicrola.voxel.input.listeners.ExecutePrimaryCursorListener;
import com.omnicrola.voxel.input.listeners.ExecuteSecondaryCursorListener;
import com.omnicrola.voxel.input.listeners.PanCameraListener;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.terrain.ChunkHandlerFactory;
import com.omnicrola.voxel.terrain.PerlinNoiseGenerator;
import com.omnicrola.voxel.terrain.VoxelTerrainGenerator;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.builders.ActivePlayUiBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by omnic on 1/15/2016.
 */
public class ActivePlayState extends VoxelGameState implements ICurrentLevelProvider {

    private IGameContainer gameContainer;
    private LevelState currentLevelState;
    private GameXmlDataParser gameDataParser;
    private LevelStateFactory levelStateFactory;
    private LevelDefinitionRepository levelDefinitions;
    private List<ILevelChangeObserver> observers;

    public ActivePlayState(GameXmlDataParser gameDataParser) {
        super("Active Play");
        this.gameDataParser = gameDataParser;
        this.observers = new ArrayList<>();
    }

    @Override
    protected void voxelInitialize(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;
        this.levelDefinitions = this.gameDataParser.loadLevels(GameConstants.LEVEL_DEFINITIONS);
        ChunkHandlerFactory chunkHandlerFactory = new ChunkHandlerFactory(gameContainer);
        VoxelTerrainGenerator voxelTerrainGenerator = new VoxelTerrainGenerator(chunkHandlerFactory, new PerlinNoiseGenerator());
        this.levelStateFactory = new LevelStateFactory(voxelTerrainGenerator, gameContainer);

        initializeKeybindings(gameContainer);

        ActivePlayUiBuilder.build(gameContainer, this);
    }

    public void loadLevel(UUID levelId) {
        LevelDefinition levelDefinition = levelDefinitions.getLevel(levelId);
        loadLevel(levelDefinition);
    }

    public void loadLevel(LevelDefinition newLevelDefinition) {
        if (this.currentLevelState != null) {
            this.currentLevelState.detatchFromWorld(this.gameContainer);
            this.currentLevelState.dispose();
        }
        this.currentLevelState = this.levelStateFactory.create(newLevelDefinition);
        this.currentLevelState.attachToWorld(this.gameContainer);

        this.gameContainer.gui().setCameraRotation(newLevelDefinition.getCameraOrientation());
        this.gameContainer.gui().setCameraPosition(newLevelDefinition.getCameraPosition());

        notifyObserversLevelChanged();
    }

    private void initializeKeybindings(IGameContainer gameContainer) {

        addStateInput(GameInputAction.CLEAR_SELECTION, new ClearSelectionListener(this));

        ExecutePrimaryCursorListener primaryCursorListener = new ExecutePrimaryCursorListener(this);
        addStateInput(GameInputAction.MULTI_SELECT, primaryCursorListener);
        addStateInput(GameInputAction.MOUSE_PRIMARY, primaryCursorListener);
        addStateInput(GameInputAction.MOUSE_SECONDARY, new ExecuteSecondaryCursorListener(this));

        PanCameraListener panCameraListener = new PanCameraListener(gameContainer.gui(), gameContainer.input());
        panCameraListener.registerInputs(this);
    }

    private void notifyObserversLevelChanged() {
        this.observers.forEach(o -> o.levelChanged(this.currentLevelState));
    }

    @Override
    public LevelState getCurrentLevelState() {
        return this.currentLevelState;
    }

    @Override
    public void subscribe(ILevelChangeObserver levelChangeObserver) {
        this.observers.add(levelChangeObserver);
    }

    @Override
    protected void voxelEnable(IGameContainer gameContainer) {
        gameContainer.input().setCameraMoveSpeed(10);
        gameContainer.gui().changeScreens(UiScreen.ACTIVE_PLAY);
        if (this.currentLevelState != null) {
            this.currentLevelState.attachToWorld(this.gameContainer);
        }
    }

    @Override
    protected void voxelDisable(IGameContainer gameContainer) {
        if (this.currentLevelState != null) {
            this.currentLevelState.detatchFromWorld(this.gameContainer);
        }
    }
}
