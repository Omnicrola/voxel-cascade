package com.omnicrola.voxel.entities.control;

import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.entities.build.ProjectileBuilder;
import com.omnicrola.voxel.jme.wrappers.impl.ParticleBuilder;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.world.WorldManager;

/**
 * Created by Eric on 2/27/2016.
 */
public class EntityControlAdapter {
    private ParticleBuilder particleBuilder;
    private WorldManager worldManager;
    private ProjectileBuilder projectileBuilder;
    private ITerrainManager terrainManager;
    private LevelManager levelManager;

    public EntityControlAdapter(ParticleBuilder particleBuilder,
                                WorldManager worldManager,
                                ProjectileBuilder projectileBuilder,
                                ITerrainManager terrainManager,
                                LevelManager levelManager) {
        this.particleBuilder = particleBuilder;
        this.worldManager = worldManager;
        this.projectileBuilder = projectileBuilder;
        this.terrainManager = terrainManager;
        this.levelManager = levelManager;
    }

    public ParticleBuilder getParticleBuilder() {
        return this.particleBuilder;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public LevelState getCurrentLevel() {
        return this.levelManager.getCurrentLevel();
    }

    public ProjectileBuilder getProjectileBuilder() {
        return projectileBuilder;
    }

    public ITerrainManager getTerrainManager() {
        return terrainManager;
    }
}
