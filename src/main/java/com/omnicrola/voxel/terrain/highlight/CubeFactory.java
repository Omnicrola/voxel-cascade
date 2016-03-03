package com.omnicrola.voxel.terrain.highlight;

import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.omnicrola.voxel.world.build.WorldEntityBuilder;

/**
 * Created by Eric on 3/2/2016.
 */
public class CubeFactory {

    private Vector3f scale;
    private WorldEntityBuilder worldEntityBuilder;
    private ColorRGBA color;
    private boolean isWireFrame;

    public CubeFactory(WorldEntityBuilder worldEntityBuilder) {
        this.worldEntityBuilder = worldEntityBuilder;
        this.scale = new Vector3f(1, 1, 1);
        this.color = ColorRGBA.White;
    }

    public void setScale(Vector3f newScale) {
        this.scale.set(newScale);
    }

    public void setColor(ColorRGBA color) {
        this.color = color;
    }

    public Geometry build() {
        Geometry cube = this.worldEntityBuilder.buildCube(this.color);
        Material material = cube.getMaterial();
        material.getAdditionalRenderState().setWireframe(this.isWireFrame);
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Additive);
        material.setBoolean("UseAlpha", true);
        cube.setLocalScale(this.scale);
        return cube;
    }

    public void setWireframe(boolean wireframe) {
        this.isWireFrame = wireframe;
    }
}
