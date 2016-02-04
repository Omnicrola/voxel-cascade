package com.omnicrola.voxel.terrain;

import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import com.omnicrola.voxel.engine.MaterialRepository;

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

    public Geometry build(final Vector3f bottomLeft,
                          final Vector3f topLeft,
                          final Vector3f topRight,
                          final Vector3f bottomRight,
                          final VoxelFace voxel,
                          final boolean backFace) {

        final Vector3f[] vertices = new Vector3f[4];

        vertices[2] = topLeft.multLocal(VoxelChunkRebuilder.VOXEL_SIZE);
        vertices[3] = topRight.multLocal(VoxelChunkRebuilder.VOXEL_SIZE);
        vertices[0] = bottomLeft.multLocal(VoxelChunkRebuilder.VOXEL_SIZE);
        vertices[1] = bottomRight.multLocal(VoxelChunkRebuilder.VOXEL_SIZE);

        Mesh mesh = createMesh(backFace, vertices);
        Geometry geometry = createGeometry(voxel, mesh);
        return geometry;
    }

    private Geometry createGeometry(VoxelFace voxel, Mesh mesh) {
        Geometry geometry = new Geometry("VoxelMesh", mesh);
        Material material = this.materialRepository.getByType(voxel.type());
        geometry.setMaterial(material);
        return geometry;
    }

    private Mesh createMesh(boolean backFace, Vector3f[] vertices) {
        Mesh mesh = new Mesh();
        mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        mesh.setBuffer(VertexBuffer.Type.TexCoord, 2, createTextureUv());
        mesh.setBuffer(VertexBuffer.Type.Index, 3, createIndexes(backFace));
        mesh.updateBound();
        return mesh;
    }

    private FloatBuffer createTextureUv() {
        Vector2f[] texCoord = new Vector2f[4];
        texCoord[0] = new Vector2f(0, 0);
        texCoord[1] = new Vector2f(1, 0);
        texCoord[2] = new Vector2f(0, 1);
        texCoord[3] = new Vector2f(1, 1);
        return BufferUtils.createFloatBuffer(texCoord);
    }

    private IntBuffer createIndexes(boolean backFace) {
        int[] indexes = backFace ? new int[]{2, 0, 1, 1, 3, 2} : new int[]{2, 3, 1, 1, 0, 2};
        return BufferUtils.createIntBuffer(indexes);
    }
}
