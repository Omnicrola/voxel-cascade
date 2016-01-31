package com.omnicrola.voxel.engine.states;

import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.data.GameXmlDataParser;
import com.omnicrola.voxel.data.level.*;
import com.omnicrola.voxel.input.GameInputAction;
import com.omnicrola.voxel.input.listeners.*;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.settings.GameConstants;
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

    private class DebugReloadListener implements ActionListener {

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (!isPressed) {
                levelDefinitions = gameDataParser.loadLevels(GameConstants.LEVEL_DEFINITIONS);
                loadLevel(LevelGeneratorTool.BASIC_LEVEL_UUID);
            }
        }
    }

    private class MouseLookListener implements ActionListener {

        private IGameContainer gameContainer;

        public MouseLookListener(IGameContainer gameContainer) {
            this.gameContainer = gameContainer;
        }

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            currentLevelState.getWorldCursor().setVisible(!isPressed);
            this.gameContainer.input().setMouseGrabbed(isPressed);
        }
    }

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
        this.levelStateFactory = new LevelStateFactory(new VoxelTerrainGenerator(gameContainer), gameContainer);

        initializeKeybindings(gameContainer);

        ActivePlayUiBuilder.build(gameContainer, this);
    }

    public void loadLevel(UUID levelId) {
        detatchStateNodes();
        if (this.currentLevelState != null) {
            this.currentLevelState.dispose();
        }
        LevelDefinition levelDefinition = levelDefinitions.getLevel(levelId);
        this.currentLevelState = this.levelStateFactory.create(levelDefinition);

        setStateRootNode(new GameStateNode(this.currentLevelState));
        this.gameContainer.gui().setCameraRotation(levelDefinition.getCameraOrientation());
        this.gameContainer.gui().setCameraPosition(levelDefinition.getCameraPosition());

        attachStateNodes();
        notifyObserversLevelChanged();
    }

    private void initializeKeybindings(IGameContainer gameContainer) {
        addStateInput(GameInputAction.DEBUG_TOGGLE_MOUSE_LOOK, new MouseLookListener(gameContainer));
        addStateInput(GameInputAction.DEBUG_RELOAD_LEVEL, new DebugReloadListener());

        addStateInput(GameInputAction.CLEAR_SELECTION, new ClearSelectionListener(this));

        addStateInput(GameInputAction.MOUSE_PRIMARY, new ExecutePrimaryCursorListener(this));
        addStateInput(GameInputAction.MOUSE_SECONDARY, new ExecuteSecondaryCursorListener(this));

        PanCameraListener panCameraListener = new PanCameraListener(gameContainer.gui(), gameContainer.input());
        panCameraListener.registerInputs(this);

        addStateInput(GameInputAction.ORDER_MOVE, new SetMoveCursorStrategyListener(this, gameContainer.gui()));
        addStateInput(GameInputAction.ORDER_ATTACK, new SetAttackCursorStrategyListener(this, gameContainer.gui()));
        addStateInput(GameInputAction.ORDER_STOP, new OrderSelectedUnitsStopListeners(this));
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
    }

    @Override
    protected void voxelDisable(IGameContainer gameContainer) {
    }
}
