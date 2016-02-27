package com.omnicrola.voxel.entities.control;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;

/**
 * Created by omnic on 1/31/2016.
 */
public class DeathControllerFactory implements IControlFactory {
    @Override
    public void build(Spatial spatial, UnitDefinitionRepository unitDefinitionRepository, EntityControlAdapter entityControlAdapter) {
        DeathController deathController = new DeathController(entityControlAdapter);
        spatial.addControl(deathController);
    }
}
