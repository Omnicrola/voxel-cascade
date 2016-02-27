package com.omnicrola.voxel.entities;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.util.VoxelUtil;
import com.omnicrola.voxel.world.IGameEntity;

/**
 * Created by Eric on 2/26/2016.
 */
public class AbstractGameEntity implements IGameEntity{
    protected Spatial spatial;

    public AbstractGameEntity(Spatial spatial) {
        this.spatial = spatial;
    }

    @Override
    public Spatial getSpatial() {
        return this.spatial;
    }

    @Override
    public TeamData getTeam() {
        return this.spatial.getUserData(EntityDataKeys.TEAM_DATA);
    }

    @Override
    public boolean isAlive() {
        return VoxelUtil.isAlive(this.spatial);
    }

    public void setLocation(Vector3f location) {
        this.spatial.setLocalTranslation(location);
    }
}
