package com.omnicrola.voxel.jme.wrappers;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.TeamData;

/**
 * Created by omnic on 1/16/2016.
 */
public interface IEntityBuilder {
    Geometry terrainVoxel(float size, ColorRGBA color);

    public Spatial projectile(Spatial emittingEntity, int projectileId, Vector3f attackVector);

    Spatial unit(int definitionId, TeamData teamData);
}
