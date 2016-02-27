package com.omnicrola.voxel.entities.control.old;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.entities.control.EntityControlAdapter;
import com.omnicrola.voxel.physics.GroundVehicleControl;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by omnic on 1/17/2016.
 */

@XmlRootElement(name = "PhysicsControl")
public class GroundVehicleControlFactory implements IControlFactory {
    private float mass;

    public GroundVehicleControlFactory(float mass) {
        this.mass = mass;
    }

    @Override
    public void build(Spatial spatial, UnitDefinitionRepository unitDefinitionRepository, EntityControlAdapter entityControlAdapter) {
        GroundVehicleControl tankVehicleControl = new GroundVehicleControl(gameContainer, this.mass);
        spatial.addControl(tankVehicleControl);
    }
}
