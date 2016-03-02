package com.omnicrola.voxel.ui.select;

import com.jme3.math.Vector2f;
import com.jme3.scene.Node;

/**
 * Created by Eric on 2/29/2016.
 */
public class UiSelectionRectangle extends Node {

    private Vector2f selectionStart;
    private RingMesh ringMesh;

    public UiSelectionRectangle(RingMesh ringMesh) {
        super("Selection Rectangle");
        this.ringMesh = ringMesh;
    }

    public void setVisible(boolean isVisible) {
        if (isVisible) {
            this.setCullHint(CullHint.Inherit);
        } else {
            this.setCullHint(CullHint.Always);
        }
    }

    public void setUpperLeft(Vector2f p) {
        this.selectionStart = p.clone();
        this.ringMesh.setTopLeft(p.x, p.y);
        this.ringMesh.setBottomRight(p.x + 1, p.y + 1);
    }

    public void setLowerRight(Vector2f p) {
        this.ringMesh.setBottomRight(p.x, p.y);
    }

}
