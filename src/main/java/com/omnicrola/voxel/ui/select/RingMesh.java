package com.omnicrola.voxel.ui.select;

import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.stream.Stream;

/**
 * Created by Eric on 3/2/2016.
 */
public class RingMesh extends Mesh {

    public static final int LINE_WIDTH = 1;
    private float top;
    private float left;
    private float bottom;
    private float right;
    private Vector3f setVertex = new Vector3f();

    public RingMesh() {
        Vector3f[] verticies = Stream.generate(() -> new Vector3f())
                .limit(16)
                .toArray(Vector3f[]::new);
        this.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(verticies));
        this.setBuffer(VertexBuffer.Type.Index, 2, createIndicies());

        this.top = 0f;
        this.left = 0f;
        this.bottom = 0f;
        this.right = 0f;
    }

    private ShortBuffer createIndicies() {
        short[] indicies = new short[]{
                0, 1, 3,
                1, 2, 3,

                4, 5, 7,
                5, 6, 7,

                8, 9, 11,
                9, 10, 11,

                12, 13, 15,
                13, 14, 15
        };

        return BufferUtils.createShortBuffer(indicies);
    }

    public void setTopLeft(float left, float top) {
        this.top = top;
        this.left = left;
        recalculateMesh();
    }

    public void setBottomRight(float right, float bottom) {
        this.right = right;
        this.bottom = bottom;
        recalculateMesh();
    }

    private void recalculateMesh() {
        float localLeft = Math.min(left, right);
        float localRight = Math.max(left, right);
        float localTop = Math.max(top, bottom);
        float localBottom = Math.min(top, bottom);

        setQuad(localLeft, localTop, localLeft + LINE_WIDTH, localBottom, 0);  // left edge
        setQuad(localRight, localTop, localRight + LINE_WIDTH, localBottom, 4);  // right edge
        setQuad(localLeft, localTop, localRight, localTop - LINE_WIDTH, 8);  // top edge
        setQuad(localLeft, localBottom, localRight, localBottom - LINE_WIDTH, 12);  // bottom edge
    }

    private void setQuad(float x1, float y1, float x2, float y2, int indexOffset) {
        VertexBuffer positionBuffer = this.getBuffer(VertexBuffer.Type.Position);
        FloatBuffer floatBuffer = (FloatBuffer) positionBuffer.getData();

        BufferUtils.setInBuffer(vert2d(x1, y2), floatBuffer, indexOffset);  // lower left
        BufferUtils.setInBuffer(vert2d(x2, y2), floatBuffer, indexOffset + 1);  // lower right
        BufferUtils.setInBuffer(vert2d(x2, y1), floatBuffer, indexOffset + 2);  // upper right
        BufferUtils.setInBuffer(vert2d(x1, y1), floatBuffer, indexOffset + 3);  // upper left
        positionBuffer.setUpdateNeeded();
    }

    private Vector3f vert2d(float x, float y) {
        this.setVertex.set(x, y, 0);
        return this.setVertex;
    }
}
