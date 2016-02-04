package com.omnicrola.voxel.engine;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.texture.Texture;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.terrain.IVoxelType;

import java.util.HashMap;

/**
 * Created by Eric on 2/3/2016.
 */
public class MaterialRepository {

    private final HashMap<IVoxelType, Material> materials;
    private AssetManager assetManager;

    public MaterialRepository(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.materials = new HashMap<>();
    }

    public Material getByType(IVoxelType type) {
        if (!this.materials.containsKey(type)) {
            makeMaterial(type);
        }
        return this.materials.get(type);
    }

    private void makeMaterial(IVoxelType type) {
        String textureName = type.texture();
        Texture texture = this.assetManager.loadTexture(GameConstants.DIR_TEXTURES + textureName);
        Material material = new Material(this.assetManager, GameConstants.MATERIAL_SHADED);
        material.setTexture("DiffuseMap", texture);
        this.materials.put(type,material);
    }
}
