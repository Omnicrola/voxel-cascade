package com.omnicrola.voxel.server.main;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.omnicrola.voxel.data.units.EntityDefinitionXmlAssetLoader;
import com.omnicrola.voxel.engine.MasterCollisionHandler;
import com.omnicrola.voxel.server.main.init.ServerInitializer;
import com.omnicrola.voxel.settings.GameConstants;

/**
 * Created by Eric on 2/21/2016.
 */
public class VoxelServerEngine extends SimpleApplication {

    private BulletAppState bulletAppState;
    private ServerInitializer serverInitializer;

    public VoxelServerEngine(BulletAppState bulletAppState, ServerInitializer serverInitializer) {
        this.bulletAppState = bulletAppState;
        this.serverInitializer = serverInitializer;
    }

    @Override
    public void simpleInitApp() {
        this.stateManager.attach(this.bulletAppState);
        this.bulletAppState.getPhysicsSpace().addCollisionListener(new MasterCollisionHandler());

        this.assetManager.registerLoader(EntityDefinitionXmlAssetLoader.class, GameConstants.UNIT_DEFINITION_FILE_EXTENSION);
        this.serverInitializer.init(this);
    }
}
