package com.omnicrola.voxel.engine.states;

import com.omnicrola.voxel.input.GameInputAction;
import com.omnicrola.voxel.input.listeners.ClearSelectionListener;
import com.omnicrola.voxel.input.listeners.ExecutePrimaryCursorListener;
import com.omnicrola.voxel.input.listeners.ExecuteSecondaryCursorListener;
import com.omnicrola.voxel.input.listeners.PanCameraListener;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.ui.UiScreen;

/**
 * Created by omnic on 1/15/2016.
 */
public class ActivePlayInputState extends VoxelGameState {

    private IGameContainer gameContainer;

    @Override
    protected void voxelInitialize(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;
        initializeKeybindings(gameContainer);
        setEnabled(false);
    }

    private void initializeKeybindings(IGameContainer gameContainer) {

        WorldLevelState currentLevelState = gameContainer.getState(WorldLevelState.class);
        addStateInput(GameInputAction.CLEAR_SELECTION, new ClearSelectionListener(currentLevelState));

        ExecutePrimaryCursorListener primaryCursorListener = new ExecutePrimaryCursorListener(currentLevelState);
        addStateInput(GameInputAction.MULTI_SELECT, primaryCursorListener);
        addStateInput(GameInputAction.MOUSE_PRIMARY, primaryCursorListener);
        addStateInput(GameInputAction.MOUSE_SECONDARY, new ExecuteSecondaryCursorListener(currentLevelState));

        PanCameraListener panCameraListener = new PanCameraListener(gameContainer.gui(), gameContainer.input());
        panCameraListener.registerInputs(this);
    }

    @Override
    protected void voxelEnable(IGameContainer gameContainer) {
        gameContainer.input().setCameraMoveSpeed(10);
        gameContainer.gui().changeScreens(UiScreen.ACTIVE_PLAY);
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        this.gameContainer.getState(WorldLevelState.class).getCurrentLevel().addTime(tpf);
    }

    @Override
    protected void voxelDisable(IGameContainer gameContainer) {
    }

}
