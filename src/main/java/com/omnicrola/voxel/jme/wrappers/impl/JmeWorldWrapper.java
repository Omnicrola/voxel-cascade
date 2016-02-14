package com.omnicrola.voxel.jme.wrappers.impl;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingSphere;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.light.Light;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.input.ScreenRectangle;
import com.omnicrola.voxel.input.ScreenSelectionEvaluator;
import com.omnicrola.voxel.input.ScreenSelectionEvaluatorFactory;
import com.omnicrola.voxel.input.WorldCursor;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;
import com.omnicrola.voxel.jme.wrappers.IWorldBuilder;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.terrain.VoxelData;
import com.omnicrola.voxel.terrain.VoxelTerrainControl;
import com.omnicrola.voxel.util.VoxelUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by omnic on 1/15/2016.
 */
public class JmeWorldWrapper implements IGameWorld {
    private final WorldBuilder geometryBuilder;
    private final ScreenSelectionEvaluatorFactory screenSelectionEvaluatorFactory;
    private VoxelGameEngine game;
    private JmePhysicsWrapper physicsWrapper;
    private Node terrain;
    private Node units;

    public JmeWorldWrapper(VoxelGameEngine game, JmeApplicationWrapper jmeApplicationWrapper, JmePhysicsWrapper physicsWrapper) {
        this.game = game;
        this.physicsWrapper = physicsWrapper;
        AssetManager assetManager = game.getAssetManager();
        this.geometryBuilder = new WorldBuilder(assetManager, jmeApplicationWrapper, new ParticleBuilder(assetManager));
        this.screenSelectionEvaluatorFactory = new ScreenSelectionEvaluatorFactory(game.getCamera());
    }

    @Override
    public void attach(Spatial spatial) {
        this.game.getRootNode().attachChild(spatial);
        addChildrenToPhysicsSpace(spatial);
    }

    @Override
    public void detatch(Spatial spatial) {
        this.game.getRootNode().detachChild(spatial);
        removeChildrenFromPhysicsSpace(spatial);
    }

    @Override
    public boolean isBelowTerrain(Geometry geometry) {
        Vector3f worldLocation = geometry.getWorldTranslation();
        Vec3i location = Vec3i.floor(worldLocation.add(0, 0.5f, 0));
        VoxelTerrainControl control = this.terrain.getControl(VoxelTerrainControl.class);
        return control.isVoxelSolidAt(location);
    }

    @Override
    public Vector3f getSpawnPointFor(Vector3f location) {
        return this.terrain.getControl(VoxelTerrainControl.class).findLowestNonSolidVoxel(location);
    }

    private void addChildrenToPhysicsSpace(Spatial spatial) {
        this.physicsWrapper.add(spatial);
    }

    @Override
    public void remove(Spatial spatial) {
        this.game.getRootNode().detachChild(spatial);
        removeChildrenFromPhysicsSpace(spatial);
    }

    private void removeChildrenFromPhysicsSpace(Spatial spatial) {
        this.physicsWrapper.remove(spatial);
    }

    @Override
    public IWorldBuilder build() {
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
        return VoxelUtil.convertToStream(collisionResults);
    }

    @Override
    public List<Spatial> selectAllUnitsIn(ScreenRectangle screenRectangle) {
        ScreenSelectionEvaluator screenSelectionEvaluator = this.screenSelectionEvaluatorFactory.build(screenRectangle);
        List<Spatial> children = this.units.getChildren();
        List<Spatial> collect = children
                .stream()
                .filter(s -> isSelectableUnit(s))
                .filter(s -> screenSelectionEvaluator.isInSelection(s.getWorldTranslation()))
                .collect(Collectors.toList());
        return collect;
    }

    private boolean isSelectableUnit(Spatial s) {
        Boolean isUnit = s.getUserData(EntityDataKeys.IS_UNIT);
        Boolean isSelectable = s.getUserData(EntityDataKeys.IS_SELECTABLE);
        return isSelectable != null && isSelectable.booleanValue() &&
                isUnit != null && isUnit.booleanValue();
    }

    @Override
    public WorldCursor createCursor(Node terrain) {
        JmeInputWrapper inputManager = new JmeInputWrapper(this.game);
        WorldCursor worldCursor = new WorldCursor(inputManager, this.game.getCamera(), terrain);
        return worldCursor;
    }

    @Override
    public void attachLights(List<Light> lights) {
        for (Light light : lights) {
            this.game.getRootNode().addLight(light);
        }
    }

    @Override
    public void attachTerrain(Node terrain) {
        this.terrain = terrain;
        attach(terrain);
    }

    @Override
    public void attachUnits(Node units) {
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

    @Override
    public VoxelData getVoxelAt(Vector3f location) {
        VoxelTerrainControl terrainControl = this.terrain.getControl(VoxelTerrainControl.class);
        return terrainControl.getVoxelAt(Vec3i.round(location));
    }
}
