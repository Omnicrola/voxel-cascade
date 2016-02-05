package com.omnicrola.voxel.terrain;

import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import com.omnicrola.voxel.engine.MaterialRepository;
import com.omnicrola.voxel.terrain.data.VoxelChunk;
import com.omnicrola.voxel.terrain.data.VoxelFace;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by Eric on 2/3/2016.
 */
public class QuadFactory {
    private MaterialRepository materialRepository;

    public QuadFactory(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
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

        final Vector3f[] vertices = new Vector3f[4];

        vertices[0] = bottomLeft.multLocal(VoxelChunkRebuilder.VOXEL_SIZE);
        vertices[1] = bottomRight.multLocal(VoxelChunkRebuilder.VOXEL_SIZE);
        vertices[2] = topLeft.multLocal(VoxelChunkRebuilder.VOXEL_SIZE);
        vertices[3] = topRight.multLocal(VoxelChunkRebuilder.VOXEL_SIZE);

        Mesh mesh = createMesh(indicies, vertices, side);
        Geometry geometry = createGeometry(voxel, mesh);
        return geometry;
    }

    private Geometry createGeometry(VoxelFace voxel, Mesh mesh) {
        Geometry geometry = new Geometry("VoxelMesh", mesh);
        Material material = this.materialRepository.getByType(voxel.type());
        geometry.setMaterial(material);
//        geometry.addControl(new VoxelPhysicsControl(mesh));
        return geometry;
    }

    private Mesh createMesh(IntBuffer indicies, Vector3f[] vertices, int side) {
        Mesh mesh = new Mesh();
        mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        mesh.setBuffer(VertexBuffer.Type.TexCoord, 2, createTextureUv(vertices));
        mesh.setBuffer(VertexBuffer.Type.Index, 3, indicies);
        mesh.setBuffer(VertexBuffer.Type.Normal, 3, createNormals(side));
        mesh.updateBound();
        return mesh;
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
