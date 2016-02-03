package com.omnicrola.voxel.terrain;

import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.jme.wrappers.IGamePhysics;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by omnic on 1/31/2016.
 */
public class VoxelChunkHandler {

    private final Map<ChunkId, VoxelChunk> chunks;
    private VoxelChunkRebuilder voxelChunkRebuilder;

    public VoxelChunkHandler(VoxelChunkRebuilder voxelChunkRebuilder) {
        this.voxelChunkRebuilder = voxelChunkRebuilder;
        this.chunks = new HashMap<>();
    }

    public void create(Geometry geometry, Vec3i location) {
        ChunkId chunkId = ChunkId.fromGlobal(location);
        VoxelChunk voxelChunk = findChunk(chunkId);
        voxelChunk.set(location).to(geometry);
        System.out.println("create: " + location);
    }

    public VoxelChunk getChunkContaining(Vec3i location) {
        ChunkId chunkId = ChunkId.fromGlobal(location);
        return this.chunks.get(chunkId);
    }

    private VoxelChunk findChunk(ChunkId chunkId) {
        if (!this.chunks.containsKey(chunkId)) {
            this.chunks.put(chunkId, new VoxelChunk(chunkId));
        }
        return this.chunks.get(chunkId);
    }

    public void update(Node parentNode, IGamePhysics gamePhysics) {
        this.chunks.values()
                .stream()
                .filter(c -> c.needsRebuilt())
                .forEach(c -> {
                    this.voxelChunkRebuilder.rebuild(c, this);
                    c.rebuild(parentNode, gamePhysics);
                });
    }

    public void flagAllChunksForRebuild() {
        this.chunks.values().forEach(c -> c.flagForRebuild());
    }
}
