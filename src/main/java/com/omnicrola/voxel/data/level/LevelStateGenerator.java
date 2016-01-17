package com.omnicrola.voxel.data.level;

import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.omnicrola.voxel.input.WorldCursor;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.terrain.VoxelTerrainGenerator;

/**
 * Created by omnic on 1/16/2016.
 */
public class LevelStateGenerator {
    public static LevelState create(LevelData levelData, IGameContainer gameContainer) {
        Node terrain = VoxelTerrainGenerator.load(levelData, gameContainer);
        WorldCursor worldCursor = createWorldCursor(gameContainer, terrain);
        Node entities = LevelEntityGenerator.create(levelData, gameContainer);
        DirectionalLight sun = createLights();

        return new LevelState(terrain, sun, entities, worldCursor, "Default Level");
    }

    private static WorldCursor createWorldCursor(IGameContainer gameContainer, Node terrain) {
        WorldCursor worldCursor = gameContainer.world().createCursor(terrain);
        return worldCursor;
    }

    private static DirectionalLight createLights() {
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-0.5f, -0.5f, -0.5f).normalizeLocal());
        return sun;
    }
}
