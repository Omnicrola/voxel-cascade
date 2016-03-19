package com.omnicrola.voxel.ui.decorations.selected;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import com.omnicrola.voxel.entities.control.move.RotationControl;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.ui.decorations.DecorationPlacementHelper;

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
        geometry.setLocalTranslation(-0.5f, 0, -0.5f);
        Quaternion rotation = new Quaternion();
        rotation.lookAt(Vector3f.UNIT_Y, Vector3f.UNIT_X);
        geometry.setLocalRotation(rotation);

        Material material = new Material(assetManager, GameConstants.MATERIAL_UNSHADED);
        Texture texture = assetManager.loadTexture("Interface/selection-ring.png");
        material.setTexture("ColorMap", texture);
        geometry.setMaterial(material);

        DecorationPlacementHelper placementHelper = new DecorationPlacementHelper(new Vector3f());
        placementHelper.setPosition(DecorationPlacementHelper.Position.BOTTOM);
        SelectionRing selectionRing = new SelectionRing(geometry, placementHelper);
        selectionRing.addControl(new RotationControl(new Vector3f(0, 1, 0), 1f));

        return selectionRing;
    }
}
