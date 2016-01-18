package com.omnicrola.voxel.jme.wrappers;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.entities.EntityDefinition;
import com.omnicrola.voxel.data.entities.ProjectileDefinition;

/**
 * Created by omnic on 1/16/2016.
 */
public interface IGeometryBuilder {
    Geometry cube(float size, ColorRGBA color);

    Spatial entity(EntityDefinition entityDefinition);

    Geometry box(float width, float height, float depth, ColorRGBA color);

    Geometry cylinder(float radius, float height, ColorRGBA color);

    Geometry sphere(float radius, ColorRGBA colorRGBA);

    Spatial projectile(ProjectileDefinition projectileDefinition, Vector3f attackVector);
}
