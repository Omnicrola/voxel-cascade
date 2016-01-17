package com.omnicrola.voxel.engine.states;

import com.jme3.input.controls.ActionListener;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.omnicrola.voxel.data.LevelData;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.data.level.LevelStateGenerator;
import com.omnicrola.voxel.engine.input.GameInputAction;
import com.omnicrola.voxel.engine.input.UserInteractionHandler;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.ui.UiSelectionObserver;
import com.omnicrola.voxel.ui.UserInterface;
import com.omnicrola.voxel.ui.UserInterfaceGenerator;


/**
 * Created by omnic on 1/15/2016.
 */
public class ActivePlayState extends VoxelGameState {


    private class ReloadListener implements ActionListener {

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (!isPressed) {
                loadLevel(new LevelData());
            }
        }
    }

    private class SelectBuildEntity implements ActionListener {

        private int entityType;

        public SelectBuildEntity(int entityType) {
            this.entityType = entityType;
        }

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (!isPressed) {
                userInteractionHandler.setBuildMode(this.entityType);
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

    private class SelectionListener implements ActionListener {

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (!isPressed) {
                userInteractionHandler.activateSelection();
            }
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
        userInteractionHandler.addSelectionListener(new UiSelectionObserver(userInterface));

        addStateInput(GameInputAction.DEBUG_RELOAD_LEVEL, new ReloadListener());
        addStateInput(GameInputAction.MOUSE_LOOK, new MouseLookListener());
        addStateInput(GameInputAction.MOUSE_SELECT, new SelectionListener());
        addStateInput(GameInputAction.BUILD_1, new SelectBuildEntity(1));
        addStateInput(GameInputAction.BUILD_2, new SelectBuildEntity(2));
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
