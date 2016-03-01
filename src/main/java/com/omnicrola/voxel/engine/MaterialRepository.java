package com.omnicrola.voxel.engine;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture;
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
            this.materials.get(materialToken);
        }
        return this.materials.get(materialToken);
    }

    public Material getByType(IVoxelType type) {
        if (!this.voxelMaterials.containsKey(type)) {
            makeMaterial(type);
        }
        return this.voxelMaterials.get(type);
    }

    public Material getMaterial(MaterialToken materialToken) {
        Material material = createMaterial(materialToken.texture());
        if (materialToken.isTransparent()) {
            material.setBoolean("UseAlpha", true);
            material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        }
        return material;
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

    private void makeMaterial(IVoxelType type) {
        ColorRGBA color = type.color();
        Material material = getMaterial(color);
        this.voxelMaterials.put(type, material);
    }

    public Material getMaterial(ColorRGBA color) {
        Material material = new Material(assetManager, GameConstants.MATERIAL_SHADED);
        material.setBoolean("UseMaterialColors", true);
        material.setBoolean("UseVertexColor", true);
        material.setColor("Ambient", color);
        material.setColor("Diffuse", color);
        return material;
    }

}
