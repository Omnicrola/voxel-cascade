package com.omnicrola.voxel.jme.wrappers.impl;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.omnicrola.voxel.data.xml.DefinitionRepository;
import com.omnicrola.voxel.data.xml.UnitDefinition;
import com.omnicrola.voxel.data.xml.ProjectileDefinition;
import com.omnicrola.voxel.engine.physics.CollisionController;
import com.omnicrola.voxel.engine.physics.ProjectileCollisionHandler;
import com.omnicrola.voxel.entities.control.IControlFactory;
import com.omnicrola.voxel.entities.control.LinearProjectileControl;
import com.omnicrola.voxel.jme.wrappers.IGeometryBuilder;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.settings.GameConstants;

/**
 * Created by omnic on 1/16/2016.
 */
public class EntityBuilder implements IGeometryBuilder {
    private static final String LIGHTED_MATERIAL = "Common/MatDefs/Light/Lighting.j3md";

    private final DefinitionRepository definitionRepository;
    private AssetManager assetManager;
    private JmeWorldWrapper worldWrapper;

    public EntityBuilder(AssetManager assetManager, JmeWorldWrapper jmeWorldWrapper) {
        this.assetManager = assetManager;
        this.definitionRepository = (DefinitionRepository) assetManager.loadAsset(GameConstants.DEFINITION_REPOSITORY_FILE);
        this.worldWrapper = jmeWorldWrapper;
    }

    @Override
    public Geometry terrainVoxel(float size, ColorRGBA color) {
        Box box = new Box(size, size, size);
        Geometry cube = new Geometry("terrain-voxel", box);
        Material material = createMaterial(color);
        cube.setMaterial(material);
        return cube;
    }

    @Override
    public Spatial unit(int definitionId) {
        UnitDefinition entityDefinition = this.definitionRepository.getUnitDefinition(definitionId);
        if (entityDefinition == UnitDefinition.NONE) {
            throw new IllegalArgumentException("Entity with ID of " + definitionId + " does not exist");
        }
        Spatial spatial = this.assetManager.loadModel(entityDefinition.getModel());
        Texture texture = this.assetManager.loadTexture(entityDefinition.getTexture());
        Material material = new Material(this.assetManager, LIGHTED_MATERIAL);
        material.setTexture("DiffuseMap", texture);
        spatial.setMaterial(material);
        for (IControlFactory factory : entityDefinition.getControlFactories(this.definitionRepository)) {
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

        projectile.setUserData(EntityDataKeys.IS_PROJECTILE, true);
        projectile.setUserData(EntityDataKeys.PROJECTILE_DAMAGE, projectileDefinition.getDamage());
        projectile.addControl(new CollisionController(new ProjectileCollisionHandler(projectile, this.worldWrapper)));
        LinearProjectileControl linearProjectileControl = new LinearProjectileControl(attackVector);
        projectile.addControl(linearProjectileControl);

        return projectile;
    }

    private Material createMaterial(ColorRGBA color) {
        Material material = new Material(assetManager, LIGHTED_MATERIAL);
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Ambient", color);
        material.setColor("Diffuse", color);
        return material;
    }
}