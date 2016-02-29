package com.omnicrola.voxel.entities;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.control.unit.GroundVehicleControl;

/**
 * Created by Eric on 2/25/2016.
 */
public class Unit extends  AbstractGameEntity {

    public Unit(Spatial spatial) {
        super(spatial);
    }

    @Override
    public void setLocation(Vector3f location) {
        this.spatial.getControl(GroundVehicleControl.class).setPhysicsLocation(location);
    }
}
