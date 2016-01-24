package com.omnicrola.voxel.entities.control;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.units.MovementDefinition;
import com.omnicrola.voxel.data.units.WeaponDefinition;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;

/**
 * Created by omnic on 1/17/2016.
 */

public class EntityAiControlFactory implements IControlFactory {

    private WeaponDefinition weaponDefinition;
    private final MovementDefinition movementDefinition;
    private int projectileId;
    private Vector3f projectileOffset;

    public EntityAiControlFactory(WeaponDefinition weaponDefinition,
                                  int projectileId,
                                  Vector3f projectileOffset,
                                  MovementDefinition movementDefinition) {
        this.weaponDefinition = weaponDefinition;
        this.projectileId = projectileId;
        this.projectileOffset = projectileOffset;
        this.movementDefinition = movementDefinition;
    }

    @Override
    public void build(Spatial spatial, IGameWorld gameWorld) {
        MotionGovernorControl motionGovernor = new MotionGovernorControl(this.movementDefinition);
        WeaponsController weaponsController = new WeaponsController(gameWorld, this.weaponDefinition, this.projectileOffset, this.projectileId);
        TargetingController targetingController = new TargetingController(gameWorld);
        EntityAiController entityAi = new EntityAiController(motionGovernor, weaponsController, targetingController);
        spatial.addControl(motionGovernor);
        spatial.addControl(targetingController);
        spatial.addControl(weaponsController);
        spatial.addControl(entityAi);
    }
}
