package com.omnicrola.voxel.entities.control.weapon;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.audio.AudioRepository;
import com.omnicrola.voxel.audio.IAudioPlayer;
import com.omnicrola.voxel.data.units.ProjectileDefinition;
import com.omnicrola.voxel.entities.Projectile;
import com.omnicrola.voxel.entities.control.EntityControlAdapter;
import com.omnicrola.voxel.entities.control.collision.CollisionController;
import com.omnicrola.voxel.entities.control.collision.ProjectileCollisionHandler;
import com.omnicrola.voxel.fx.VoxelShowerSpawnAction;
import com.omnicrola.voxel.jme.wrappers.impl.ParticleBuilder;
import com.omnicrola.voxel.world.WorldManager;

/**
 * Created by Eric on 2/9/2016.
 */
public abstract class ProjectileStrategy implements IProjectileStrategy {

    protected EntityControlAdapter entityControlAdapter;
    protected ProjectileDefinition projectileDefinition;

    public ProjectileStrategy(EntityControlAdapter entityControlAdapter, ProjectileDefinition projectileDefinition) {
        this.entityControlAdapter = entityControlAdapter;
        this.projectileDefinition = projectileDefinition;
    }

    protected void addCollisionControl(Projectile projectile) {
        Spatial spatial = projectile.getSpatial();
        WorldManager worldManager = this.entityControlAdapter.getWorldManager();
        ParticleBuilder particleBuilder = this.entityControlAdapter.getParticleBuilder();
        AudioRepository audioRepository = this.entityControlAdapter.getAudioRepository();

        ProjectileCollisionHandler projectileCollisionHandler = new ProjectileCollisionHandler(spatial, worldManager);
        projectileCollisionHandler.addDeathAction(new VoxelShowerSpawnAction(particleBuilder, 100));

        IAudioPlayer deathSound = audioRepository.getSoundPlayer(this.projectileDefinition.deathSound);
        projectileCollisionHandler.addDeathAction(new PlayAudioDeathAction(deathSound));
        spatial.addControl(new CollisionController(projectileCollisionHandler));
    }
}
