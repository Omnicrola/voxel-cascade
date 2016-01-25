package com.omnicrola.voxel.engine.states;

import com.jme3.input.controls.ActionListener;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.omnicrola.voxel.data.GameXmlDataParser;
import com.omnicrola.voxel.data.level.*;
import com.omnicrola.voxel.input.GameInputAction;
import com.omnicrola.voxel.input.listeners.*;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.jme.wrappers.IGameGui;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.ui.UiSelectionObserver;
import com.omnicrola.voxel.ui.UserInterface;
import com.omnicrola.voxel.ui.builders.UserInterfaceBuilder;

import java.util.UUID;

/**
 * Created by omnic on 1/15/2016.
 */
public class ActivePlayState extends VoxelGameState implements ICurrentLevelProvider {

    private GameXmlDataParser gameDataParser;
    private LevelDefinitionRepository levelDefinitions;
    private UserInterface userInterface;

    private class DebugReloadListener implements ActionListener {

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (!isPressed) {
                loadLevel(LevelGeneratorTool.BASIC_LEVEL_UUID);
            }
        }
    }

    private class MouseLookListener implements ActionListener {

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            currentLevelState.getWorldCursor().setVisible(!isPressed);
            gameContainer.input().setMouseGrabbed(isPressed);
        }
    }

    private IGameContainer gameContainer;
    private LevelState currentLevelState;

    public ActivePlayState(GameXmlDataParser gameDataParser) {
        super("Active Play");
        this.gameDataParser = gameDataParser;
    }

    @Override
    protected void voxelInitialize(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;
        this.levelDefinitions = this.gameDataParser.loadLevels(GameConstants.LEVEL_DEFINITIONS);
        this.userInterface = UserInterfaceBuilder.createPlayUi(gameContainer);
        this.stateRootUiNode.attachChild(userInterface);

        addStateInput(GameInputAction.DEBUG_TOGGLE_MOUSE_LOOK, new MouseLookListener());
        addStateInput(GameInputAction.DEBUG_RELOAD_LEVEL, new DebugReloadListener());

        addStateInput(GameInputAction.CLEAR_SELECTION, new ClearSelectionListener(this));

        addStateInput(GameInputAction.MOUSE_PRIMARY, new ExecutePrimaryCursorListener(this));
        addStateInput(GameInputAction.MOUSE_SECONDARY, new ExecuteSecondaryCursorListener(this));

        IGameGui input = gameContainer.gui();
        addStateInput(GameInputAction.ARROW_UP, new PanCameraForwardListener(input));
        addStateInput(GameInputAction.ARROW_DOWN, new PanCameraBackwardListener(input));
        addStateInput(GameInputAction.ARROW_LEFT, new PanCameraLeftListener(input));
        addStateInput(GameInputAction.ARROW_RIGHT, new PanCameraRightListener(input));

        addStateInput(GameInputAction.ORDER_MOVE, new SetMoveCursorStrategyListener(this));
        addStateInput(GameInputAction.ORDER_ATTACK, new SetAttackCursorStrategyListener(this));
        addStateInput(GameInputAction.ORDER_STOP, new OrderSelectedUnitsStopListeners(this));
        addStateInput(GameInputAction.ORDER_BUILD_MODE, new ToggleBuildModeListener(this.userInterface));
    }

    public void loadLevel(UUID levelId) {
        detatchStateNodes();
        if (this.currentLevelState != null) {
            this.currentLevelState.dispose();
        }
        LevelDefinition levelDefinition = levelDefinitions.getLevel(levelId);
        this.currentLevelState = LevelStateFactory.create(levelDefinition, this.gameContainer);

        setStateRootNode(new GameStateNode(this.currentLevelState));
        this.currentLevelState.getWorldCursor().addSelectionObserver(new UiSelectionObserver(this.userInterface));
        attachStateNodes();
    }

    @Override
    public LevelState getCurrentLevelState() {
        return this.currentLevelState;
    }

    @Override
    protected void voxelEnable(IGameContainer gameContainer) {
        gameContainer.input().setCameraMoveSpeed(10);
        gameContainer.gui().setCameraRotation(new Quaternion(-0.021947166f, 0.9495664f, -0.06827275f, -0.30525514f));
        gameContainer.gui().setCameraPosition(new Vector3f(10, 0, 10));
    }

    @Override
    protected void voxelDisable(IGameContainer gameContainer) {
    }
}
