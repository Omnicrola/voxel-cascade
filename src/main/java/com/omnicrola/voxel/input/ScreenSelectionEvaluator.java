package com.omnicrola.voxel.input;

import com.jme3.math.FastMath;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;

/**
 * Created by omnic on 1/24/2016.
 */
public class ScreenSelectionEvaluator {
    private final Vector3f cameraLocation;
    private final Vector3f b1;
    private final Vector3f b2;
    private final Vector3f b3;
    private final Vector3f b4;

    public ScreenSelectionEvaluator(Vector3f cameraLocation, Vector3f b1, Vector3f b2, Vector3f b3, Vector3f b4) {

        this.cameraLocation = cameraLocation;
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
        this.b4 = b4;
    }

    public boolean isInSelection(Vector3f targetLocation) {
        Ray ray = new Ray(targetLocation, randomVector());

        if (contains(ray, b1, b2)) return true;
        if (contains(ray, b2, b3)) return true;
        if (contains(ray, b3, b4)) return true;
        if (contains(ray, b4, b1)) return true;
        if (contains(ray, b1, b2, b3)) return true;
        return contains(ray, b1, b3, b4);

    }

    private boolean contains(Ray ray, Vector3f v1, Vector3f v2) {
        return contains(ray, this.cameraLocation, v1, v2);
    }

    private boolean contains(Ray ray, Vector3f v1, Vector3f v2, Vector3f v3) {
        float intersectionDistance = ray.intersects(v1, v2, v3);
        boolean isContained = intersectionDistance > Float.NEGATIVE_INFINITY
                && intersectionDistance < Float.POSITIVE_INFINITY;
        return isContained;
    }

    private Vector3f randomVector() {
        return new Vector3f(FastMath.nextRandomFloat(), FastMath.nextRandomFloat(), FastMath.nextRandomFloat());
    }
}
