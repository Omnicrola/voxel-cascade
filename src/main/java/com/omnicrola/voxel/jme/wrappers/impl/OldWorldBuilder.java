package com.omnicrola.voxel.jme.wrappers.impl;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.data.units.ProjectileDefinition;
import com.omnicrola.voxel.data.units.StructureDefinition;
import com.omnicrola.voxel.data.units.UnitDefinition;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.entities.control.IControlFactory;
import com.omnicrola.voxel.fx.MaterialToken;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.jme.wrappers.IParticleBuilder;
import com.omnicrola.voxel.jme.wrappers.IWorldBuilder;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.settings.GameConstants;

/**
 * Created by omnic on 1/16/2016.
 */
public class OldWorldBuilder implements IWorldBuilder {

    private final UnitDefinitionRepository definitionRepository;
    private AssetManager assetManager;
    private IGameContainer gameContainer;
    private IParticleBuilder particleBuilder;

    public OldWorldBuilder(AssetManager assetManager, IGameContainer gameContainer, ParticleBuilder particleBuilder) {
        this.assetManager = assetManager;
        this.gameContainer = gameContainer;
        this.particleBuilder = particleBuilder;
        this.definitionRepository = (UnitDefinitionRepository) assetManager.loadAsset(GameConstants.DEFINITION_REPOSITORY_FILE);
    }

    @Override
    public Geometry terrainVoxel(ColorRGBA color) {
        Box box = new Box(0.5f, 0.5f, 0.5f);
        Geometry geometry = new Geometry("voxel", box);
        Material material = createMaterial(color);
        geometry.setMaterial(material);
        geometry.setLocalTranslation(0.5f, 0.5f, 0.5f);
        return geometry;
    }

    @Override
    public Spatial unitCursor(int definitionId) {
        UnitDefinition entityDefinition = getUnitDefinition(definitionId);
        Spatial spatial = getModel(entityDefinition.getModel());
        spatial.setName(entityDefinition.getName());
        Material material = createMaterial(entityDefinition.getTexture());
        spatial.setMaterial(material);
        return spatial;
    }

    @Override
    public Spatial unit(int definitionId, TeamData teamData) {
        UnitDefinition unitDefinition = getUnitDefinition(definitionId);
        Spatial spatial = unitCursor(definitionId);
        for (IControlFactory factory : unitDefinition.getControlFactories()) {
            factory.build(spatial, this.definitionRepository, );
        }
        spatial.setUserData(EntityDataKeys.IS_SELECTABLE, true);
        spatial.setUserData(EntityDataKeys.IS_TARGETABLE, true);
        spatial.setUserData(EntityDataKeys.IS_COLLIDABLE, true);
        spatial.setUserData(EntityDataKeys.IS_UNIT, true);
        spatial.setUserData(EntityDataKeys.HARVEST_RANGE, 2f);
        spatial.setUserData(EntityDataKeys.HITPOINTS, unitDefinition.getHitpoints());
        spatial.setUserData(EntityDataKeys.TEAM_DATA, teamData);
        return spatial;
    }

    @Override
    public Spatial structure(int definitionId, TeamData teamData) {
        StructureDefinition structureDefinition = getStructureDefinition(definitionId);
        Spatial structure = buildStructureModel(structureDefinition);

        for (IControlFactory factory : structureDefinition.getControlFactories()) {
            factory.build(structure, this.definitionRepository, );
        }

        structure.setUserData(EntityDataKeys.IS_SELECTABLE, true);
        structure.setUserData(EntityDataKeys.IS_STRUCTURE, true);
        structure.setUserData(EntityDataKeys.IS_COLLIDABLE, true);
        structure.setUserData(EntityDataKeys.IS_TARGETABLE, true);
        structure.setUserData(EntityDataKeys.HITPOINTS, structureDefinition.getHitpoints());
        structure.setUserData(EntityDataKeys.TEAM_DATA, teamData);

        return structure;
    }


    @Override
    public Spatial projectile(Spatial emittingEntity, int projectileId) {
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
        projectile.setUserData(EntityDataKeys.IS_COLLIDABLE, true);
        projectile.setUserData(EntityDataKeys.PROJECTILE_DAMAGE, projectileDefinition.getDamage());
        projectile.setUserData(EntityDataKeys.TEAM_DATA, emittingEntity.getUserData(EntityDataKeys.TEAM_DATA));
        projectile.setUserData(EntityDataKeys.PROJECTILE_EMITTING_ENTITY, emittingEntity);

        return projectile;
    }

    @Override
    public IParticleBuilder particles() {
        return this.particleBuilder;
    }

    @Override
    public Material material(MaterialToken materialToken) {
        Material material;
        if (materialToken.color() != null) {
            material = createMaterial(materialToken.color());
        } else {
            material = createMaterial(materialToken.texture());
        }
        if (materialToken.isTransparent()) {
            material.setBoolean("UseAlpha", true);
            material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        }
        return material;
    }

    private Texture getTexture(String texture) {
        return this.assetManager.loadTexture(GameConstants.DIR_TEXTURES + texture);
    }

    private Spatial getModel(String modelName) {
        if (modelName.equals("CUBE")) {
            Box box = new Box(0.4f, 0.4f, 0.4f);
            return new Geometry("cube", box);
        }
        return this.assetManager.loadModel(GameConstants.DIR_MODELS + modelName);
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

    @Override
    public Spatial arrow(Vector3f direction, ColorRGBA color) {
        Arrow arrow = new Arrow(direction);
        arrow.setLineWidth(4);
        return buildShape(arrow, color);
    }

    private Geometry buildShape(Mesh shape, ColorRGBA color) {
        Geometry geometry = new Geometry("coordinate axis", shape);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.getAdditionalRenderState().setWireframe(true);
        mat.setColor("Color", color);
        geometry.setMaterial(mat);
        return geometry;
    }


    private UnitDefinition getUnitDefinition(int definitionId) {
        UnitDefinition entityDefinition = this.definitionRepository.getUnitDefinition(definitionId);
        if (entityDefinition == UnitDefinition.NONE) {
            throw new IllegalArgumentException("Unit with ID of " + definitionId + " is not defined.");
        }
        return entityDefinition;
    }

    private Spatial buildStructureModel(StructureDefinition structureDefinition) {
        Spatial structure = getModel(structureDefinition.getModel());
        structure.setName(structureDefinition.getName());
        Material material = createMaterial(structureDefinition.getTexture());
        structure.setMaterial(material);
        return structure;
    }

    private StructureDefinition getStructureDefinition(int definitionId) {
        StructureDefinition structureDefinition = this.definitionRepository.getBuildingDefinition(definitionId);
        if (structureDefinition == StructureDefinition.NONE) {
            throw new IllegalArgumentException("Structure with ID of " + definitionId + " is not defined");
        }
        return structureDefinition;
    }
}
