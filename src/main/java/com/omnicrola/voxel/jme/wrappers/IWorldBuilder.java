package com.omnicrola.voxel.jme.wrappers;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.TeamId;
import com.omnicrola.voxel.fx.MaterialToken;

/**
 * Created by omnic on 1/16/2016.
 */
public interface IWorldBuilder {
    Geometry terrainVoxel(ColorRGBA color);

    Spatial projectile(Spatial emittingEntity, int projectileId);

    Spatial unit(int definitionId, TeamId teamId);

    IParticleBuilder particles();

    Spatial structure(int unitId, TeamId teamId);

    Material material(MaterialToken materialToken);

    Spatial arrow(Vector3f direction, ColorRGBA blue);

    Spatial unitCursor(int unitId);
}
