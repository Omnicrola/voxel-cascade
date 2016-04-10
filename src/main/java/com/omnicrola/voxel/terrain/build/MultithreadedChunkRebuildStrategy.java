package com.omnicrola.voxel.terrain.build;

import com.omnicrola.voxel.terrain.IChunkRebuildStrategy;
import com.omnicrola.voxel.terrain.data.VoxelChunk;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Eric on 4/10/2016.
 */
public class MultithreadedChunkRebuildStrategy implements IChunkRebuildStrategy {
    public static final MultithreadedChunkRebuildStrategy INSTANCE = new MultithreadedChunkRebuildStrategy();
    private float totalChunks;
    private volatile int chunksComplete;

    private MultithreadedChunkRebuildStrategy() {
    }

    @Override
    public void rebuild(List<VoxelChunk> chunksToRebuild, VoxelChunkRebuilder voxelChunkRebuilder) {
        this.totalChunks = chunksToRebuild.size();
        this.chunksComplete = 0;

        chunksToRebuild.parallelStream().forEach(chunk -> {
            long start = System.nanoTime();
            voxelChunkRebuilder.rebuild(chunk);
            float elapsed = (System.nanoTime() - start) / 1_000_000f;
            this.chunksComplete++;
            Logger logger = Logger.getLogger(MultithreadedChunkRebuildStrategy.class.getName());
            logger.log(Level.FINE, "Rebuilt chunk in " + elapsed + "ms");
        });
    }

    @Override
    public float percentComplete() {
        return chunksComplete / totalChunks;
    }
}
