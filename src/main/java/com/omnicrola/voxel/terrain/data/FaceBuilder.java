package com.omnicrola.voxel.terrain.data;

import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.terrain.IVoxelType;
import com.omnicrola.voxel.terrain.VoxelChunkHandler;

import java.util.BitSet;

/**
 * Created by Eric on 2/13/2016.
 */
public class FaceBuilder {

    private VoxelChunkHandler voxelChunkHandler;

    public FaceBuilder(VoxelChunkHandler voxelChunkHandler) {
        this.voxelChunkHandler = voxelChunkHandler;
    }

    public VoxelFace build(Vec3i globalLocation, int side) {
        BitSet bitSet = new BitSet(8);
        bitSet.set(0, isSolid(globalLocation, -1, 1, -1));
        bitSet.set(1, isSolid(globalLocation, -1, 1, 0));
        bitSet.set(2, isSolid(globalLocation, -1, 1, 1));

        bitSet.set(3, isSolid(globalLocation, 0, 1, -1));
        bitSet.set(4, isSolid(globalLocation, 0, 1, 1));

        bitSet.set(5, isSolid(globalLocation, 1, 1, -1));
        bitSet.set(6, isSolid(globalLocation, 1, 1, 0));
        bitSet.set(7, isSolid(globalLocation, 1, 1, 1));
        IVoxelType type = this.voxelChunkHandler.getVoxelAt(globalLocation);
        VoxelFace voxelFace = new VoxelFace(type, side, bitSet);
        return voxelFace;
    }

    private boolean isSolid(Vec3i globalLocation, int x, int y, int z) {
        return this.voxelChunkHandler.isVoxelSolidAt(globalLocation.translate(x, y, z));
    }
}
