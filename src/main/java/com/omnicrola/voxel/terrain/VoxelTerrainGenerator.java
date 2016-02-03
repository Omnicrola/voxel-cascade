package com.omnicrola.voxel.terrain;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.omnicrola.util.Vec3i;
import com.omnicrola.util.Vec3iRead;
import com.omnicrola.voxel.data.level.LevelDefinition;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;
import com.omnicrola.voxel.jme.wrappers.IWorldBuilder;

/**
 * Created by omnic on 1/16/2016.
 */
public class VoxelTerrainGenerator {

    private IGameContainer gameContainer;

    public VoxelTerrainGenerator(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;
    }

    public Node load(LevelDefinition levelData) {
        IGameWorld gameWorld = this.gameContainer.world();
        IWorldBuilder geometryBuilder = gameWorld.build();

        Node terrainRoot = new Node("Terrain");
        VoxelChunkHandler voxelChunkHandler = new VoxelChunkHandler(new VoxelChunkRebuilder(new VoxelFaceParser()));

        Vec3iRead size = levelData.getTerrainSize();
        Vec3iRead offset = levelData.getTerrainOffset();
        int xSize = size.getX() / 2;
        int ySize = size.getY() / 2;
        int zSize = size.getZ() / 2;
        for (int x = -xSize; x < xSize; x++) {
            for (int y = -ySize; y < ySize; y++) {
                for (int z = -zSize; z < zSize; z++) {
                    Geometry cube = createVoxelSpatial(gameWorld, geometryBuilder, offset, x, y, z);
                    voxelChunkHandler.create(cube, new Vec3i(x, y, z));
                }
            }
        }

        terrainRoot.addControl(new VoxelTerrainControl(voxelChunkHandler));

        return terrainRoot;
    }

    private Geometry createVoxelSpatial(IGameWorld gameWorld, IWorldBuilder geometryBuilder, Vec3iRead offset, int x, int y, int z) {
        Geometry voxel = geometryBuilder.terrainVoxel(ColorRGBA.randomColor());
        voxel.setName("voxel " + x + "," + y + "," + z);
        return voxel;
    }
}
