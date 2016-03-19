package com.omnicrola.voxel.ui.decorations.selected;

import com.jme3.asset.AssetManager;

/**
 * Created by Eric on 3/19/2016.
 */
public class SelectionRingFactory {
    private AssetManager assetManager;

    public SelectionRingFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public SelectionRing build() {
        return new SelectionRing();
    }
}
