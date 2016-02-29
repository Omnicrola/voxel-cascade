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
public abstract class AbstractGameEntity implements IGameEntity {
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

    @Override
    public boolean matches(Spatial spatial) {
        return spatial == this.spatial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractGameEntity)) return false;

        AbstractGameEntity that = (AbstractGameEntity) o;

        return !(spatial != null ? !spatial.equals(that.spatial) : that.spatial != null);

    }

    @Override
    public int hashCode() {
        return spatial != null ? spatial.hashCode() : 0;
    }

    public void setLocation(Vector3f location) {
        this.spatial.setLocalTranslation(location);
    }
}
