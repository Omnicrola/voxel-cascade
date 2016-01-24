package com.omnicrola.voxel.entities.control;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;
import com.omnicrola.voxel.physics.GroundVehicleControl;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by omnic on 1/17/2016.
 */

@XmlRootElement(name = "PhysicsControl")
public class PhysicsControlFactory implements IControlFactory {
    private float mass;

    public PhysicsControlFactory(float mass) {
        this.mass = mass;
    }

    @Override
    public void build(Spatial spatial, IGameWorld gameWorld) {
//        RigidBodyControl rigidBodyControl = new RigidBodyControl(this.mass);
        GroundVehicleControl tankVehicleControl = new GroundVehicleControl(this.mass);

        spatial.addControl(tankVehicleControl);
    }
}
