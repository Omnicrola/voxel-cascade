package com.omnicrola.voxel.jme.wrappers;

import com.jme3.collision.CollisionResult;
import com.jme3.light.Light;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.input.ScreenRectangle;
import com.omnicrola.voxel.input.WorldCursor;
import com.omnicrola.voxel.terrain.data.VoxelData;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by omnic on 1/15/2016.
 */
public interface IGameWorld {

    void attach(Spatial node);

    void remove(Spatial node);

    IWorldBuilder build();

    WorldCursor createCursor(Node terrain);

    void addLight(Light light);

    Stream<CollisionResult> getUnitsInRange(Vector3f position, float radius);

    void attachTerrain(Node terrain);

    void attachUnits(Node units);

    void attachLights(List<Light> lights);

    void detatchTerrain(Spatial terrain);

    void detatchUnits(Spatial units);

    void detatchLights(List<Light> lights);

    List<Spatial> selectAllUnitsIn(ScreenRectangle screenRectangle);

    void detatch(Spatial spatial);

    boolean isBelowTerrain(Geometry spatial);

    Vector3f getSpawnPointFor(Vector3f location);

    VoxelData getVoxelAt(Vector3f contactPoint);

    ILightManager lights();
}
