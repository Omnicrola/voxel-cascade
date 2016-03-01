package com.omnicrola.voxel.main.init.states;

import com.jme3.app.state.AppState;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.engine.states.ActivePlayState;
import com.omnicrola.voxel.input.IWorldCursor;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.terrain.TerrainManager;
import com.omnicrola.voxel.ui.UiSelectionRectangle;

/**
 * Created by omnic on 2/28/2016.
 */
public class ActivePlayInputStateInitializer implements IStateInitializer {
    @Override
    public AppState buildState(InitializationContainer initializationContainer) {
        LevelManager levelManager = initializationContainer.getLevelManager();
        TerrainManager terrainManager = initializationContainer.getTerrainManager();
        IWorldCursor worldCursor = initializationContainer.getWorldManager().getWorldCursor();
        UiSelectionRectangle uiSelectionRectangle = buildSelectionRectangle(initializationContainer);
        return new ActivePlayState(levelManager, terrainManager, worldCursor, uiSelectionRectangle);
    }

    private UiSelectionRectangle buildSelectionRectangle(InitializationContainer initializationContainer) {
        Material material = getMaterial(initializationContainer.getAssetManager());
        Quad quad = new Quad(1, 1);
        Geometry geometry = new Geometry("selection quad", quad);
        geometry.setMaterial(material);

        UiSelectionRectangle uiSelectionRectangle = new UiSelectionRectangle(quad, geometry);
        return uiSelectionRectangle;
    }

    private Material getMaterial(AssetManager assetManager) {
        ColorRGBA green = ColorRGBA.Green;
        Material material = new Material(assetManager, GameConstants.MATERIAL_UNSHADED);
        material.setColor("Color", green);
        return material;
    }

}
