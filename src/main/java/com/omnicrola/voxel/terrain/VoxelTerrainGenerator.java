package com.omnicrola.voxel.terrain;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.omnicrola.voxel.data.LevelData;
import com.omnicrola.voxel.data.Vec3iRead;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.jme.wrappers.IGamePhysics;
import com.omnicrola.voxel.jme.wrappers.IGeometryBuilder;

/**
 * Created by omnic on 1/16/2016.
 */
public class VoxelTerrainGenerator {
    public static Node load(LevelData levelData, IGameContainer gameContainer) {
        IGeometryBuilder geometryBuilder = gameContainer.world().build();
        IGamePhysics gamePhysics = gameContainer.physics();

        Node terrainRoot = new Node("Terrain");
        Vec3iRead size = levelData.getTerrainSize();
        Vec3iRead offset = levelData.getTerrainOffset();
        int xSize = size.getX() / 2;
        int ySize = size.getY() / 2;
        int zSize = size.getZ() / 2;
        for (int x = -xSize; x < xSize; x++) {
            for (int y = -ySize; y < ySize; y++) {
                for (int z = -zSize; z < zSize; z++) {
                    Geometry cube = geometryBuilder.cube(0.5f, ColorRGBA.randomColor());
                    cube.setName("voxel");
                    cube.setLocalTranslation(x + offset.getX(), y + offset.getY(), z + offset.getZ());
                    RigidBodyControl rigidBodyControl = new RigidBodyControl(0f);
                    cube.addControl(rigidBodyControl);
                    gamePhysics.addControl(rigidBodyControl);
                    terrainRoot.attachChild(cube);
                }
            }
        }
        return terrainRoot;
    }
}
