package com.omnicrola.voxel.ui;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.shape.Quad;
import com.jme3.util.BufferUtils;

import java.nio.FloatBuffer;

/**
 * Created by Eric on 2/29/2016.
 */
public class UiSelectionRectangle extends Node {

    private final Quad quad;
    private Vector3f setVertex;
    private Vector2f selectionStart;

    public UiSelectionRectangle(Quad quad, Geometry geometry) {
        super("Selection Rectangle");
        this.attachChild(geometry);
        this.quad = quad;
        setVertex = new Vector3f();
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
        drawRectangle(p, new Vector2f(p).addLocal(5, 5));
    }

    private void drawRectangle(Vector2f topLeft, Vector2f bottomRight) {
        float left = Math.min(topLeft.x, bottomRight.x);
        float right = Math.max(topLeft.x, bottomRight.x);
        float top = Math.max(topLeft.y, bottomRight.y);
        float bottom = Math.min(topLeft.y, bottomRight.y);

        VertexBuffer positionBuffer = quad.getBuffer(VertexBuffer.Type.Position);
        FloatBuffer floatBuffer = (FloatBuffer) positionBuffer.getData();

        BufferUtils.setInBuffer(vert2d(left, bottom), floatBuffer, 0);  // lower left
        BufferUtils.setInBuffer(vert2d(right, bottom), floatBuffer, 1);  // lower right
        BufferUtils.setInBuffer(vert2d(right, top), floatBuffer, 2);  // upper right
        BufferUtils.setInBuffer(vert2d(left, top), floatBuffer, 3);  // upper left

        positionBuffer.setUpdateNeeded();
    }

    private Vector3f vert2d(float x, float y) {
        this.setVertex.set(x, y, 0);
        return this.setVertex;
    }

    public void setLowerRight(Vector2f p) {
        drawRectangle(this.selectionStart, p);
    }
}
