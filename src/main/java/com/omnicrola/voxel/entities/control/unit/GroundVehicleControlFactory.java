package com.omnicrola.voxel.entities.control.unit;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.entities.control.EntityControlAdapter;
import com.omnicrola.voxel.entities.control.IControlFactory;
import com.omnicrola.voxel.terrain.ITerrainManager;

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
        ITerrainManager terrainManager = entityControlAdapter.getTerrainManager();
        GroundVehicleControl tankVehicleControl = new GroundVehicleControl(terrainManager);
        spatial.addControl(tankVehicleControl);
    }
}
