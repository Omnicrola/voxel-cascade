package com.omnicrola.voxel.input;

import com.jme3.math.Plane;
import com.jme3.math.Vector3f;

/**
 * Created by omnic on 3/5/2016.
 */
public class SelectionFrustrum {
    private final Plane leftPlane;
    private final Plane rightPlane;
    private final Plane topPlane;
    private final Plane bottomPlane;

    public SelectionFrustrum(Plane leftPlane, Plane rightPlane, Plane topPlane, Plane bottomPlane) {
        this.leftPlane = leftPlane;
        this.rightPlane = rightPlane;
        this.topPlane = topPlane;
        this.bottomPlane = bottomPlane;
    }

    public boolean isContained(Vector3f location) {
        boolean inLeft = this.leftPlane.whichSide(location).equals(Plane.Side.Positive);
        boolean inRight = this.rightPlane.whichSide(location).equals(Plane.Side.Positive);
        boolean inTop = this.topPlane.whichSide(location).equals(Plane.Side.Positive);
        boolean inBottom = this.bottomPlane.whichSide(location).equals(Plane.Side.Positive);
        return inLeft && inRight && inTop && inBottom;
    }
}
