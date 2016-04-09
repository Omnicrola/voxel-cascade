package com.omnicrola.voxel.entities.control.resources;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.data.TeamId;
import com.omnicrola.voxel.entities.control.EntityControlAdapter;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.terrain.data.VoxelData;
import com.omnicrola.voxel.terrain.data.VoxelType;

import java.util.Optional;

/**
 * Created by omnic on 3/13/2016.
 */
public class PassiveHarvestControl extends AbstractControl {
    private final float speed;
    private final float range;
    private final EntityControlAdapter entityControlAdapter;

    public PassiveHarvestControl(float speed, float range, EntityControlAdapter entityControlAdapter) {
        this.speed = speed;
        this.range = range;
        this.entityControlAdapter = entityControlAdapter;
    }

    @Override
    protected void controlUpdate(float tpf) {
        ITerrainManager terrainManager = this.entityControlAdapter.getTerrainManager();
        Vector3f ourLocation = this.spatial.getWorldTranslation();
        Optional<VoxelData> highestSolidVoxel = terrainManager.getHighestSolidVoxel(ourLocation);
        if (highestSolidVoxel.isPresent()) {
            VoxelData voxelData = highestSolidVoxel.get();
            float distance = voxelData.getLocation().distance(ourLocation);
            if (distance <= this.range) {
                harvest(voxelData, tpf);
            }
        }
    }

    private void harvest(VoxelData voxelData, float tpf) {
        TeamId teamId = this.spatial.getUserData(EntityDataKeys.TEAM_DATA);
        float resourceTic = tpf * this.speed;
        float resourcesAvailable = voxelData.getResources();
        if (resourcesAvailable > resourceTic) {
            voxelData.setResources(resourcesAvailable - resourceTic);
        } else {
            voxelData.setResources(0f);
            voxelData.setType(VoxelType.EMPTY);
            resourceTic = resourcesAvailable;
        }
        this.entityControlAdapter.getCurrentLevel().addResouces(teamId, resourceTic);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
