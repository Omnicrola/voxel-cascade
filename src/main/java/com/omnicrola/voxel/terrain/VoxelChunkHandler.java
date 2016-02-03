package com.omnicrola.voxel.terrain;

import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.omnicrola.util.Vec3i;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by omnic on 1/31/2016.
 */
public class VoxelChunkHandler {

    private final Map<ChunkId, VoxelChunk> chunks;
    private VoxelChunkRebuilder voxelChunkRebuilder;
    private Node parentNode;

    public VoxelChunkHandler(VoxelChunkRebuilder voxelChunkRebuilder) {
        this.voxelChunkRebuilder = voxelChunkRebuilder;
        this.chunks = new HashMap<>();
    }

    public void create(Geometry geometry, Vec3i location) {
        ChunkId chunkId = ChunkId.fromGlobal(location);
        VoxelChunk voxelChunk = findChunk(chunkId);
        voxelChunk.set(location).to(geometry);
    }

    public void set(Vec3i location, IVoxelType voxelType) {
        ChunkId chunkId = ChunkId.fromGlobal(location);
        VoxelChunk chunk = findChunk(chunkId);
        chunk.set(location).to(voxelType);
    }

    public VoxelChunk getChunkContaining(Vec3i globalLocation) {
        ChunkId chunkId = ChunkId.fromGlobal(globalLocation);
        VoxelChunk voxelChunk = this.chunks.get(chunkId);
        if (voxelChunk == null) {
            return EmptyVoxelChunk.NO_OP;
        }
        return voxelChunk;
    }

    private VoxelChunk findChunk(ChunkId chunkId) {
        if (!this.chunks.containsKey(chunkId)) {
            VoxelChunk chunk = new VoxelChunk(chunkId);
            this.chunks.put(chunkId, chunk);
        }
        return this.chunks.get(chunkId);
    }

    public void update() {
        this.chunks.values()
                .stream()
                .filter(c -> c.needsRebuilt())
                .forEach(c -> {
                    this.voxelChunkRebuilder.rebuild(c);
                });
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
        this.chunks.forEach((id, c) -> parentNode.attachChild(c));
    }

    public void flagAllChunksForRebuild() {
        this.chunks.values().forEach(c -> c.flagForRebuild());
    }
}
