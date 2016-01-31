package com.omnicrola.voxel.terrain;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.omnicrola.util.Vec3iRead;
import com.omnicrola.voxel.data.level.LevelDefinition;
import com.omnicrola.voxel.engine.physics.CollisionController;
import com.omnicrola.voxel.engine.physics.TerrainCollisionHandler;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;
import com.omnicrola.voxel.jme.wrappers.IWorldBuilder;
import com.omnicrola.voxel.settings.EntityDataKeys;

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
        VoxelChunkHandler voxelChunkHandler = new VoxelChunkHandler();

        Vec3iRead size = levelData.getTerrainSize();
        Vec3iRead offset = levelData.getTerrainOffset();
        int xSize = size.getX() / 2;
        int ySize = size.getY() / 2;
        int zSize = size.getZ() / 2;
        for (int x = -xSize; x < xSize; x++) {
            for (int y = -ySize; y < ySize; y++) {
                for (int z = -zSize; z < zSize; z++) {
                    Geometry cube = createVoxelSpatial(gameWorld, geometryBuilder, offset, x, y, z);
                    voxelChunkHandler.create(cube, x, y, z);
                }
            }
        }
        terrainRoot.addControl(new VoxelTerrainControl(voxelChunkHandler));

        return terrainRoot;
    }

    private Geometry createVoxelSpatial(IGameWorld gameWorld, IWorldBuilder geometryBuilder, Vec3iRead offset, int x, int y, int z) {
        Geometry cube = geometryBuilder.terrainVoxel(0.5f, ColorRGBA.randomColor());
        cube.setName("voxel " + x + "," + y + "," + z);
        cube.setUserData(EntityDataKeys.IS_TERRAIN, true);
        cube.setLocalTranslation(x + offset.getX(), y + offset.getY(), z + offset.getZ());
        RigidBodyControl rigidBodyControl = new RigidBodyControl(0f);
        cube.addControl(rigidBodyControl);
        cube.addControl(new CollisionController(new TerrainCollisionHandler(cube, gameWorld)));
        return cube;
    }
}
