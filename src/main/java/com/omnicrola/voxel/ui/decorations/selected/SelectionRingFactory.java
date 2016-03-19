package com.omnicrola.voxel.ui.decorations.selected;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import com.omnicrola.voxel.settings.GameConstants;

/**
 * Created by Eric on 3/19/2016.
 */
public class SelectionRingFactory {
    private AssetManager assetManager;

    public SelectionRingFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public SelectionRing build() {
        Geometry geometry = new Geometry("selection ring", new Quad(1, 1));
        Material material = new Material(assetManager, GameConstants.MATERIAL_UNSHADED);
        Texture texture = assetManager.loadTexture("Interface/selection-ring.png");
        material.setTexture("ColorMap", texture);
        geometry.setMaterial(material);

        SelectionRing selectionRing = new SelectionRing(geometry);

        return selectionRing;
    }
}
