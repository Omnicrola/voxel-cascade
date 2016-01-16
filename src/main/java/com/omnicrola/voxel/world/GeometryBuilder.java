package com.omnicrola.voxel.world;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.omnicrola.voxel.jme.wrappers.IGeometryBuilder;

/**
 * Created by omnic on 1/16/2016.
 */
public class GeometryBuilder implements IGeometryBuilder {
    private AssetManager assetManager;

    public GeometryBuilder(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public Geometry cube(float size, ColorRGBA color) {
        Box box = new Box(size, size, size);
        Geometry cube = new Geometry("cube", box);
        Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Ambient", color);
        material.setColor("Diffuse", color);
        cube.setMaterial(material);
        return cube;
    }
}
