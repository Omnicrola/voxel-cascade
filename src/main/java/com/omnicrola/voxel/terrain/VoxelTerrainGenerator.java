package com.omnicrola.voxel.terrain;

import com.jme3.material.Material;
import com.jme3.scene.Node;
import com.omnicrola.util.Vec3i;
import com.omnicrola.util.Vec3iRead;
import com.omnicrola.voxel.data.level.LevelDefinition;
import com.omnicrola.voxel.fx.MaterialToken;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;

/**
 * Created by omnic on 1/16/2016.
 */
public class VoxelTerrainGenerator {

    private IGameContainer gameContainer;

    public VoxelTerrainGenerator(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;
    }

    public Node load(LevelDefinition levelData) {
        Node terrainRoot = new Node("Terrain");
        Material chunkMaterial = this.gameContainer.world().build().material(MaterialToken.TERRAIN_VOXEL);
        VoxelChunkRebuilder voxelChunkRebuilder = new VoxelChunkRebuilder(this.gameContainer, chunkMaterial);
        VoxelChunkHandler voxelChunkHandler = new VoxelChunkHandler(voxelChunkRebuilder);

        Vec3iRead size = levelData.getTerrainSize();
        for (int x = 0; x < size.getX(); x++) {
            for (int y = 0; y < size.getY(); y++) {
                for (int z = 0; z < size.getZ(); z++) {
                    voxelChunkHandler.set(new Vec3i(x, y, z), VoxelType.SOLID);
                }
            }
        }

        terrainRoot.addControl(new VoxelTerrainControl(voxelChunkHandler));
        return terrainRoot;
    }
}
