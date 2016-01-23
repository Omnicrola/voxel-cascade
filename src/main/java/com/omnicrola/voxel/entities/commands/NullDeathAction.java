package com.omnicrola.voxel.entities.commands;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.engine.IDeathAction;

/**
 * Created by omnic on 1/22/2016.
 */
public class NullDeathAction implements IDeathAction{

    public static final IDeathAction NULL = new NullDeathAction();

    private NullDeathAction(){}

    @Override
    public void destruct(Spatial parentSpatial) {

    }
}
