package com.omnicrola.voxel.engine.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.light.DirectionalLight;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.shadow.EdgeFilteringMode;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.engine.VoxelGameEngine;

/**
 * Created by Eric on 2/17/2016.
 */
public class ShadowState extends AbstractAppState {

    private LevelState currentLevelState;

    private class ShadowLevelStateObserver implements ILevelChangeObserver {
        @Override
        public void levelChanged(LevelState currentLevelState) {
            setCurrentState(currentLevelState);
        }
    }

    private VoxelGameEngine voxelGameEngine;
    private DirectionalLightShadowRenderer shadowRenderer;
    private FilterPostProcessor postProcessor;


    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        this.voxelGameEngine = (VoxelGameEngine) app;
        AssetManager assetManager = this.voxelGameEngine.getAssetManager();
        ViewPort viewPort = this.voxelGameEngine.getViewPort();
        DirectionalLight sun = this.voxelGameEngine.getLightManager().getSun();
        /* Drop shadows */
        final int SHADOWMAP_SIZE = 1024;
        this.shadowRenderer = new DirectionalLightShadowRenderer(assetManager, SHADOWMAP_SIZE, 3);
        this.shadowRenderer.setLight(sun);
        this.shadowRenderer.setEdgeFilteringMode(EdgeFilteringMode.PCF4);

        DirectionalLightShadowFilter shadowFilter = new DirectionalLightShadowFilter(assetManager, SHADOWMAP_SIZE, 3);
        shadowFilter.setLight(sun);
        shadowFilter.setEnabled(true);
        this.postProcessor = new FilterPostProcessor(assetManager);
        this.postProcessor.addFilter(shadowFilter);

        setEnabled(true);
        ILevelChangeObserver shadowObserver = new ShadowLevelStateObserver();
        this.voxelGameEngine.getStateManager().getState(WorldLevelState.class).addObserver(shadowObserver);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            enableShadows();
        } else {
            disableShadows();
        }
    }

    private void disableShadows() {
        ViewPort viewPort = voxelGameEngine.getViewPort();
        viewPort.removeProcessor(this.shadowRenderer);
        viewPort.removeProcessor(this.postProcessor);
        enableLevel();
    }

    private void enableShadows() {
        ViewPort viewPort = voxelGameEngine.getViewPort();
        viewPort.addProcessor(this.shadowRenderer);
        viewPort.addProcessor(this.postProcessor);
        disableLevel();
    }

    private void setCurrentState(LevelState currentLevelState) {
        this.currentLevelState = currentLevelState;
        if (isEnabled()) {
            enableLevel();
        } else {
            disableLevel();
        }
    }

    private void enableLevel() {
        if (this.currentLevelState != null) {
            this.currentLevelState.getUnitsNode().setShadowMode(RenderQueue.ShadowMode.Cast);
            this.currentLevelState.getTerrainNode().setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        }
    }

    private void disableLevel() {
        if (this.currentLevelState != null) {
            this.currentLevelState.getUnitsNode().setShadowMode(RenderQueue.ShadowMode.Off);
            this.currentLevelState.getTerrainNode().setShadowMode(RenderQueue.ShadowMode.Off);
        }
    }


}
