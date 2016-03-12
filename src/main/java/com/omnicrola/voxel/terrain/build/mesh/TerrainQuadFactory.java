package com.omnicrola.voxel.terrain.build.mesh;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import com.omnicrola.voxel.engine.MaterialRepository;
import com.omnicrola.voxel.terrain.build.occlusion.OcclusionSet;
import com.omnicrola.voxel.terrain.data.VoxelChunk;
import com.omnicrola.voxel.terrain.data.VoxelFace;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by Eric on 2/3/2016.
 */
public class TerrainQuadFactory {
    private MaterialRepository materialRepository;
    private ITerrainQuadMeshStrategy[] meshStrategies;

    public TerrainQuadFactory(MaterialRepository materialRepository, ITerrainQuadMeshStrategy[] meshStrategies) {
        this.materialRepository = materialRepository;
        this.meshStrategies = meshStrategies;
    }

    public Geometry buildFront(final Vector3f bottomLeft,
                               final Vector3f topLeft,
                               final Vector3f topRight,
                               final Vector3f bottomRight,
                               final VoxelFace voxel,
                               int side) {
        int[] indexes = new int[]{2, 3, 1, 1, 0, 2};
        IntBuffer indexBuffer = BufferUtils.createIntBuffer(indexes);
        return build(bottomLeft, topLeft, topRight, bottomRight, voxel, indexBuffer, side);
    }

    public Geometry buildBack(final Vector3f bottomLeft,
                              final Vector3f topLeft,
                              final Vector3f topRight,
                              final Vector3f bottomRight,
                              final VoxelFace voxel,
                              int side) {
        int[] indexes = new int[]{2, 0, 1, 1, 3, 2};
        IntBuffer indexBuffer = BufferUtils.createIntBuffer(indexes);
        return build(bottomLeft, topLeft, topRight, bottomRight, voxel, indexBuffer, side);
    }

    private Geometry build(final Vector3f bottomLeft,
                           final Vector3f topLeft,
                           final Vector3f topRight,
                           final Vector3f bottomRight,
                           final VoxelFace voxel,
                           IntBuffer indicies,
                           int side) {

        final Vector3f[] vertices = this.meshStrategies[side].build(voxel, bottomLeft, bottomRight, topLeft, topRight);

        Mesh mesh = createMesh(voxel, indicies, vertices, side);
        Geometry geometry = createGeometry(voxel, mesh);
        return geometry;
    }

    private Geometry createGeometry(VoxelFace voxel, Mesh mesh) {
        Geometry geometry = new Geometry("VoxelMesh", mesh);
        Material material = this.materialRepository.getByType(voxel.type());
        geometry.setMaterial(material);
        return geometry;
    }

    private Mesh createMesh(VoxelFace voxel, IntBuffer indicies, Vector3f[] vertices, int side) {
        Mesh mesh = new Mesh();
        mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        mesh.setBuffer(VertexBuffer.Type.TexCoord, 2, createTextureUv(vertices));
        mesh.setBuffer(VertexBuffer.Type.Index, 3, indicies);
        mesh.setBuffer(VertexBuffer.Type.Normal, 3, createNormals(side));
        mesh.setBuffer(VertexBuffer.Type.Color, 4, createColors(voxel, vertices));
        mesh.updateBound();
        return mesh;
    }

    private float[] createColors(VoxelFace voxel, Vector3f[] vertices) {
        float[] colors = new float[vertices.length * 4];
        addColor(colors, voxel, 0);
        addColor(colors, voxel, 1);
        addColor(colors, voxel, 2);
        addColor(colors, voxel, 3);
        return colors;
    }

    private void addColor(float[] colors, VoxelFace voxel, int vertexIndex) {
        int bufferIndex = vertexIndex * 4;
        OcclusionSet occlusionSet = voxel.getOcclusion();
        float occlusionPercentage = occlusionSet.vertexValue(vertexIndex);
        ColorRGBA color = voxel.type().color();
        colors[bufferIndex++] = color.r * occlusionPercentage;
        colors[bufferIndex++] = color.g * occlusionPercentage;
        colors[bufferIndex++] = color.b * occlusionPercentage;
        colors[bufferIndex++] = 1f;
    }

    private FloatBuffer createNormals(int side) {
        if (side == VoxelChunk.SIDE_Y_NEG) {
            return createNormalBuffer(0, -1, 0);
        } else if (side == VoxelChunk.SIDE_Y_POS) {
            return createNormalBuffer(0, 1, 0);
        } else if (side == VoxelChunk.SIDE_X_NEG) {
            return createNormalBuffer(-1, 0, 0);
        } else if (side == VoxelChunk.SIDE_X_POS) {
            return createNormalBuffer(1, 0, 0);
        } else if (side == VoxelChunk.SIDE_Z_NEG) {
            return createNormalBuffer(0, 0, -1);
        } else if (side == VoxelChunk.SIDE_Z_POS) {
            return createNormalBuffer(0, 0, 1);
        }
        return null;
    }

    private FloatBuffer createNormalBuffer(float x, float y, float z) {
        float[] normals = new float[]{
                x, y, z,
                x, y, z,
                x, y, z,
                x, y, z,};
        return BufferUtils.createFloatBuffer(normals);
    }

    private FloatBuffer createTextureUv(Vector3f[] vertices) {
        float width = vertices[1].x - vertices[0].x;
        float height = vertices[1].y - vertices[0].y;


        Vector2f[] texCoord = new Vector2f[4];
        texCoord[0] = new Vector2f(0, 0);
        texCoord[1] = new Vector2f(width, 0);
        texCoord[2] = new Vector2f(0, height);
        texCoord[3] = new Vector2f(width, height);
        return BufferUtils.createFloatBuffer(texCoord);
    }

}
