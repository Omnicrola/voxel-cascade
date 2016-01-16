package com.omnicrola.voxel.engine;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapFont;
import com.jme3.renderer.RenderManager;
import com.omnicrola.voxel.main.init.VoxelGameEngineInitializer;

/**
 * Created by omnic on 1/15/2016.
 */
public class VoxelGameEngine extends SimpleApplication {
    @Override
    public void simpleInitApp() {
        VoxelGameEngineInitializer.initializeGame(this.stateManager);
    }

    @Override
    public void simpleRender(RenderManager rm) {

    }

    @Override
    public void simpleUpdate(float tpf) {

    }

    public BitmapFont getGuiFont() {
        return guiFont;
    }
}
