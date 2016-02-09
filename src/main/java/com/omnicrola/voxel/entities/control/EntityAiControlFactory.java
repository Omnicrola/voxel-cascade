package com.omnicrola.voxel.entities.control;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.WeaponType;
import com.omnicrola.voxel.data.units.MovementDefinition;
import com.omnicrola.voxel.data.units.ProjectileDefinition;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.data.units.WeaponDefinition;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;

/**
 * Created by omnic on 1/17/2016.
 */

public class EntityAiControlFactory implements IControlFactory {

    private final MovementDefinition movementDefinition;
    private int weaponId;
    private Vector3f projectileOffset;

    public EntityAiControlFactory(Vector3f projectileOffset,
                                  MovementDefinition movementDefinition, int weaponId) {
        this.projectileOffset = projectileOffset;
        this.movementDefinition = movementDefinition;
        this.weaponId = weaponId;
    }

    @Override
    public void build(Spatial spatial, IGameContainer gameContainer, UnitDefinitionRepository unitDefinitionRepository) {
        IGameWorld gameWorld = gameContainer.world();
        WeaponDefinition weaponDefinition = unitDefinitionRepository.getWeaponDefinition(this.weaponId);
        ProjectileDefinition projectileDefinition = unitDefinitionRepository.getProjectileDefinition(weaponDefinition.getProjectileId());

        MotionGovernorControl motionGovernor = new MotionGovernorControl(this.movementDefinition);
        IProjectileStrategy projectileFactory = createProjectileFactory(gameContainer, weaponDefinition, projectileDefinition);
        WeaponsController weaponsController = new WeaponsController(gameContainer.world(), weaponDefinition, this.projectileOffset, projectileFactory);
        TargetingController targetingController = new TargetingController(gameWorld);
        EntityAiController entityAi = new EntityAiController(motionGovernor, weaponsController, targetingController);

        spatial.addControl(motionGovernor);
        spatial.addControl(targetingController);
        spatial.addControl(weaponsController);
        spatial.addControl(entityAi);
    }

    private IProjectileStrategy createProjectileFactory(IGameContainer gameContainer, WeaponDefinition weaponDefinition, ProjectileDefinition projectileDefinition) {
        if (weaponDefinition.type().equals(WeaponType.PARABOLIC)) {
            return new ParabolicProjectileStrategy();
        } else {
            return new LinearProjectileStrategy(gameContainer, weaponDefinition, projectileDefinition);
        }
    }
}
