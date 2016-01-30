package com.omnicrola.voxel.jme.wrappers.impl;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.data.units.ProjectileDefinition;
import com.omnicrola.voxel.data.units.StructureDefinition;
import com.omnicrola.voxel.data.units.UnitDefinition;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.engine.physics.CollisionController;
import com.omnicrola.voxel.engine.physics.ProjectileCollisionHandler;
import com.omnicrola.voxel.entities.control.IControlFactory;
import com.omnicrola.voxel.entities.control.LinearProjectileControl;
import com.omnicrola.voxel.fx.VoxelShowerSpawnAction;
import com.omnicrola.voxel.jme.wrappers.IEntityBuilder;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.jme.wrappers.IParticleBuilder;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.settings.GameConstants;

/**
 * Created by omnic on 1/16/2016.
 */
public class EntityBuilder implements IEntityBuilder {

    private final UnitDefinitionRepository definitionRepository;
    private AssetManager assetManager;
    private IGameContainer gameContainer;
    private IParticleBuilder particleBuilder;

    public EntityBuilder(AssetManager assetManager, IGameContainer gameContainer, ParticleBuilder particleBuilder) {
        this.assetManager = assetManager;
        this.gameContainer = gameContainer;
        this.particleBuilder = particleBuilder;
        this.definitionRepository = (UnitDefinitionRepository) assetManager.loadAsset(GameConstants.DEFINITION_REPOSITORY_FILE);
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
    public Spatial unit(int definitionId, TeamData teamData) {
        UnitDefinition entityDefinition = this.definitionRepository.getUnitDefinition(definitionId);
        if (entityDefinition == UnitDefinition.NONE) {
            throw new IllegalArgumentException("Unit with ID of " + definitionId + " is not defined.");
        }
        Spatial spatial = getModel(entityDefinition.getModel());
        spatial.setName(entityDefinition.getName());
        Material material = createMaterial(entityDefinition.getTexture());
        spatial.setMaterial(material);
        for (IControlFactory factory : entityDefinition.getControlFactories(this.gameContainer, this.definitionRepository)) {
            factory.build(spatial);
        }
        spatial.setUserData(EntityDataKeys.IS_SELECTABLE, true);
        spatial.setUserData(EntityDataKeys.IS_UNIT, true);
        spatial.setUserData(EntityDataKeys.HITPOINTS, entityDefinition.getHitpoints());
        spatial.setUserData(EntityDataKeys.TEAM_DATA, teamData);
        return spatial;
    }

    @Override
    public Spatial structure(int definitionId, TeamData teamData) {
        StructureDefinition structureDefinition = this.definitionRepository.getBuildingDefinition(definitionId);
        if (structureDefinition == StructureDefinition.NONE) {
            throw new IllegalArgumentException("Structure with ID of " + definitionId + " is not defined");
        }
        Spatial spatial = getModel(structureDefinition.getModel());
        spatial.setName(structureDefinition.getName());
        Material material = createMaterial(structureDefinition.getTexture());
        spatial.setMaterial(material);

        for (IControlFactory factory : structureDefinition.getControlFactories(this.gameContainer)) {
            factory.build(spatial);
        }

        spatial.setUserData(EntityDataKeys.IS_SELECTABLE, true);
        spatial.setUserData(EntityDataKeys.IS_STRUCTURE, true);
        spatial.setUserData(EntityDataKeys.HITPOINTS, structureDefinition.getHitpoints());
        spatial.setUserData(EntityDataKeys.TEAM_DATA, teamData);

        return spatial;
    }

    @Override
    public Spatial projectile(Spatial emittingEntity, int projectileId, Vector3f attackVector) {
        ProjectileDefinition projectileDefinition = this.definitionRepository.getProjectileDefinition(projectileId);
        if (projectileDefinition == ProjectileDefinition.NONE) {
            throw new IllegalArgumentException("Projectile with ID of " + projectileId + " is not defined");
        }

        Spatial projectile = getModel(projectileDefinition.getModel());
        Texture texture = getTexture(projectileDefinition.getTexture());
        Material material = new Material(this.assetManager, GameConstants.MATERIAL_SHADED);
        material.setTexture("DiffuseMap", texture);
        projectile.setMaterial(material);

        projectile.setUserData(EntityDataKeys.IS_PROJECTILE, true);
        projectile.setUserData(EntityDataKeys.PROJECTILE_DAMAGE, projectileDefinition.getDamage());
        projectile.setUserData(EntityDataKeys.TEAM_DATA, emittingEntity.getUserData(EntityDataKeys.TEAM_DATA));
        projectile.setUserData(EntityDataKeys.PROJECTILE_EMITTING_ENTITY, emittingEntity);

        ProjectileCollisionHandler projectileCollisionHandler = new ProjectileCollisionHandler(projectile, this.gameContainer.world());
        projectileCollisionHandler.setDeathAction(new VoxelShowerSpawnAction(this, 100));
        projectile.addControl(new CollisionController(projectileCollisionHandler));

        Vector3f velocity = attackVector.normalize().mult(projectileDefinition.getMuzzleVelocity());
        LinearProjectileControl linearProjectileControl = new LinearProjectileControl(projectileDefinition.getSize(), velocity);
        projectile.addControl(linearProjectileControl);

        return projectile;
    }

    @Override
    public IParticleBuilder particles() {
        return this.particleBuilder;
    }

    private Texture getTexture(String texture) {
        return this.assetManager.loadTexture("Textures/" + texture);
    }

    private Spatial getModel(String modelName) {
        if (modelName.equals("CUBE")) {
            Box box = new Box(0.4f, 0.4f, 0.4f);
            return new Geometry("cube", box);
        }
        return this.assetManager.loadModel("Models/" + modelName);
    }

    private Material createMaterial(String textureName) {
        Texture texture = getTexture(textureName);
        Material material = new Material(this.assetManager, GameConstants.MATERIAL_SHADED);
        material.setTexture("DiffuseMap", texture);
        return material;
    }

    private Material createMaterial(ColorRGBA color) {
        Material material = new Material(assetManager, GameConstants.MATERIAL_SHADED);
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Ambient", color);
        material.setColor("Diffuse", color);
        return material;
    }
}
