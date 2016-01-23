package com.omnicrola.voxel.engine;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.font.BitmapFont;
import com.jme3.input.controls.ActionListener;
import com.jme3.renderer.RenderManager;
import com.omnicrola.voxel.data.units.EntityDefinitionXmlAssetLoader;
import com.omnicrola.voxel.input.GameInputAction;
import com.omnicrola.voxel.main.init.VoxelGameEngineInitializer;
import com.omnicrola.voxel.settings.GameConstants;

/**
 * Created by omnic on 1/15/2016.
 */
public class VoxelGameEngine extends SimpleApplication {

    private BulletAppState bulletAppState;

    public VoxelGameEngine(BulletAppState bulletAppState) {
        this.bulletAppState = bulletAppState;
    }

    @Override
    public void simpleInitApp() {
        this.stateManager.attach(this.bulletAppState);
        this.assetManager.registerLoader(EntityDefinitionXmlAssetLoader.class, GameConstants.UNIT_DEFINITION_FILE_EXTENSION);
        VoxelGameEngineInitializer.initializeGame(this.stateManager);
        this.bulletAppState.getPhysicsSpace().addCollisionListener(new MasterCollisionHandler());
        this.inputManager.addListener(new ActionListener() {
            @Override
            public void onAction(String name, boolean isPressed, float tpf) {
                if (!isPressed) {
                    bulletAppState.setDebugEnabled(!bulletAppState.isDebugEnabled());
                }
            }
        }, GameInputAction.TOGGLE_PHYSICS_DEBUG.trigger());
//        bulletAppState.setDebugEnabled(true);
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

    public PhysicsSpace getPhysicsSpace() {
        return this.bulletAppState.getPhysicsSpace();
    }
}
