package com.omnicrola.voxel.entities.control.old;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.entities.commands.IConstructionPackage;

/**
 * Created by Eric on 2/12/2016.
 */
public class NullConstructionPackage implements IConstructionPackage {
    public static final NullConstructionPackage NO_OP = new NullConstructionPackage();

    private NullConstructionPackage() {
    }

    @Override
    public Vector3f getLocation() {
        return new Vector3f();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public float applyResourceTic(float tpf) {
        return 0;
    }

    @Override
    public void completeConstruction() {

    }
}
