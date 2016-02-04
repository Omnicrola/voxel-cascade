package com.omnicrola.voxel.terrain;

import com.jme3.scene.Node;
import com.omnicrola.util.Vec3i;
import com.omnicrola.util.Vec3iRead;
import com.omnicrola.voxel.data.level.LevelDefinition;
import com.omnicrola.voxel.engine.MaterialRepository;
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
        QuadFactory quadFactory = new QuadFactory(new MaterialRepository(this.gameContainer.getAssetManager()));
        VoxelChunkRebuilder voxelChunkRebuilder = new VoxelChunkRebuilder(this.gameContainer, quadFactory);
        VoxelChunkHandler voxelChunkHandler = new VoxelChunkHandler(voxelChunkRebuilder);

        Vec3iRead size = levelData.getTerrainSize();
        for (int x = 0; x < size.getX(); x++) {
            for (int y = 0; y < size.getY(); y++) {
                for (int z = 0; z < size.getZ(); z++) {
                    VoxelType type = (Math.random() < 0.95) ? VoxelType.BLUE : VoxelType.EMPTY;
                    voxelChunkHandler.set(new Vec3i(x, y, z), type);
                }
            }
        }

        terrainRoot.addControl(new VoxelTerrainControl(voxelChunkHandler));
        return terrainRoot;
    }
}
