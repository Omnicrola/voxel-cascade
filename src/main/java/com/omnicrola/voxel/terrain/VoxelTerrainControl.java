package com.omnicrola.voxel.terrain;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 * Created by omnic on 1/31/2016.
 */
public class VoxelTerrainControl extends AbstractControl   {
    private VoxelChunkHandler voxelChunkHandler;
    private Node parentNode;

    public VoxelTerrainControl(VoxelChunkHandler voxelChunkHandler) {
        this.voxelChunkHandler = voxelChunkHandler;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        this.parentNode = (Node) spatial;
    }

    @Override
    protected void controlUpdate(float tpf) {
        this.voxelChunkHandler.update(this.parentNode);
    }


    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }

}
