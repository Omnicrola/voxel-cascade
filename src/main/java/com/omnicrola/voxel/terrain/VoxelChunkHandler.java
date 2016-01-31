package com.omnicrola.voxel.terrain;

import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by omnic on 1/31/2016.
 */
public class VoxelChunkHandler {

    private final Map<ChunkId, VoxelChunk> chunks;

    public VoxelChunkHandler() {
        this.chunks = new HashMap<>();
    }

    public void create(Geometry geometry, int x, int y, int z) {
        ChunkId chunkId = ChunkId.fromGlobal(x, y, z);
        VoxelChunk voxelChunk = findChunk(chunkId);
        voxelChunk.set(x, y, z).to(geometry);
    }

    public VoxelChunk getChunkContaining(int x, int y, int z) {
        ChunkId chunkId = ChunkId.fromGlobal(x, y, z);
        return this.chunks.get(chunkId);
    }

    private VoxelChunk findChunk(ChunkId chunkId) {
        if (!this.chunks.containsKey(chunkId)) {
            this.chunks.put(chunkId, new VoxelChunk(chunkId));
        }
        return this.chunks.get(chunkId);
    }

    public void update(Node parentNode) {
        this.chunks.values()
                .stream()
                .filter(c -> c.isDirty())
                .forEach(c -> c.rebuild(parentNode));
    }
}
