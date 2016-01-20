package com.omnicrola.voxel.entities.control;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;

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
        RigidBodyControl rigidBodyControl = new RigidBodyControl(this.mass);
        spatial.addControl(rigidBodyControl);
    }
}
