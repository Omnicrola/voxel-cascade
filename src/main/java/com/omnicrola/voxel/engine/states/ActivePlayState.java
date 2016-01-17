package com.omnicrola.voxel.engine.states;

import com.jme3.input.controls.ActionListener;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.omnicrola.voxel.data.level.LevelData;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.data.level.LevelStateGenerator;
import com.omnicrola.voxel.input.GameInputAction;
import com.omnicrola.voxel.input.UserInteractionHandler;
import com.omnicrola.voxel.input.listeners.*;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.ui.UiSelectionObserver;
import com.omnicrola.voxel.ui.UserInterface;
import com.omnicrola.voxel.ui.UserInterfaceGenerator;

/**
 * Created by omnic on 1/15/2016.
 */
public class ActivePlayState extends VoxelGameState {

    private class DebugReloadListener implements ActionListener {

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (!isPressed) {
                loadLevel(new LevelData());
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

    public ActivePlayState() {
        super("Active Play");
    }

    @Override
    protected void voxelInitialize(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;

        this.userInteractionHandler = new UserInteractionHandler(this.stateRootNode, gameContainer);
        UserInterface userInterface = UserInterfaceGenerator.createPlayUi(gameContainer);
        this.stateRootUiNode.attachChild(userInterface);
        this.userInteractionHandler.addSelectionListener(new UiSelectionObserver(userInterface));

        addStateInput(GameInputAction.DEBUG_RELOAD_LEVEL, new DebugReloadListener());
        addStateInput(GameInputAction.MOUSE_LOOK, new MouseLookListener());
        addStateInput(GameInputAction.MOUSE_SELECT, new UserSelectionListener(userInteractionHandler));
        addStateInput(GameInputAction.CLEAR_SELECTION, new ClearSelectionListener(userInteractionHandler));

        addStateInput(GameInputAction.ORDER_MOVE, new OrderMoveListener(userInteractionHandler));
        addStateInput(GameInputAction.ORDER_STOP, new OrderStopListener(userInteractionHandler));
        addStateInput(GameInputAction.ORDER_ATTACK, new OrderAttackListener(userInteractionHandler));

        addStateInput(GameInputAction.DEBUG_BUILD_1, new SetBuildSelectionListener(1, userInteractionHandler));
        addStateInput(GameInputAction.DEBUG_BUILD_2, new SetBuildSelectionListener(2, userInteractionHandler));
    }

    public void loadLevel(LevelData levelData) {
        this.stateRootNode.detachAllChildren();
        this.currentLevelState = LevelStateGenerator.create(levelData, this.gameContainer);
        this.userInteractionHandler.setLevel(this.currentLevelState);

        this.stateRootNode.attachChild(this.currentLevelState.getTerrain());
        this.stateRootNode.attachChild(this.currentLevelState.getEntities());
        this.stateRootNode.attachChild(this.currentLevelState.getWorldCursor());
        this.stateRootNode.addLight(this.currentLevelState.getSun());
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
