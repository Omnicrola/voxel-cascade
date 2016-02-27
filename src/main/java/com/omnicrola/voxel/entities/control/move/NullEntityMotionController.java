package com.omnicrola.voxel.entities.control.move;

/**
 * Created by omnic on 2/6/2016.
 */
public class NullEntityMotionController extends EntityMotionControl {
    public static final EntityMotionControl NO_OP = new NullEntityMotionController();

    private NullEntityMotionController() {
        super(null);
    }

    @Override
    public void update(float tpf) {

    }
}
