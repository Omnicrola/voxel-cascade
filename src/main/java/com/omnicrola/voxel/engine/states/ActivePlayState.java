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
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.builders.ActivePlayUiBuilder;
import com.omnicrola.voxel.ui.controllers.UserActionGuiAdapter;

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
    private GameXmlDataParser gameDataParser;
    private LevelDefinitionRepository levelDefinitions;
    private List<ILevelChangeObserver> observers;

    public ActivePlayState(GameXmlDataParser gameDataParser) {
        super("Active Play");
        this.gameDataParser = gameDataParser;
        this.observers= new ArrayList<>();
    }

    @Override
    protected void voxelInitialize(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;
        this.levelDefinitions = this.gameDataParser.loadLevels(GameConstants.LEVEL_DEFINITIONS);

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

        SetMoveCursorStrategyListener moveListener = new SetMoveCursorStrategyListener(this);
        SetAttackCursorStrategyListener attackListener = new SetAttackCursorStrategyListener(this);
        OrderSelectedUnitsStopListeners stopListener = new OrderSelectedUnitsStopListeners(this);

        addStateInput(GameInputAction.ORDER_MOVE, moveListener);
        addStateInput(GameInputAction.ORDER_ATTACK, attackListener);
        addStateInput(GameInputAction.ORDER_STOP, stopListener);

        UserActionGuiAdapter actionAdapter = new UserActionGuiAdapter(moveListener, attackListener, stopListener);
        ActivePlayUiBuilder.build(gameContainer, this, actionAdapter);
    }

    public void loadLevel(UUID levelId) {
        detatchStateNodes();
        if (this.currentLevelState != null) {
            this.currentLevelState.dispose();
        }
        LevelDefinition levelDefinition = levelDefinitions.getLevel(levelId);
        this.currentLevelState = LevelStateFactory.create(levelDefinition, this.gameContainer);

        setStateRootNode(new GameStateNode(this.currentLevelState));
        attachStateNodes();
        notifyObserversLevelChanged();
    }

    private void notifyObserversLevelChanged() {
        this.observers.forEach(o->o.levelChanged(this.currentLevelState));
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
        gameContainer.gui().setCameraRotation(new Quaternion(-0.021947166f, 0.9495664f, -0.06827275f, -0.30525514f));
        gameContainer.gui().setCameraPosition(new Vector3f(10, 0, 10));
        gameContainer.gui().changeScreens(UiScreen.ACTIVE_PLAY);
    }

    @Override
    protected void voxelDisable(IGameContainer gameContainer) {
    }
}
