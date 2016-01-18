package com.omnicrola.voxel.world;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.omnicrola.voxel.data.entities.EntityDefinition;
import com.omnicrola.voxel.data.entities.ProjectileDefinition;
import com.omnicrola.voxel.engine.physics.CollisionController;
import com.omnicrola.voxel.engine.physics.ProjectileCollisionHandler;
import com.omnicrola.voxel.entities.control.IControlFactory;
import com.omnicrola.voxel.entities.control.LinearProjectileControl;
import com.omnicrola.voxel.jme.wrappers.IGeometryBuilder;
import com.omnicrola.voxel.jme.wrappers.impl.JmeWorldWrapper;
import com.omnicrola.voxel.settings.EntityDataKeys;

/**
 * Created by omnic on 1/16/2016.
 */
public class GeometryBuilder implements IGeometryBuilder {
    private static final String LIGHTED_MATERIAL = "Common/MatDefs/Light/Lighting.j3md";
    private AssetManager assetManager;
    private JmeWorldWrapper worldWrapper;

    public GeometryBuilder(AssetManager assetManager, JmeWorldWrapper jmeWorldWrapper) {
        this.assetManager = assetManager;
        this.worldWrapper = jmeWorldWrapper;
    }

    @Override
    public Geometry cube(float size, ColorRGBA color) {
        Box box = new Box(size, size, size);
        Geometry cube = new Geometry("cube", box);
        Material material = createMaterial(color);
        cube.setMaterial(material);
        return cube;
    }

    @Override
    public Spatial entity(EntityDefinition entityDefinition) {
        Spatial spatial = this.assetManager.loadModel(entityDefinition.getModel());
        Texture texture = assetManager.loadTexture(entityDefinition.getTexture());
        Material material = new Material(this.assetManager, LIGHTED_MATERIAL);
        material.setTexture("DiffuseMap", texture);
        spatial.setMaterial(material);
        for (IControlFactory factory : entityDefinition.getControlFactories()) {
            factory.build(spatial, worldWrapper);
        }
        spatial.setUserData(EntityDataKeys.IS_SELECTABLE, true);
        spatial.setUserData(EntityDataKeys.HITPOINTS, entityDefinition.getHitpoints());
        return spatial;
    }

    @Override
    public Spatial projectile(ProjectileDefinition projectileDefinition, Vector3f attackVector) {
        Spatial projectile = this.assetManager.loadModel(projectileDefinition.getModel());
        Texture texture = assetManager.loadTexture(projectileDefinition.getTexture());
        Material material = new Material(this.assetManager, LIGHTED_MATERIAL);
        material.setTexture("DiffuseMap", texture);
        projectile.setMaterial(material);
        for (IControlFactory factory : projectileDefinition.getControlFactories()) {
            factory.build(projectile, worldWrapper);
        }
        projectile.setUserData(EntityDataKeys.IS_PROJECTILE, true);
        projectile.setUserData(EntityDataKeys.PROJECTILE_DAMAGE, projectileDefinition.getDamage());
        projectile.addControl(new CollisionController(new ProjectileCollisionHandler(projectile, this.worldWrapper)));
        LinearProjectileControl linearProjectileControl = new LinearProjectileControl(attackVector);
        projectile.addControl(linearProjectileControl);

        return projectile;
    }

    @Override
    public Geometry box(float width, float height, float depth, ColorRGBA color) {
        Box box = new Box(width, height, depth);
        Geometry cube = new Geometry("cube", box);
        Material material = createMaterial(color);
        cube.setMaterial(material);
        return cube;
    }

    private Material createMaterial(ColorRGBA color) {
        Material material = new Material(assetManager, LIGHTED_MATERIAL);
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Ambient", color);
        material.setColor("Diffuse", color);
        return material;
    }

    @Override
    public Geometry cylinder(float radius, float height, ColorRGBA color) {
        Cylinder cylinder = new Cylinder(1, 8, radius, height);
        Geometry geometry = new Geometry("cylinder", cylinder);
        Material material = createMaterial(color);
        geometry.setMaterial(material);
        return geometry;
    }

    @Override
    public Geometry sphere(float radius, ColorRGBA color) {
        Sphere sphere = new Sphere(8, 8, radius);
        Geometry geometry = new Geometry("sphere", sphere);
        Material material = createMaterial(color);
        geometry.setMaterial(material);
        return geometry;
    }
}
