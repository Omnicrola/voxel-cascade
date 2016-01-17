package com.omnicrola.voxel.engine.entities.commands;

import com.jme3.scene.Spatial;

/**
 * Created by omnic on 1/16/2016.
 */
public interface IEntityCommand {
    void evaluate(Spatial parentEntity, float tpf);

    boolean isFinished();

    int priority();
}
