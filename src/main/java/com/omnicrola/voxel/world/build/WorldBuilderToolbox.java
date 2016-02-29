package com.omnicrola.voxel.world.build;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.omnicrola.voxel.data.ILevelManager;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.settings.GameConstants;

/**
 * Created by Eric on 2/28/2016.
 */
public  class WorldBuilderToolbox {
    private AssetManager assetManager;
    private ILevelManager levelManager;
    private UnitDefinitionRepository definitionRepository;

    public WorldBuilderToolbox(AssetManager assetManager,
                               ILevelManager levelManager,
                               UnitDefinitionRepository definitionRepository                               ) {
        this.assetManager = assetManager;
        this.levelManager = levelManager;
        this.definitionRepository = definitionRepository;
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

    public UnitDefinitionRepository getDefinitionRepository() {
        return definitionRepository;
    }
}
