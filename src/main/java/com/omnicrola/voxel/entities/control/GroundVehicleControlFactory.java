package com.omnicrola.voxel.entities.control;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;
import com.omnicrola.voxel.physics.GroundVehicleControl;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by omnic on 1/17/2016.
 */

@XmlRootElement(name = "PhysicsControl")
public class GroundVehicleControlFactory implements IControlFactory {
    private float mass;
    private IGameWorld gameWorld;

    public GroundVehicleControlFactory(IGameWorld gameWorld, float mass) {
        this.mass = mass;
        this.gameWorld = gameWorld;
    }

    @Override
    public void build(Spatial spatial) {
        GroundVehicleControl tankVehicleControl = new GroundVehicleControl(this.gameWorld, this.mass);
        spatial.addControl(tankVehicleControl);
    }
}
