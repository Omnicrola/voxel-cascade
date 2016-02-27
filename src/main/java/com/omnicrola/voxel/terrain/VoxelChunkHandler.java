package com.omnicrola.voxel.terrain;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.terrain.build.FaceBuilder;
import com.omnicrola.voxel.terrain.build.OcclusionCalculatorBuilder;
import com.omnicrola.voxel.terrain.build.VoxelChunkRebuilder;
import com.omnicrola.voxel.terrain.data.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by omnic on 1/31/2016.
 */
public class VoxelChunkHandler {

    private final Map<ChunkId, VoxelChunk> chunks;
    private VoxelTypeLibrary voxelTypeLibrary;
    private VoxelChunkRebuilder voxelChunkRebuilder;
    private Node parentNode;
    private FaceBuilder faceBuilder;

    public VoxelChunkHandler(VoxelTypeLibrary voxelTypeLibrary, VoxelChunkRebuilder voxelChunkRebuilder) {
        this.voxelTypeLibrary = voxelTypeLibrary;
        this.voxelChunkRebuilder = voxelChunkRebuilder;
        this.faceBuilder = new FaceBuilder(this, OcclusionCalculatorBuilder.build(this));
        this.chunks = new HashMap<>();
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
            VoxelChunk chunk = new VoxelChunk(chunkId, this.faceBuilder);
            Vector3f translate = new Vector3f(chunkId.getX(), chunkId.getY(), chunkId.getZ()).multLocal(16);
            chunk.setLocalTranslation(translate);
            if (this.parentNode != null) {
                this.parentNode.attachChild(chunk);
            }
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

    private boolean isSolid(Vec3i worldPosition) {
        byte voxel = this.getChunkContaining(worldPosition).getVoxelGlobal(worldPosition);
        if (voxel == VoxelType.EMPTY.uniqueId()) {
            return false;
        }
        return true;
    }

    public boolean isVoxelSolidAt(Vec3i location) {
        boolean solid = isSolid(location);
        return solid;
    }

    public VoxelData getVoxelAt(Vec3i location) {
        VoxelChunk chunk = getChunkContaining(location);
        byte voxel = chunk.getVoxelGlobal(location);
        IVoxelType voxelType = this.voxelTypeLibrary.lookup(voxel);
        return new VoxelData(chunk, location, voxelType);
    }


    public void clearAll() {
        this.chunks.values().stream().forEach(c -> c.dispose());
        this.chunks.clear();
    }
}
