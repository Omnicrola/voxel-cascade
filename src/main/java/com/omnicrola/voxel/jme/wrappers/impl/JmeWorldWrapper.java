package com.omnicrola.voxel.jme.wrappers.impl;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.PhysicsControl;
import com.jme3.light.Light;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.input.WorldCursor;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;
import com.omnicrola.voxel.jme.wrappers.IEntityBuilder;

/**
 * Created by omnic on 1/15/2016.
 */
public class JmeWorldWrapper implements IGameWorld {
    private final EntityBuilder geometryBuilder;
    private final PhysicsSpace physicsSpace;
    private VoxelGameEngine game;

    public JmeWorldWrapper(VoxelGameEngine game) {
        this.game = game;
        this.physicsSpace = game.getPhysicsSpace();
        this.geometryBuilder = new EntityBuilder(game.getAssetManager(), this);
    }

    @Override
    public void attach(Spatial spatial) {
        this.game.getRootNode().attachChild(spatial);
        addChildrenToPhysicsSpace(spatial);
    }

    private void addChildrenToPhysicsSpace(Spatial spatial) {
        PhysicsControl control = spatial.getControl(PhysicsControl.class);
        if (control != null) {
            this.physicsSpace.add(spatial);
        }
        if (spatial instanceof Node) {
            ((Node) spatial)
                    .getChildren()
                    .stream()
                    .forEach(child -> addChildrenToPhysicsSpace(child));
        }
    }

    @Override
    public void remove(Spatial spatial) {
        this.game.getRootNode().detachChild(spatial);
        removeChildrenFromPhysicsSpace(spatial);
    }

    private void removeChildrenFromPhysicsSpace(Spatial spatial) {
        PhysicsControl control = spatial.getControl(PhysicsControl.class);
        if (control != null) {
            this.physicsSpace.remove(spatial);
        }
        if (spatial instanceof Node) {
            ((Node) spatial)
                    .getChildren()
                    .stream()
                    .forEach(child -> removeChildrenFromPhysicsSpace(child));
        }
    }

    @Override
    public IEntityBuilder build() {
        return this.geometryBuilder;
    }

    @Override
    public void addLight(Light light) {
        this.game.getRootNode().addLight(light);
    }

    @Override
    public WorldCursor createCursor(Node terrain) {
        return new WorldCursor(this.game.getInputManager(), this.game.getCamera(), terrain);
    }
}
