package com.omnicrola.voxel.terrain;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.omnicrola.voxel.data.LevelData;
import com.omnicrola.voxel.data.Vec3i;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.jme.wrappers.IGeometryBuilder;

/**
 * Created by omnic on 1/16/2016.
 */
public class VoxelTerrainGenerator {
    public static Node load(LevelData levelData, IGameContainer gameContainer) {
        IGeometryBuilder geometryBuilder = gameContainer.getWorld().getBuilder();
        Node terrainRoot = new Node();
        Vec3i size = levelData.getTerrainSize();
        Vec3i offset = levelData.getTerrainOffset();
        int xSize = (size.getX() / 2) + offset.getX();
        int ySize = (size.getY() / 2) + offset.getY();
        int zSize = (size.getZ() / 2) + offset.getZ();
        for (int x = -xSize; x < xSize; x++) {
            for (int y = -10; y < -5; y++) {
                for (int z = -20; z < 20; z++) {
                    Geometry cube = geometryBuilder.cube(0.5f, ColorRGBA.randomColor());
                    cube.setLocalTranslation(x, y, z);
                    terrainRoot.attachChild(cube);
                }
            }
        }
        return terrainRoot;
    }
}
