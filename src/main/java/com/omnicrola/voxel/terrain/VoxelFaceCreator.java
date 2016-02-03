package com.omnicrola.voxel.terrain;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import com.omnicrola.voxel.settings.GameConstants;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;

/**
 * Created by omnic on 2/2/2016.
 */
public class VoxelFaceCreator {

    public static final int VERTEXES_PER_FACE = 4;
    public static final int MAX_ARRAY_CAPACITY = GameConstants.CHUNK_SIZE_CUBED * VERTEXES_PER_FACE;
    public static final int VALUES_PER_VERTEX = 3;
    public static final int INDEXES_PER_TRIANGLE = 3;

    private final List<Vector3f> vertexes;
    private final Map<Vector3f, Integer> indexMap;
    private final ArrayList<Integer> indexes;

    public VoxelFaceCreator() {
        this.vertexes = new ArrayList<>(MAX_ARRAY_CAPACITY);
        this.indexMap = new HashMap<>(MAX_ARRAY_CAPACITY);
        this.indexes = new ArrayList<>(MAX_ARRAY_CAPACITY);
    }

    public Mesh buildTopFaces(ChunkId chunkId, BitSet topFaces) {
        reset();

        final int size = topFaces.size();
        int index = 0;
        while (index < size) {
            if (topFaces.get(index)) {
                buildFace(index, chunkId);
            }
            index++;
        }
        Mesh mesh = new Mesh();
        mesh.setBuffer(VertexBuffer.Type.Position, VALUES_PER_VERTEX, getVertexBuffer());
        mesh.setBuffer(VertexBuffer.Type.TexCoord, 2, getTextureBuffer());
        mesh.setBuffer(VertexBuffer.Type.Index, INDEXES_PER_TRIANGLE, getIndexBuffer());
        mesh.updateBound();
        return mesh;
    }

    private IntBuffer getIndexBuffer() {
        IntBuffer intBuffer = BufferUtils.createIntBuffer(this.indexes.size() * INDEXES_PER_TRIANGLE);
        this.indexes.forEach(i -> intBuffer.put(i));
        return intBuffer;
    }

    private FloatBuffer getVertexBuffer() {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(this.vertexes.size() * VALUES_PER_VERTEX);
        this.vertexes.forEach(v -> buildBuffer(floatBuffer, v));
        return floatBuffer;
    }

    private void buildBuffer(FloatBuffer floatBuffer, Vector3f v) {
        floatBuffer.put(v.getX());
        floatBuffer.put(v.getY());
        floatBuffer.put(v.getZ());
    }

    public FloatBuffer getTextureBuffer() {
        Vector2f[] textureCoordinates = new Vector2f[4];
        textureCoordinates[0] = new Vector2f(0, 0);
        textureCoordinates[1] = new Vector2f(1, 0);
        textureCoordinates[2] = new Vector2f(0, 1);
        textureCoordinates[3] = new Vector2f(1, 1);
        return BufferUtils.createFloatBuffer(textureCoordinates);
    }

    private void reset() {
        this.indexMap.clear();
        this.vertexes.clear();
        this.indexes.clear();
    }

    private void buildFace(int index, ChunkId chunkId) {
        Vector3f firstCorner = ChunkVertexLookup.findByIndex(index).asVector3f().add(chunkId.getX(), chunkId.getY(), chunkId.getZ());
        int index0 = findIndex(firstCorner);
        int index1 = findIndex(firstCorner.add(1, 0, 0));
        int index2 = findIndex(firstCorner.add(1, 0, 1));
        int index3 = findIndex(firstCorner.add(0, 0, 1));
        this.indexes.add(index2);
        this.indexes.add(index0);
        this.indexes.add(index1);
        this.indexes.add(index1);
        this.indexes.add(index3);
        this.indexes.add(index2);
    }

    private int findIndex(Vector3f vertex) {
        Integer index = this.indexMap.get(vertex);
        if (index != null) {
            return index.intValue();
        }
        this.vertexes.add(vertex);
        int newIndex = this.vertexes.indexOf(vertex);
        this.indexMap.put(vertex, newIndex);
        return newIndex;
    }
}
