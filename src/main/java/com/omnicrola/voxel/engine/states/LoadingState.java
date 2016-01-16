package com.omnicrola.voxel.engine.states;

import com.jme3.math.ColorRGBA;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.ui.GLabel;

/**
 * Created by omnic on 1/15/2016.
 */
public class LoadingState extends VoxelGameState {

    private static final float ONE_SECOND = 1.0f;
    private GLabel loadingText;
    private IGameContainer gameContainer;
    private float timeElapsed;

    @Override
    protected void voxelInitialize(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;

        this.loadingText = this.gameContainer
                .gui()
                .build()
                .label("Loading...", ColorRGBA.White);
        loadingText.setTextPosition(300, 300);
        setEnabled(true);
    }

    @Override
    protected void voxelEnable(IGameContainer gameContainer) {
        this.timeElapsed = 0;
        this.gameContainer
                .gui()
                .setMouseGrabbed(false);
        this.gameContainer
                .gui()
                .attach(this.loadingText);
    }

    @Override
    protected void voxelDisable(IGameContainer gameContainer) {
        this.gameContainer
                .gui()
                .remove(this.loadingText);
    }

    @Override
    public void update(float tpf) {
        this.timeElapsed += tpf;
        if (timeElapsed > ONE_SECOND) {
            this.gameContainer.enableState(MainMenuState.class);
            setEnabled(false);
        }
    }
}
