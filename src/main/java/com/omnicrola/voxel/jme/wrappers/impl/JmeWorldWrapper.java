package com.omnicrola.voxel.jme.wrappers.impl;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingSphere;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.PhysicsControl;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.light.Light;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.input.WorldCursor;
import com.omnicrola.voxel.jme.wrappers.IEntityBuilder;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by omnic on 1/15/2016.
 */
public class JmeWorldWrapper implements IGameWorld {
    private final EntityBuilder geometryBuilder;
    private final PhysicsSpace physicsSpace;
    private VoxelGameEngine game;
    private Spatial terrain;
    private Spatial units;

    public JmeWorldWrapper(VoxelGameEngine game) {
        this.game = game;
        this.physicsSpace = game.getPhysicsSpace();
        AssetManager assetManager = game.getAssetManager();
        this.geometryBuilder = new EntityBuilder(assetManager, this, new ParticleBuilder(assetManager));
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
    public Stream<CollisionResult> getUnitsInRange(Vector3f position, float radius) {
        CollisionResults collisionResults = new CollisionResults();
        this.units.collideWith(new BoundingSphere(radius, position), collisionResults);
        Iterable<CollisionResult> iterable = () -> collisionResults.iterator();
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    @Override
    public WorldCursor createCursor(Node terrain) {
        return new WorldCursor(this.game.getInputManager(), this.game.getCamera(), terrain);
    }

    @Override
    public void attachLights(List<Light> lights) {
        for (Light light : lights) {
            this.game.getRootNode().addLight(light);
        }
    }

    @Override
    public void attachTerrain(Spatial terrain) {
        this.terrain = terrain;
        attach(terrain);
    }

    @Override
    public void attachUnits(Spatial units) {
        this.units = units;
        attach(units);
    }

    @Override
    public void detatchLights(List<Light> lights) {
        for (Light light : lights) {
            this.game.getRootNode().removeLight(light);
        }
    }

    @Override
    public void detatchTerrain(Spatial terrain) {
        this.terrain = null;
        remove(terrain);
    }

    @Override
    public void detatchUnits(Spatial units) {
        this.units = null;
        remove(units);
    }
}
