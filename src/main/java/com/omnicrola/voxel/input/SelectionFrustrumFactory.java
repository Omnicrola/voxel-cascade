package com.omnicrola.voxel.input;

import com.jme3.math.Plane;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

/**
 * Created by omnic on 3/5/2016.
 */
public class SelectionFrustrumFactory {

    private Camera camera;

    public SelectionFrustrumFactory(Camera camera) {
        this.camera = camera;
    }

    public SelectionFrustrum build(ScreenRectangle screenRectangle) {

        float nearProjectionZPos = 0.1f;
        Vector3f frontTopLeft = camera.getWorldCoordinates(screenRectangle.getTopLeft(), nearProjectionZPos);
        Vector3f frontTopRight = camera.getWorldCoordinates(screenRectangle.getTopRight(), nearProjectionZPos);
        Vector3f frontBottomRight = camera.getWorldCoordinates(screenRectangle.getBottomRight(), nearProjectionZPos);
        Vector3f frontBottomLeft = camera.getWorldCoordinates(screenRectangle.getBottomLeft(), nearProjectionZPos);

        float farProjectionZPos = 0.99f;
        Vector3f backTopLeft = camera.getWorldCoordinates(screenRectangle.getTopLeft(), farProjectionZPos);
        Vector3f backTopRight = camera.getWorldCoordinates(screenRectangle.getTopRight(), farProjectionZPos);
        Vector3f backBottomRight = camera.getWorldCoordinates(screenRectangle.getBottomRight(), farProjectionZPos);
        Vector3f backBottomLeft = camera.getWorldCoordinates(screenRectangle.getBottomLeft(), farProjectionZPos);

        Plane leftPlane = newPlane(frontBottomLeft, frontTopLeft, backBottomLeft);
        Plane rightPlane = newPlane(frontBottomRight, backBottomRight, frontTopRight);
        Plane topPlane = newPlane(frontTopLeft, frontTopRight, backTopLeft);
        Plane bottomPlane = newPlane(frontBottomLeft, backBottomLeft, frontBottomRight);

        return new SelectionFrustrum(leftPlane, rightPlane, topPlane, bottomPlane);
    }

    private Plane newPlane(Vector3f v1, Vector3f v2, Vector3f v3) {
        Plane plane = new Plane();
        plane.setPlanePoints(v1, v2, v3);
        return plane;
    }
}
