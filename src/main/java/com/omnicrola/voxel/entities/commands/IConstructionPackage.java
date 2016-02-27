package com.omnicrola.voxel.entities.commands;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;

/**
 * Created by omnic on 2/11/2016.
 */
public interface IConstructionPackage {

    Vector3f getLocation();

    boolean isFinished();

    float applyResourceTic(float tpf);

    void completeConstruction();
}
