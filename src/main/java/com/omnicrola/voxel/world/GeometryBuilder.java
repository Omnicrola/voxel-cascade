package com.omnicrola.voxel.world;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Sphere;
import com.omnicrola.voxel.jme.wrappers.IGeometryBuilder;

/**
 * Created by omnic on 1/16/2016.
 */
public class GeometryBuilder implements IGeometryBuilder {
    private static final String LIGHTED_MATERIAL = "Common/MatDefs/Light/Lighting.j3md";
    private AssetManager assetManager;

    public GeometryBuilder(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public Geometry cube(float size, ColorRGBA color) {
        Box box = new Box(size, size, size);
        Geometry cube = new Geometry("cube", box);
        Material material = createMaterial(color);
        cube.setMaterial(material);
        return cube;
    }

    @Override
    public Geometry box(float width, float height, float depth, ColorRGBA color) {
        Box box = new Box(width, height, depth);
        Geometry cube = new Geometry("cube", box);
        Material material = createMaterial(color);
        cube.setMaterial(material);
        return cube;
    }

    private Material createMaterial(ColorRGBA color) {
        Material material = new Material(assetManager, LIGHTED_MATERIAL);
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Ambient", color);
        material.setColor("Diffuse", color);
        return material;
    }

    @Override
    public Geometry cylinder(float radius, float height, ColorRGBA color) {
        Cylinder cylinder = new Cylinder(1, 8, radius, height);
        Geometry geometry = new Geometry("cylinder", cylinder);
        Material material = createMaterial(color);
        geometry.setMaterial(material);
        return geometry;
    }

    @Override
    public Geometry sphere(float radius, ColorRGBA color) {
        Sphere sphere = new Sphere(8, 8, radius);
        Geometry geometry = new Geometry("sphere", sphere);
        Material material = createMaterial(color);
        geometry.setMaterial(material);
        return geometry;
    }
}
