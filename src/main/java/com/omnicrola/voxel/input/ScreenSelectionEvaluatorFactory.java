package com.omnicrola.voxel.input;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

/**
 * Created by omnic on 1/24/2016.
 */
public class ScreenSelectionEvaluatorFactory {
    private Camera camera;
    private AssetManager assetManager;

    public ScreenSelectionEvaluatorFactory(Camera camera, AssetManager assetManager) {
        this.camera = camera;
        this.assetManager = assetManager;
    }

    public ScreenSelectionEvaluator build(ScreenRectangle screenRectangle) {
        float distance = 0.99f;
        Vector3f b1 = this.camera.getWorldCoordinates(screenRectangle.getTopLeft(), distance);
        Vector3f b2 = this.camera.getWorldCoordinates(screenRectangle.getTopRight(), distance);
        Vector3f b3 = this.camera.getWorldCoordinates(screenRectangle.getBottomRight(), distance);
        Vector3f b4 = this.camera.getWorldCoordinates(screenRectangle.getBottomLeft(), distance);
        return new ScreenSelectionEvaluator(this.camera.getLocation(),b1,b2,b3,b4);
    }

}
