package com.omnicrola.voxel.entities.control.old;

/**
 * Created by omnic on 2/6/2016.
 */
public class NullMotionController extends MotionGovernorControl {
    public static final MotionGovernorControl NO_OP = new NullMotionController();

    private NullMotionController() {
        super(null);
    }

    @Override
    public void update(float tpf) {

    }
}
