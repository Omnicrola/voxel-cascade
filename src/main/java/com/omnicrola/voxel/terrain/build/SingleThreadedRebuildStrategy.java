package com.omnicrola.voxel.terrain.build;

import com.omnicrola.voxel.debug.DebugTimer;
import com.omnicrola.voxel.terrain.IChunkRebuildStrategy;
import com.omnicrola.voxel.terrain.data.VoxelChunk;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Eric on 4/10/2016.
 */
public class SingleThreadedRebuildStrategy implements IChunkRebuildStrategy {

    private static final Logger LOGGER = Logger.getLogger(SingleThreadedRebuildStrategy.class.getName());

    public static final SingleThreadedRebuildStrategy INSTANCE = new SingleThreadedRebuildStrategy();
    private final DebugTimer debugTimer;
    private float totalChunks;
    private float chunksComplete;

    private SingleThreadedRebuildStrategy() {
        this.debugTimer = new DebugTimer();
    }

    @Override
    public void rebuild(List<VoxelChunk> chunksToRebuild, VoxelChunkRebuilder voxelChunkRebuilder) {
        this.totalChunks = chunksToRebuild.size();
        this.chunksComplete = 0;

        chunksToRebuild.forEach(c -> {
            this.debugTimer.reset();
            voxelChunkRebuilder.rebuild(c);
            this.chunksComplete++;
            logRebuildTime(c);
        });

    }

    @Override
    public float percentComplete() {
        return chunksComplete / totalChunks;
    }

    private void logRebuildTime(VoxelChunk chunk) {
        float elapsed = this.debugTimer.mark();
        LOGGER.log(Level.FINE, "Rebuilt chunk " + chunk.toString() + " in " + elapsed + "ms");
    }

}
