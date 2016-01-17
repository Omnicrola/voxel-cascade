package com.omnicrola.voxel.engine.entities.commands;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.engine.entities.control.MotionGovernorControl;

/**
 * Created by omnic on 1/16/2016.
 */
public class MoveToLocationCommand implements IEntityCommand {
    private Vector3f targetPostion;
    private boolean isFinished;

    public MoveToLocationCommand(Vector3f targetPostion) {
        this.targetPostion = targetPostion;
        this.isFinished = false;
    }

    @Override
    public void evaluate(Spatial parentEntity, float tpf) {
        MotionGovernorControl motionGovernor = parentEntity.getControl(MotionGovernorControl.class);
        this.isFinished = motionGovernor.seekAndArrive(this.targetPostion, 1.5f);
    }

    @Override
    public boolean isFinished() {
        return this.isFinished;
    }

    @Override
    public int priority() {
        return 0;
    }
}
