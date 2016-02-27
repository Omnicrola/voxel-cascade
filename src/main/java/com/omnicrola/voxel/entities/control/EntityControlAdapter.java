package com.omnicrola.voxel.entities.control;

import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.entities.build.EffectsBuilder;
import com.omnicrola.voxel.entities.build.ProjectileBuilder;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.world.WorldManager;

/**
 * Created by Eric on 2/27/2016.
 */
public class EntityControlAdapter {
    private EffectsBuilder effectsBuilder;
    private WorldManager worldManager;
    private ProjectileBuilder projectileBuilder;
    private ITerrainManager terrainManager;

    public EffectsBuilder getEffectsBuilder() {
        return this.effectsBuilder;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public LevelState getCurrentLevel() {
        return null;
    }

    public ProjectileBuilder getProjectileBuilder() {
        return projectileBuilder;
    }

    public ITerrainManager getTerrainManager() {
        return terrainManager;
    }
}
