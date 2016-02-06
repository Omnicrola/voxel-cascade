package com.omnicrola.voxel.terrain;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.AbstractPhysicsControl;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import com.omnicrola.util.Vec3i;

/**
 * Created by omnic on 1/31/2016.
 */
public class VoxelTerrainControl extends AbstractPhysicsControl {
    private VoxelChunkHandler voxelChunkHandler;

    public VoxelTerrainControl(VoxelChunkHandler voxelChunkHandler) {
        this.voxelChunkHandler = voxelChunkHandler;
    }

    @Override
    protected void createSpatialData(Spatial spat) {

    }

    @Override
    protected void removeSpatialData(Spatial spat) {

    }

    @Override
    protected void setPhysicsLocation(Vector3f vec) {

    }

    @Override
    protected void setPhysicsRotation(Quaternion quat) {

    }

    @Override
    protected void addPhysics(PhysicsSpace space) {

    }

    @Override
    protected void removePhysics(PhysicsSpace space) {

    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        this.voxelChunkHandler.setParentNode((Node) spatial);
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        return null;
    }

    @Override
    public void update(float tpf) {
        this.voxelChunkHandler.update();
    }

    @Override
    public void render(RenderManager rm, ViewPort vp) {

    }

    public void forceRebuild() {
        this.voxelChunkHandler.flagAllChunksForRebuild();
    }

    public Vector3f getSpawnPointFor(Vector3f location) {
        return this.voxelChunkHandler.adjustLocationForHeight(location);
    }

    public boolean isVoxelSolidAt(Vector3f location) {
        return this.voxelChunkHandler.isVoxelSolidAt(location);
    }

    public void setVoxel(byte voxel, Vec3i location) {
        this.voxelChunkHandler.set(location, voxel);
    }
}
