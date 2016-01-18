package com.omnicrola.voxel.entities.control;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.entities.ProjectileDefinition;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;

/**
 * Created by omnic on 1/17/2016.
 */
public class EntityAiControlFactory implements IControlFactory {
    @Override
    public void build(Spatial spatial, IGameWorld gameWorld) {
        MotionGovernorControl motionGovernor = new MotionGovernorControl();
        WeaponsController weaponsController = new WeaponsController(gameWorld, new ProjectileDefinition());
        EntityAiController entityAi = new EntityAiController(motionGovernor, weaponsController);
        spatial.addControl(motionGovernor);
        spatial.addControl(weaponsController);
        spatial.addControl(entityAi);

    }
}
