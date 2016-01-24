package com.omnicrola.voxel.input;

import com.jme3.math.Vector2f;

/**
 * Created by omnic on 1/23/2016.
 */
public class ScreenRectangle {
    private final float left;
    private final float right;
    private final float top;
    private final float bottom;

    public ScreenRectangle(Vector2f p1, Vector2f p2) {
        this.left = Math.min(p1.x, p2.x);
        this.right = Math.max(p1.x, p2.x);
        this.top = Math.min(p1.y, p2.y);
        this.bottom = Math.max(p1.y, p2.y);
    }

    public Vector2f getTopLeft() {
        return new Vector2f(left, top);
    }

    public Vector2f getTopRight() {
        return new Vector2f(right, top);
    }

    public Vector2f getBottomLeft() {
        return new Vector2f(left, bottom);
    }

    public Vector2f getBottomRight() {
        return new Vector2f(right, bottom);
    }
}
