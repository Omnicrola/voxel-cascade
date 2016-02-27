package com.omnicrola.voxel.entities.control.old;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;

/**
 * Created by omnic on 1/31/2016.
 */
public class DeathControllerFactory implements IControlFactory {
    @Override
    public void build(Spatial spatial, UnitDefinitionRepository unitDefinitionRepository) {
        DeathController deathController = new DeathController(gameContainer);
        spatial.addControl(deathController);
    }
}
