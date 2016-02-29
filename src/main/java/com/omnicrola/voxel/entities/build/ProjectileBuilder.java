package com.omnicrola.voxel.entities.build;

import com.jme3.material.Material;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.units.ProjectileDefinition;
import com.omnicrola.voxel.engine.MaterialRepository;
import com.omnicrola.voxel.entities.Projectile;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.world.build.WorldBuilderToolbox;

/**
 * Created by Eric on 2/27/2016.
 */
public class ProjectileBuilder {
    private WorldBuilderToolbox toolbox;
    private MaterialRepository materialRepository;

    public ProjectileBuilder(WorldBuilderToolbox toolbox, MaterialRepository materialRepository) {
        this.toolbox = toolbox;
        this.materialRepository = materialRepository;
    }

    public Projectile build(Spatial emittingEntity, ProjectileDefinition projectileDefinition) {

        Spatial spatial = toolbox.getModel(projectileDefinition.getModel());
        Material material = materialRepository.createMaterial(projectileDefinition.getTexture());
        spatial.setMaterial(material);

        spatial.setUserData(EntityDataKeys.IS_PROJECTILE, true);
        spatial.setUserData(EntityDataKeys.IS_COLLIDABLE, true);
        spatial.setUserData(EntityDataKeys.PROJECTILE_DAMAGE, projectileDefinition.getDamage());
        spatial.setUserData(EntityDataKeys.TEAM_DATA, emittingEntity.getUserData(EntityDataKeys.TEAM_DATA));
        spatial.setUserData(EntityDataKeys.PROJECTILE_EMITTING_ENTITY, emittingEntity);

        return new Projectile(spatial);
    }
}
