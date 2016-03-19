package com.omnicrola.voxel.ui.decorations.selected;

import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.ui.decorations.IDecoration;

/**
 * Created by Eric on 3/19/2016.
 */
public class SelectionRing extends Node implements IDecoration {
    private Geometry ring;
    private Spatial targetSpatial;

    public SelectionRing(Geometry ring) {
        this.ring = ring;
        this.attachChild(ring);
    }

    @Override
    public void attachTo(Spatial spatial) {
        this.targetSpatial = spatial;
    }

    @Override
    public void detatchFrom(Spatial spatial) {
        this.targetSpatial = null;
    }

    @Override
    public void removeFromWorld() {
        if (this.parent != null) {
            this.parent.detachChild(this);
        }
    }
}
