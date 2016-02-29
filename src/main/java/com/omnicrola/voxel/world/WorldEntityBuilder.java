package com.omnicrola.voxel.world;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.data.level.UnitPlacement;
import com.omnicrola.voxel.data.units.UnitDefinition;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.entities.Structure;
import com.omnicrola.voxel.entities.Unit;
import com.omnicrola.voxel.entities.control.EntityControlAdapter;
import com.omnicrola.voxel.entities.control.IControlFactory;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.settings.GameConstants;

/**
 * Created by Eric on 2/22/2016.
 */
public class WorldEntityBuilder {
    private final UnitDefinitionRepository definitionRepository;
    private final AssetManager assetManager;
    private LevelManager levelManager;
    private EntityControlAdapter entityControlAdapter;

    public WorldEntityBuilder(UnitDefinitionRepository definitionRepository,
                              AssetManager assetManager,
                              LevelManager levelManager,
                              EntityControlAdapter entityControlAdapter) {
        this.definitionRepository = definitionRepository;
        this.assetManager = assetManager;
        this.levelManager = levelManager;
        this.entityControlAdapter = entityControlAdapter;
    }

    public Unit buildUnit(UnitPlacement unitPlacement) {
        int unitId = unitPlacement.getUnitId();
        UnitDefinition unitDefinition = getUnitDefinition(unitId);
        TeamData teamData = getTeamData(unitPlacement.getTeamId());
        Spatial spatial = getModel(unitDefinition.getModel());
        spatial.setName(unitDefinition.getName());
        Material material = createMaterial(unitDefinition.getTexture());
        spatial.setMaterial(material);
        for (IControlFactory factory : unitDefinition.getControlFactories()) {
            factory.build(spatial, this.definitionRepository, this.entityControlAdapter);
        }
        spatial.setUserData(EntityDataKeys.IS_SELECTABLE, true);
        spatial.setUserData(EntityDataKeys.IS_TARGETABLE, true);
        spatial.setUserData(EntityDataKeys.IS_COLLIDABLE, true);
        spatial.setUserData(EntityDataKeys.IS_UNIT, true);
        spatial.setUserData(EntityDataKeys.HARVEST_RANGE, 2f);
        spatial.setUserData(EntityDataKeys.HITPOINTS, unitDefinition.getHitpoints());
        spatial.setUserData(EntityDataKeys.TEAM_DATA, teamData);
        return new Unit(spatial);
    }

    public Structure buildStructure(UnitPlacement unitPlacement) {
        return null;
    }

    public Geometry buildCube(ColorRGBA color) {

        Material material = new Material(assetManager, GameConstants.MATERIAL_SHADED);
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Ambient", color);
        material.setColor("Diffuse", color);

        Box box = new Box(0.5f, 0.5f, 0.5f);
        Geometry cube = new Geometry("cube", box);
        cube.setMaterial(material);
        return cube;
    }

    private UnitDefinition getUnitDefinition(int definitionId) {
        UnitDefinition entityDefinition = this.definitionRepository.getUnitDefinition(definitionId);
        if (entityDefinition == UnitDefinition.NONE) {
            throw new IllegalArgumentException("Unit with ID of " + definitionId + " is not defined.");
        }
        return entityDefinition;
    }

    private Spatial getModel(String modelName) {
        return this.assetManager.loadModel(GameConstants.DIR_MODELS + modelName);
    }

    private Material createMaterial(String textureName) {
        Texture texture = getTexture(textureName);
        Material material = new Material(this.assetManager, GameConstants.MATERIAL_SHADED);
        material.setTexture("DiffuseMap", texture);
        return material;
    }

    private Texture getTexture(String texture) {
        return this.assetManager.loadTexture(GameConstants.DIR_TEXTURES + texture);
    }

    private TeamData getTeamData(int teamId) {
        LevelState currentLevel = levelManager.getCurrentLevel();
        return currentLevel.getTeamById(teamId);
    }
}
