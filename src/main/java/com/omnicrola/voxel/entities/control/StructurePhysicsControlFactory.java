package com.omnicrola.voxel.entities.control;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;

/**
 * Created by omnic on 1/24/2016.
 */
public class StructurePhysicsControlFactory implements IControlFactory {

    private static final float STATIC_MASS = 0f;

    @Override
    public void build(Spatial spatial, UnitDefinitionRepository unitDefinitionRepository, EntityControlAdapter entityControlAdapter) {
        RigidBodyControl rigidBodyControl = new RigidBodyControl(STATIC_MASS);
        spatial.addControl(rigidBodyControl);
    }
}
