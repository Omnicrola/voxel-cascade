package com.omnicrola.voxel.input;

import com.jme3.math.Plane;
import com.jme3.math.Vector3f;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by omnic on 3/5/2016.
 */
public class SelectionFrustrumTest extends TestCase {

    @Test
    public void testIsContained() throws Exception {
        Plane leftPlane = new Plane();
        leftPlane.setPlanePoints(new Vector3f(-1, 0, 0), new Vector3f(-1, 1, 0), new Vector3f(-1, 0, 1));
        Plane rightPlane = new Plane();
        rightPlane.setPlanePoints(new Vector3f(1, 0, 0), new Vector3f(1, 0, 1), new Vector3f(1, 1, 0));
        Plane topPlane = new Plane();
        topPlane.setPlanePoints(new Vector3f(0, 1, 0), new Vector3f(1, 1, 0), new Vector3f(0, 1, 1));
        Plane bottomPlane = new Plane();
        bottomPlane.setPlanePoints(new Vector3f(0, -1, 0), new Vector3f(0, -1, 1), new Vector3f(1, -1, 0));

        SelectionFrustrum selectionFrustrum = new SelectionFrustrum(leftPlane, rightPlane, topPlane, bottomPlane);
        Vector3f zeroPoint = new Vector3f();
        System.out.println("left :" + leftPlane.whichSide(zeroPoint));
        System.out.println("right :" + rightPlane.whichSide(zeroPoint));
        System.out.println("top :" + topPlane.whichSide(zeroPoint));
        System.out.println("bottom :" + bottomPlane.whichSide(zeroPoint));

        assertTrue(selectionFrustrum.isContained(zeroPoint));
        assertFalse(selectionFrustrum.isContained(new Vector3f(-2, 0, 0)));
        assertFalse(selectionFrustrum.isContained(new Vector3f(2, 0, 0)));
        assertFalse(selectionFrustrum.isContained(new Vector3f(0, -2, 0)));
        assertFalse(selectionFrustrum.isContained(new Vector3f(0, 2, 0)));
    }
}