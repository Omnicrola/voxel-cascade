package com.omnicrola.voxel.engine;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.omnicrola.voxel.fx.MaterialToken;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.terrain.IVoxelType;

import java.util.HashMap;

/**
 * Created by Eric on 2/3/2016.
 */
public class MaterialRepository {

    private final HashMap<IVoxelType, Material> voxelMaterials;
    private final HashMap<MaterialToken, Material> materials;
    private AssetManager assetManager;

    public MaterialRepository(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.voxelMaterials = new HashMap<>();
        this.materials = new HashMap<>();
    }

    public Material get(MaterialToken materialToken) {
        if (!this.materials.containsKey(materialToken)) {
            makeMaterial(materialToken);
        }
        return this.materials.get(materialToken);
    }

    public Material getByType(IVoxelType type) {
        if (!this.voxelMaterials.containsKey(type)) {
            makeMaterial(type);
        }
        return this.voxelMaterials.get(type);
    }

    public void makeMaterial(MaterialToken materialToken) {
        ColorRGBA color = materialToken.color();
        Material material = getMaterial(color);
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Additive);
        this.materials.put(materialToken, material);
    }

    private void makeMaterial(IVoxelType type) {
        ColorRGBA color = type.color();
        Material material = getMaterial(color);
        this.voxelMaterials.put(type, material);
    }

    private Material getMaterial(ColorRGBA color) {
        Material material = new Material(assetManager, GameConstants.MATERIAL_SHADED);
        material.setBoolean("UseMaterialColors", true);
        material.setBoolean("UseVertexColor", true);
        material.setColor("Ambient", color);
        material.setColor("Diffuse", color);
        return material;
    }

}
