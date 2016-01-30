package com.omnicrola.voxel.entities.control;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.physics.GroundVehicleControl;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by omnic on 1/17/2016.
 */

@XmlRootElement(name = "PhysicsControl")
public class UnitPhysicsControlFactory implements IControlFactory {
    private float mass;

    public UnitPhysicsControlFactory(float mass) {
        this.mass = mass;
    }

    @Override
    public void build(Spatial spatial) {
        GroundVehicleControl tankVehicleControl = new GroundVehicleControl(this.mass);
        spatial.addControl(tankVehicleControl);
    }
}
