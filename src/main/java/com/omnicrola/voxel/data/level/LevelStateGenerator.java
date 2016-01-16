package com.omnicrola.voxel.data.level;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.omnicrola.voxel.data.LevelData;
import com.omnicrola.voxel.engine.input.WorldCursor;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.terrain.VoxelTerrainGenerator;

/**
 * Created by omnic on 1/16/2016.
 */
public class LevelStateGenerator {
    public static LevelState create(LevelData levelData, IGameContainer gameContainer) {
        Node terrain = VoxelTerrainGenerator.load(levelData, gameContainer);

        WorldCursor worldCursor = gameContainer.world().createCursor(terrain);
        Geometry cursorCube = gameContainer.world().build().cube(0.25f, ColorRGBA.Blue);
        worldCursor.attachChild(cursorCube);

        return new LevelState(terrain, worldCursor, "Default Level");
    }
}
