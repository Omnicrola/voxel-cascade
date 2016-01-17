package com.omnicrola.voxel.entities.control;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.omnicrola.voxel.entities.EntityData;

/**
 * Created by omnic on 1/17/2016.
 */
public class EntityAiController extends AbstractVoxelControl {

    private Vector3f currentTargetLocation;

    public EntityAiController() {
    }

    @Override
    protected void voxelUpdate(float tpf, EntityData entityData) {
        if (currentTargetLocation != null) {
            MotionGovernorControl motionGovernor = entityData.getMotionGovernor();
            boolean hasArrived = motionGovernor.seekAndArriveAtRange(this.currentTargetLocation, 3, 0);
            if (hasArrived) {
                this.currentTargetLocation = null;
            }
        }
    }

    @Override
    protected void voxelRender(RenderManager rm, ViewPort vp) {
    }

    public void moveToLocation(Vector3f location) {
        this.currentTargetLocation = location;
    }

    public void stop() {
        this.currentTargetLocation = null;
    }
}
