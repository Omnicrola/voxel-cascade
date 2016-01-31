package com.omnicrola.voxel.engine.states;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResult;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.omnicrola.voxel.data.GameXmlDataParser;
import com.omnicrola.voxel.data.level.*;
import com.omnicrola.voxel.input.GameInputAction;
import com.omnicrola.voxel.input.listeners.*;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.jme.wrappers.impl.JmeApplicationWrapper;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.terrain.VoxelTerrainControl;
import com.omnicrola.voxel.terrain.VoxelTerrainGenerator;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.builders.ActivePlayUiBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by omnic on 1/15/2016.
 */
public class ActivePlayState extends VoxelGameState implements ICurrentLevelProvider {

    private class DebugReloadListener implements ActionListener {

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (!isPressed) {
                LevelDefinition basicLevelDefinition = LevelGeneratorTool.createBasicLevelDefinition();
                loadLevel(basicLevelDefinition);
            }
        }
    }

    private class DebugSceneGraphListener implements ActionListener {

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (!isPressed) {
                ((JmeApplicationWrapper) gameContainer).debugSceneGraph();
            }
        }
    }

    private class MouseLookListener implements ActionListener {

        private boolean isLooking;

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (!isPressed) {
                this.isLooking = !this.isLooking;
                currentLevelState.getWorldCursor().setVisible(!this.isLooking);
                gameContainer.input().setMouseGrabbed(this.isLooking);
            }
        }
    }

    private class DebugTargetListener implements ActionListener {

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (!isPressed) {
                Optional<CollisionResult> terrainUnderCursor = currentLevelState.getWorldCursor().getTerrainUnderCursor(currentLevelState.getTerrain());
                if (terrainUnderCursor.isPresent()) {
                    Geometry geometry = terrainUnderCursor.get().getGeometry();
                    RigidBodyControl control = geometry.getControl(RigidBodyControl.class);
                    Vector3f physicsLocation = control.getPhysicsLocation();
                    System.out.println("world: " + geometry.getWorldTranslation() + " physics: " + physicsLocation);
                }
            }
        }
    }

    private class DebugRebuildTerrainListener implements ActionListener {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (!isPressed) {
                VoxelTerrainControl control = currentLevelState.getTerrain().getControl(VoxelTerrainControl.class);
                control.forceRebuild();
            }
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
        LevelDefinition levelDefinition = levelDefinitions.getLevel(levelId);
        loadLevel(levelDefinition);
    }

    private void loadLevel(LevelDefinition newLevelDefinition) {
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
        addStateInput(GameInputAction.DEBUG_TOGGLE_MOUSE_LOOK, new MouseLookListener());
        addStateInput(GameInputAction.DEBUG_RELOAD_LEVEL, new DebugReloadListener());
        addStateInput(GameInputAction.DEBUG_SCENE_GRAPH, new DebugSceneGraphListener());
        addStateInput(GameInputAction.DEBUG_TARGET_OBJECT, new DebugTargetListener());
        addStateInput(GameInputAction.DEBUG_REBUILD_TERRAIN, new DebugRebuildTerrainListener());

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
