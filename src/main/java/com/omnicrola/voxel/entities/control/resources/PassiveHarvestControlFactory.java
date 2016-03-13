package com.omnicrola.voxel.entities.control.resources;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.entities.control.EntityControlAdapter;
import com.omnicrola.voxel.entities.control.IControlFactory;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by omnic on 3/13/2016.
 */
@XmlRootElement(name = "passive-harvest")
public class PassiveHarvestControlFactory implements IControlFactory {

    @XmlAttribute(name = "speed")
    private float speed;
    @XmlAttribute(name = "range")
    private float range;

    public PassiveHarvestControlFactory() {
    }

    public PassiveHarvestControlFactory(float speed, float range) {
        this.speed = speed;
        this.range = range;
    }

    @Override
    public void build(Spatial spatial, UnitDefinitionRepository unitDefinitionRepository, EntityControlAdapter entityControlAdapter) {
        PassiveHarvestControl passiveHarvestControl = new PassiveHarvestControl(this.speed, this.range, entityControlAdapter);
        spatial.addControl(passiveHarvestControl);
    }
}
