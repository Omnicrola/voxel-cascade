package com.omnicrola.voxel.engine.states;

import com.jme3.collision.CollisionResult;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.LevelData;
import com.omnicrola.voxel.data.entities.EntityDefinition;
import com.omnicrola.voxel.data.level.LevelEntityGenerator;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.data.level.LevelStateGenerator;
import com.omnicrola.voxel.engine.input.GameInputAction;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;

import java.util.Optional;


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
                isBuilding = true;
                selectedBuildType = this.entityType;
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
                if (isBuilding) {
                    ColorRGBA color = selectedBuildType == 1 ? ColorRGBA.Orange : ColorRGBA.Green;
                    Vector3f position = currentLevelState.getWorldCursor().getWorldTranslation();
                    Spatial entity = LevelEntityGenerator.createEntity(new EntityDefinition(position, color), gameContainer);
                    stateRootNode.attachChild(entity);
                    isBuilding = false;
                } else {
                    Optional<CollisionResult> entityUnderCursor = currentLevelState.getWorldCursor().getEntityUnderCursor(stateRootNode);
                    if (entityUnderCursor.isPresent()) {
                        Geometry geometry = entityUnderCursor.get().getGeometry();
                        System.out.println(geometry.getName() + " -> " + entityUnderCursor.get().getContactPoint());
                    }
                }
            }
        }
    }

    private IGameContainer gameContainer;
    private LevelState currentLevelState;
    private boolean isBuilding;
    private int selectedBuildType;

    public ActivePlayState() {
        super("Active Play");
    }

    @Override
    protected void voxelInitialize(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;
        addStateInput(GameInputAction.RELOAD_LEVEL, new ReloadListener());
        addStateInput(GameInputAction.MOUSE_LOOK, new MouseLookListener());
        addStateInput(GameInputAction.MOUSE_SELECT, new SelectionListener());
        addStateInput(GameInputAction.BUILD_1, new SelectBuildEntity(1));
        addStateInput(GameInputAction.BUILD_2, new SelectBuildEntity(2));
        addStateInput(GameInputAction.BUILD_3, new SelectBuildEntity(3));
    }

    public void loadLevel(LevelData levelData) {
        this.stateRootNode.detachAllChildren();
        this.currentLevelState = LevelStateGenerator.create(levelData, this.gameContainer);
        this.stateRootNode.attachChild(this.currentLevelState.getTerrain());
        this.stateRootNode.attachChild(this.currentLevelState.getEntities());
        this.stateRootNode.attachChild(this.currentLevelState.getWorldCursor());
        this.stateRootNode.addLight(this.currentLevelState.getSun());
    }

    @Override
    protected void voxelEnable(IGameContainer gameContainer) {
        gameContainer.input().setCameraMoveSpeed(10);
    }

    @Override
    protected void voxelDisable(IGameContainer gameContainer) {
    }
}
