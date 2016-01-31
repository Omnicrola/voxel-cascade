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
import com.omnicrola.voxel.jme.wrappers.impl.JmePhysicsWrapper;

/**
 * Created by omnic on 1/31/2016.
 */
public class VoxelTerrainControl extends AbstractPhysicsControl {
    private VoxelChunkHandler voxelChunkHandler;
    private Node parentNode;
    private JmePhysicsWrapper physicsSpace;

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
        this.physicsSpace = new JmePhysicsWrapper(space);
    }

    @Override
    protected void removePhysics(PhysicsSpace space) {
        this.physicsSpace = null;
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        return null;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        this.parentNode = (Node) spatial;
    }

    @Override
    public void update(float tpf) {
        if (this.physicsSpace != null) {
            this.voxelChunkHandler.update(this.parentNode, this.physicsSpace);
        }
    }

    @Override
    public void render(RenderManager rm, ViewPort vp) {

    }

    public void forceRebuild() {
        System.out.println("rebuilding terrain");
        this.voxelChunkHandler.flagAllChunksAsDirty();
    }
}
