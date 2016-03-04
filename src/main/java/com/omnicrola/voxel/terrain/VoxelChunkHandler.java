package com.omnicrola.voxel.terrain;

import com.jme3.math.Vector3f;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.debug.DebugTimer;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.terrain.build.FaceBuilder;
import com.omnicrola.voxel.terrain.build.OcclusionCalculatorBuilder;
import com.omnicrola.voxel.terrain.build.VoxelChunkRebuilder;
import com.omnicrola.voxel.terrain.data.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by omnic on 1/31/2016.
 */
public class VoxelChunkHandler {

    private final Map<ChunkId, VoxelChunk> chunks;
    private TerrainAdapter terrainAdapter;
    private VoxelChunkRebuilder voxelChunkRebuilder;
    private FaceBuilder faceBuilder;
    private final DebugTimer debugTimer;

    public VoxelChunkHandler(TerrainAdapter terrainAdapter, VoxelChunkRebuilder voxelChunkRebuilder) {
        this.terrainAdapter = terrainAdapter;
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
            VoxelChunk chunk = new VoxelChunk(chunkId, this.faceBuilder, this.terrainAdapter);
            Vector3f translate = new Vector3f(chunkId.getX(), chunkId.getY(), chunkId.getZ()).multLocal(GameConstants.CHUNK_SIZE);
            chunk.setLocalTranslation(translate);
            this.terrainAdapter.addChunk(chunk);
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
                    float elapsed = this.debugTimer.mark();
                    System.out.println("Rebuilt chunk " + c.toString() + " in " + elapsed + "ms");
                });
    }

    public void flagAllChunksForRebuild() {
        this.chunks.values().forEach(c -> c.flagForRebuild());
    }

    public Vector3f findLowestNonSolidVoxel(Vector3f location) {
        float x = location.getX();
        float y = 0;
        float z = location.getZ();
        boolean belowGround = true;
        while (belowGround) {
            Vec3i voxelPosition = Vec3i.round(x, y, z);
            if (isSolid(voxelPosition)) {
                y++;
            } else {
                belowGround = false;
            }
        }
        return new Vector3f(x, y, z);
    }

    public Optional<VoxelData> findHighestSolidVoxel(Vector3f location) {
        float x = location.getX();
        float y = 999;
        float z = location.getZ();
        boolean isEmpty = true;
        Vec3i voxelPosition = Vec3i.round(location);
        while (isEmpty) {
            voxelPosition = Vec3i.round(x, y, z);
            if (isSolid(voxelPosition)) {
                isEmpty = false;
            } else if (y < 0) {
                return Optional.empty();
            } else {
                y--;
            }
        }
        return Optional.of(getVoxelAt(voxelPosition));
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
        IVoxelType voxelType = this.terrainAdapter.lookupVoxelType(voxel);
        return new VoxelData(chunk, location, voxelType);
    }

    public void clearAll() {
        this.chunks.values().stream().forEach(c -> c.dispose());
        this.chunks.clear();
    }
}
