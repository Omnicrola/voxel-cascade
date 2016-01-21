package com.omnicrola.voxel.entities.control;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.units.MovementDefinition;
import com.omnicrola.voxel.data.units.ProjectileDefinition;
import com.omnicrola.voxel.data.units.WeaponDefinition;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;

/**
 * Created by omnic on 1/17/2016.
 */

public class EntityAiControlFactory implements IControlFactory {

    private WeaponDefinition weaponDefinition;
    private final MovementDefinition movementDefinition;
    private ProjectileDefinition projectileDefinition;

    public EntityAiControlFactory(WeaponDefinition weaponDefinition,
                                  ProjectileDefinition projectileDefinition,
                                  MovementDefinition movementDefinition) {
        this.projectileDefinition = projectileDefinition;
        this.weaponDefinition = weaponDefinition;
        this.movementDefinition = movementDefinition;
    }

    @Override
    public void build(Spatial spatial, IGameWorld gameWorld) {
        MotionGovernorControl motionGovernor = new MotionGovernorControl(this.movementDefinition);
        WeaponsController weaponsController = new WeaponsController(gameWorld, this.weaponDefinition, this.projectileDefinition);
        EntityAiController entityAi = new EntityAiController(motionGovernor, weaponsController);
        spatial.addControl(motionGovernor);
        spatial.addControl(weaponsController);
        spatial.addControl(entityAi);

    }
}
