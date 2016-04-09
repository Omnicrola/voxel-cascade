package com.omnicrola.voxel.terrain;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.debug.DebugTimer;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.terrain.build.FaceBuilder;
import com.omnicrola.voxel.terrain.build.VoxelChunkRebuilder;
import com.omnicrola.voxel.terrain.build.occlusion.OcclusionCalculatorBuilder;
import com.omnicrola.voxel.terrain.data.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by omnic on 1/31/2016.
 */
public class VoxelChunkHandler extends Node {

    private static final Logger LOGGER = Logger.getLogger(VoxelChunkHandler.class.getName());

    private final Map<ChunkId, VoxelChunk> chunks;
    private VoxelChunkRebuilder voxelChunkRebuilder;
    private FaceBuilder faceBuilder;
    private final DebugTimer debugTimer;
    private VoxelTypeLibrary voxelTypeLibrary;

    public VoxelChunkHandler(VoxelTypeLibrary voxelTypeLibrary, VoxelChunkRebuilder voxelChunkRebuilder) {
        this.voxelTypeLibrary = voxelTypeLibrary;
        this.voxelChunkRebuilder = voxelChunkRebuilder;
        this.faceBuilder = new FaceBuilder(this, OcclusionCalculatorBuilder.build(this));
        this.chunks = new HashMap<>();
        this.debugTimer = new DebugTimer();
    }

    public void set(Vec3i location, byte voxelType) {
        ChunkId chunkId = ChunkId.fromGlobal(location);
        VoxelChunk chunk = findChunk(chunkId);
        chunk.set(location, voxelType);
    }

    public void setResource(Vec3i location, float amount) {
        VoxelChunk chunk = getChunkContaining(location);
        chunk.setResourceGlobal(location, amount);
    }

    public void setHalf(Vec3i location, boolean isHalf) {
        VoxelChunk chunk = getChunkContaining(location);
        chunk.setHalfGlobal(location, isHalf);
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
            VoxelChunk chunk = new VoxelChunk(chunkId, this.faceBuilder);
            Vector3f translate = new Vector3f(chunkId.getX(), chunkId.getY(), chunkId.getZ()).multLocal(GameConstants.CHUNK_SIZE);
            chunk.setLocalTranslation(translate);
            this.attachChild(chunk);
            this.chunks.put(chunkId, chunk);
        }
        return this.chunks.get(chunkId);
    }

    public void update() {
        this.chunks.values()
                .stream()
                .filter(c -> c.needsRebuilt())
                .forEach(c -> {
                    this.debugTimer.reset();
                    this.voxelChunkRebuilder.rebuild(c);
                    logRebuildTime(c);
                });
    }

    private void logRebuildTime(VoxelChunk chunk) {
        float elapsed = this.debugTimer.mark();
        LOGGER.log(Level.FINE, "Rebuilt chunk " + chunk.toString() + " in " + elapsed + "ms");
    }

    public void flagAllChunksForRebuild() {
        this.chunks.values().forEach(c -> c.flagForRebuild());
    }

    public Optional<VoxelData> findLowestEmptyVoxel(Vector3f location) {
        return raycastFromAbove(location, p -> isSolid(p), v -> getVoxelAt(v.translate(0, 1, 0)));
    }

    public Optional<VoxelData> findHighestSolidVoxel(Vector3f location) {
        return raycastFromAbove(location, p -> isSolid(p), v -> getVoxelAt(v));
    }

    private Optional<VoxelData> raycastFromAbove(Vector3f location,
                                                 Predicate<Vec3i> predicate,
                                                 Function<Vec3i, VoxelData> mapper) {
        float x = location.getX();
        float y = GameConstants.MAXIMUM_VOXEL_HEIGHT;
        float z = location.getZ();
        boolean hasNotBeenFound = true;
        Vec3i voxelPosition = Vec3i.floor(location);
        while (hasNotBeenFound) {
            voxelPosition = Vec3i.floor(x, y, z);
            if (predicate.test(voxelPosition)) {
                hasNotBeenFound = false;
            } else if (y < GameConstants.MINIMUM_VOXEL_HEIGHT) {
                return Optional.empty();
            } else {
                y--;
            }
        }
        return Optional.of(mapper.apply(voxelPosition));
    }

    private boolean isSolid(Vec3i worldPosition) {
        byte voxel = this.getChunkContaining(worldPosition).getVoxelGlobal(worldPosition);
        return voxel != VoxelType.EMPTY.uniqueId();
    }

    public boolean isVoxelSolidAt(Vec3i location) {
        boolean solid = isSolid(location);
        return solid;
    }

    public VoxelData getVoxelAt(Vec3i location) {
        VoxelChunk chunk = getChunkContaining(location);
        byte voxel = chunk.getVoxelGlobal(location);
        boolean isHalf = chunk.isHalfGlobal(location);
        IVoxelType voxelType = this.voxelTypeLibrary.lookup(voxel);
        return new VoxelData(chunk, location, voxelType, isHalf);
    }

    public void clearAll() {
        this.chunks.values().stream().forEach(c -> c.dispose());
        this.chunks.clear();
    }
}
