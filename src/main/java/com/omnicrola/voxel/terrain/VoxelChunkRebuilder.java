package com.omnicrola.voxel.terrain;

import com.jme3.scene.Mesh;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;

import java.util.BitSet;

/**
 * Created by Eric on 1/31/2016.
 */
public class VoxelChunkRebuilder {

    private static final Vec3i TOP = new Vec3i(0, 1, 0);

    private IGameContainer gameContainer;
    private VoxelFaceParser voxelFaceParser;
    private final VoxelFaceCreator faceCreator;

    public VoxelChunkRebuilder(IGameContainer gameContainer, VoxelFaceParser voxelFaceParser, VoxelFaceCreator voxelFaceCreator) {
        this.gameContainer = gameContainer;
        this.voxelFaceParser = voxelFaceParser;
        this.faceCreator = voxelFaceCreator;
    }

    public void rebuild(VoxelChunk chunk, VoxelChunkHandler voxelChunkHandler) {
        BitSet topFaces = this.voxelFaceParser.parseFaces(TOP, chunk, voxelChunkHandler);
        Mesh topMesh = this.faceCreator.buildTopFaces(chunk.getChunkId(), topFaces);
        chunk.setMesh(topMesh);
        topMesh.updateBound();
        chunk.updateModelBound();
        chunk.clearRebuildFlag();
    }
}
