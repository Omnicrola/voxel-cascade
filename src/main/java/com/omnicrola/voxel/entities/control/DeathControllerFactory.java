package com.omnicrola.voxel.entities.control;

import com.jme3.scene.Spatial;

/**
 * Created by omnic on 1/31/2016.
 */
public class DeathControllerFactory implements IControlFactory {
    @Override
    public void build(Spatial spatial) {
        DeathController deathController = new DeathController();
        spatial.addControl(deathController);
    }
}
