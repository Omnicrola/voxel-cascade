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
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.world.IWorldNode;

/**
 * Created by Eric on 2/17/2016.
 */
public class ShadowState extends AbstractAppState {

    public static final int SHADOWMAP_SIZE = 1024;

    private VoxelGameEngine voxelGameEngine;
    private DirectionalLightShadowRenderer shadowRenderer;
    private FilterPostProcessor postProcessor;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        this.voxelGameEngine = (VoxelGameEngine) app;
        AssetManager assetManager = this.voxelGameEngine.getAssetManager();
        createShadowRenderer(assetManager);
        createPostProcessor(assetManager);
        enableShadows();
        setEnabled(true);
    }

    private void createPostProcessor(AssetManager assetManager) {
        DirectionalLightShadowFilter shadowFilter = new DirectionalLightShadowFilter(assetManager, SHADOWMAP_SIZE, 3);
        DirectionalLight sun = this.voxelGameEngine.getLightManager().getSun();
        shadowFilter.setLight(sun);
        shadowFilter.setEnabled(true);
        this.postProcessor = new FilterPostProcessor(assetManager);
        this.postProcessor.addFilter(shadowFilter);
    }

    private void createShadowRenderer(AssetManager assetManager) {
        DirectionalLight sun = this.voxelGameEngine.getLightManager().getSun();
        /* Drop shadows */
        this.shadowRenderer = new DirectionalLightShadowRenderer(assetManager, SHADOWMAP_SIZE, 3);
        this.shadowRenderer.setLight(sun);
        this.shadowRenderer.setEdgeFilteringMode(EdgeFilteringMode.PCF4);
    }

    private void enableShadows() {
        ViewPort viewPort = voxelGameEngine.getViewPort();
        viewPort.addProcessor(this.shadowRenderer);
        viewPort.addProcessor(this.postProcessor);
        IWorldNode worldNode = this.voxelGameEngine.getWorldNode();
        worldNode.getUnitsNode().setShadowMode(RenderQueue.ShadowMode.Cast);
        worldNode.getTerrainNode().setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
    }
}
