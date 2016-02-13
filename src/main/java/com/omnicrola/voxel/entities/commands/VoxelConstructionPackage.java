package com.omnicrola.voxel.entities.commands;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.terrain.IVoxelType;

/**
 * Created by omnic on 2/11/2016.
 */
public class VoxelConstructionPackage implements IConstructionPackage {

    public static final float BASE_BUILD_RATE = 1f;
    private float resourcesRequired;
    private IVoxelType voxelType;
    private Vec3i location;
    private Geometry ghostVoxel;

    public VoxelConstructionPackage(IVoxelType voxelType, Vec3i location, Geometry ghostVoxel) {
        this.voxelType = voxelType;
        this.location = location;
        this.ghostVoxel = ghostVoxel;
        this.resourcesRequired = 2f;
    }

    @Override
    public Vector3f getLocation() {
        return this.location.asVector3f();
    }

    @Override
    public boolean isFinished() {
        return this.resourcesRequired <= 0f;
    }

    @Override
    public float applyResourceTic(float tpf) {
        float resourcesUsed = BASE_BUILD_RATE * tpf;
        if (this.resourcesRequired > resourcesUsed) {
            this.resourcesRequired -= resourcesUsed;
        } else {
            resourcesUsed = this.resourcesRequired;
            this.resourcesRequired = 0;
        }
        return resourcesUsed;
    }

    @Override
    public void completeConstruction(IGameContainer gameContainer, LevelState levelState) {
        Node parent = this.ghostVoxel.getParent();
        if (parent != null) {
            parent.detachChild(this.ghostVoxel);
        }
        levelState.setVoxel(this.voxelType, this.location);
    }
}
