package com.omnicrola.voxel.engine;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
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
        ColorRGBA color = type.color();
        Material material = new Material(assetManager, GameConstants.MATERIAL_SHADED);
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Ambient", color);
        material.setColor("Diffuse", color);
        this.materials.put(type, material);
    }
}
