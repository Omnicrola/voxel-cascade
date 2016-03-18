package com.omnicrola.voxel.engine.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.scene.Node;
import com.omnicrola.util.Tuple;
import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.engine.CameraDolly;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.input.GameInputAction;
import com.omnicrola.voxel.input.IWorldCursor;
import com.omnicrola.voxel.input.listeners.*;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.terrain.TerrainManager;
import com.omnicrola.voxel.ui.IUiManager;

import java.util.ArrayList;

/**
 * Created by omnic on 1/15/2016.
 */
public class ActivePlayState extends AbstractAppState {
    private final ArrayList<Tuple<GameInputAction, ActionListener>> inputs;
    private LevelManager levelManager;
    private InputManager inputManager;
    private TerrainManager terrainManager;
    private IWorldCursor worldCursor;
    private IUiManager uiManager;

    public ActivePlayState(LevelManager levelManager,
                           TerrainManager terrainManager,
                           IWorldCursor worldCursor,
                           IUiManager uiManager) {
        this.levelManager = levelManager;
        this.terrainManager = terrainManager;
        this.worldCursor = worldCursor;
        this.uiManager = uiManager;
        this.inputs = new ArrayList<>();
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        VoxelGameEngine voxelGameEngine = (VoxelGameEngine) app;
        this.inputManager = voxelGameEngine.getInputManager();

        voxelGameEngine.getFlyByCamera().setMoveSpeed(10f);
        attachWorldCursor(voxelGameEngine);
        initializeKeybindings(voxelGameEngine);
        setEnabled(false);
    }

    private void attachWorldCursor(VoxelGameEngine voxelGameEngine) {
        Node fxNode = voxelGameEngine.getWorldNode().getFxNode();
        this.worldCursor.attachTo(fxNode);
        this.uiManager.attach(voxelGameEngine.getGuiNode());
    }

    private void initializeKeybindings(VoxelGameEngine voxelGameEngine) {

        addStateInput(GameInputAction.CLEAR_SELECTION, new ClearSelectionListener(this.worldCursor));

        ExecutePrimaryCursorListener primaryCursorListener = new ExecutePrimaryCursorListener(this.worldCursor);
        addStateInput(GameInputAction.MULTI_SELECT, primaryCursorListener);
        addStateInput(GameInputAction.MOUSE_PRIMARY, primaryCursorListener);
        addStateInput(GameInputAction.MOUSE_SECONDARY, new ExecuteSecondaryCursorListener(this.worldCursor));

        CameraDolly cameraDolly = new CameraDolly(voxelGameEngine.getCamera());
        PanCameraListener panCameraListener = new PanCameraListener(cameraDolly);
        panCameraListener.registerInputs(this);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            this.inputs.forEach(t -> addListener(t));
            this.uiManager.bindInput();
        } else {
            this.inputs.forEach(t -> removeListener(t));
            this.uiManager.unbindInput();
        }
    }

    private void removeListener(Tuple<GameInputAction, ActionListener> listenerTuple) {
        this.inputManager.removeListener(listenerTuple.getRight());
    }

    private void addListener(Tuple<GameInputAction, ActionListener> listenerTuple) {
        this.inputManager.addListener(listenerTuple.getRight(), listenerTuple.getLeft().toString());
    }

    public void addStateInput(GameInputAction gameInputAction, ActionListener actionListener) {
        this.inputs.add(new Tuple<>(gameInputAction, actionListener));
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        updateCurrentLevel(tpf);
        this.terrainManager.update(tpf);
    }

    private void updateCurrentLevel(float tpf) {
        LevelState currentLevel = this.levelManager.getCurrentLevel();
        if (currentLevel != null) {
            currentLevel.addTime(tpf);
        }
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public ITerrainManager getTerrainManager() {
        return terrainManager;
    }

    public IWorldCursor getWorldCursor() {
        return worldCursor;
    }
}
