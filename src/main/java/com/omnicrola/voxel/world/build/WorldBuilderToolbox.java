package com.omnicrola.voxel.world.build;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.omnicrola.voxel.data.ILevelManager;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.entities.control.EntityControlAdapter;
import com.omnicrola.voxel.entities.control.IControlFactory;
import com.omnicrola.voxel.settings.GameConstants;

import java.util.List;

/**
 * Created by Eric on 2/28/2016.
 */
public  class WorldBuilderToolbox {
    private AssetManager assetManager;
    private ILevelManager levelManager;
    private UnitDefinitionRepository definitionRepository;
    private EntityControlAdapter entityControlAdapter;

    public WorldBuilderToolbox(AssetManager assetManager,
                               ILevelManager levelManager,
                               UnitDefinitionRepository definitionRepository,
                               EntityControlAdapter entityControlAdapter) {
        this.assetManager = assetManager;
        this.levelManager = levelManager;
        this.definitionRepository = definitionRepository;
        this.entityControlAdapter = entityControlAdapter;
    }

    public void runFactories(Spatial spatial, List<IControlFactory> controlFactories) {
        controlFactories.forEach(f -> f.build(spatial, this.definitionRepository, this.entityControlAdapter));
    }

    public Spatial getModel(String modelName) {
        return this.assetManager.loadModel(GameConstants.DIR_MODELS + modelName);
    }

    public Material createMaterial(String textureName) {
        Texture texture = getTexture(textureName);
        Material material = new Material(this.assetManager, GameConstants.MATERIAL_SHADED);
        material.setTexture("DiffuseMap", texture);
        return material;
    }

    private Texture getTexture(String texture) {
        return this.assetManager.loadTexture(GameConstants.DIR_TEXTURES + texture);
    }

    public TeamData getTeamData(int teamId) {
        LevelState currentLevel = levelManager.getCurrentLevel();
        return currentLevel.getTeamById(teamId);
    }

}
