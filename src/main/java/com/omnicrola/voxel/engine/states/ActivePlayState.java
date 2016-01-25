package com.omnicrola.voxel.engine.states;

import com.jme3.input.controls.ActionListener;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.omnicrola.voxel.data.GameXmlDataParser;
import com.omnicrola.voxel.data.level.*;
import com.omnicrola.voxel.input.GameInputAction;
import com.omnicrola.voxel.input.UserInteractionHandler;
import com.omnicrola.voxel.input.listeners.SetAttackCursorStrategyListener;
import com.omnicrola.voxel.input.listeners.*;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.ui.UiSelectionObserver;
import com.omnicrola.voxel.ui.UserInterface;
import com.omnicrola.voxel.ui.UserInterfaceGenerator;

import java.util.UUID;

/**
 * Created by omnic on 1/15/2016.
 */
public class ActivePlayState extends VoxelGameState implements ICurrentLevelProvider {

    private GameXmlDataParser gameDataParser;
    private LevelDefinitionRepository levelDefinitions;

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

    private UserInteractionHandler userInteractionHandler;
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

        this.userInteractionHandler = new UserInteractionHandler(gameContainer);
        UserInterface userInterface = UserInterfaceGenerator.createPlayUi(gameContainer);

        this.stateRootUiNode.attachChild(userInterface);
        this.userInteractionHandler.addSelectionListener(new UiSelectionObserver(userInterface));

        addStateInput(GameInputAction.DEBUG_RELOAD_LEVEL, new DebugReloadListener());
        addStateInput(GameInputAction.CLEAR_SELECTION, new ClearSelectionListener(userInteractionHandler));

        addStateInput(GameInputAction.MOUSE_PRIMARY, new ExecutePrimaryCursorListener(this));
        addStateInput(GameInputAction.MOUSE_PRIMARY, new ExecuteSecondaryCursorListener(this));

        addStateInput(GameInputAction.ORDER_MOVE, new SetMoveCursorStrategyListener(this));
        addStateInput(GameInputAction.ORDER_ATTACK, new SetAttackCursorStrategyListener(this));
        addStateInput(GameInputAction.ORDER_STOP, new OrderSelectedUnitsStopListeners(this));

//        addStateInput(GameInputAction.MOUSE_PRIMARY, new UserSelectionListener(gameContainer.input(), userInteractionHandler));
//        addStateInput(GameInputAction.MOUSE_SECONDARY, new MouseLookListener());
//        addStateInput(GameInputAction.ORDER_MOVE, new OrderMoveListener(userInteractionHandler));
//        addStateInput(GameInputAction.ORDER_STOP, new OrderStopListener(userInteractionHandler));
//        addStateInput(GameInputAction.ORDER_ATTACK, new OrderAttackListener(userInteractionHandler));
//
//        addStateInput(GameInputAction.ORDER_BUILD_MODE, new ToggleBuildModeListener(this.userInteractionHandler));
//        addStateInput(GameInputAction.ORDER_BUILD_SELECT_1, new BuildSelectedItemListener(userInteractionHandler, 1));
//        addStateInput(GameInputAction.ORDER_BUILD_SELECT_2, new BuildSelectedItemListener(userInteractionHandler, 2));

    }

    public void loadLevel(UUID levelId) {
        detatchStateNodes();
        LevelDefinition levelDefinition = levelDefinitions.getLevel(levelId);
        this.currentLevelState = LevelStateFactory.create(levelDefinition, this.gameContainer);
        this.userInteractionHandler.setLevel(this.currentLevelState);

        setStateRootNode(new GameStateNode(this.currentLevelState));
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
