package com.omnicrola.voxel.engine.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.renderer.Camera;
import com.omnicrola.util.Tuple;
import com.omnicrola.voxel.data.level.LevelData;
import com.omnicrola.voxel.data.level.LevelPlayInitializer;
import com.omnicrola.voxel.engine.CameraDolly;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.input.GameInputAction;
import com.omnicrola.voxel.input.IWorldCursor;
import com.omnicrola.voxel.input.listeners.ClearSelectionListener;
import com.omnicrola.voxel.input.listeners.ExecutePrimaryCursorListener;
import com.omnicrola.voxel.input.listeners.ExecuteSecondaryCursorListener;
import com.omnicrola.voxel.input.listeners.PanCameraListener;
import com.omnicrola.voxel.ui.IUiManager;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.world.IWorldNode;

import java.util.ArrayList;

/**
 * Created by omnic on 1/15/2016.
 */
public class ActivePlayState extends AbstractAppState {
    private final ArrayList<Tuple<GameInputAction, ActionListener>> inputs;
    private InputManager inputManager;
    private IWorldCursor worldCursor;
    private IUiManager uiManager;
    private LevelPlayInitializer levelPlayInitializer;
    private Camera camera;
    private IWorldNode worldNode;

    public ActivePlayState(IWorldCursor worldCursor,
                           IUiManager uiManager,
                           LevelPlayInitializer levelPlayInitializer) {
        this.worldCursor = worldCursor;
        this.uiManager = uiManager;
        this.levelPlayInitializer = levelPlayInitializer;
        this.inputs = new ArrayList<>();
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        VoxelGameEngine voxelGameEngine = (VoxelGameEngine) app;
        this.inputManager = voxelGameEngine.getInputManager();

        voxelGameEngine.getFlyByCamera().setMoveSpeed(10f);
        this.camera = voxelGameEngine.getCamera();

        attachWorldCursor(voxelGameEngine);
        this.uiManager.attach(voxelGameEngine.getGuiNode());

        initializeKeybindings(voxelGameEngine);
        this.worldNode = voxelGameEngine.getWorldNode();
        setEnabled(false);
    }

    private void attachWorldCursor(VoxelGameEngine voxelGameEngine) {
        this.worldCursor.attachTo(voxelGameEngine.getWorldNode().getFxNode());
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
            this.uiManager.changeScreen(UiScreen.ACTIVE_PLAY);
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
    }

    public IWorldCursor getWorldCursor() {
        return worldCursor;
    }

    public void setLevelData(LevelData levelData) {
        this.levelPlayInitializer.activate(levelData, this.worldCursor, this.camera);
    }
}
