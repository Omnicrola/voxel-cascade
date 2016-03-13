package com.omnicrola.voxel.entities.control.move;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.VectorXmlTypeAdapter;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.entities.control.EntityControlAdapter;
import com.omnicrola.voxel.entities.control.IControlFactory;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by omnic on 3/13/2016.
 */
@XmlRootElement(name = "rotation-control")
public class RotationControlFactory implements IControlFactory {

    @XmlJavaTypeAdapter(VectorXmlTypeAdapter.class)
    @XmlElement(name = "axis")
    private Vector3f rotationAxis;

    @XmlAttribute(name = "speed")
    private float rotationAmount;

    public RotationControlFactory() {
    }

    public RotationControlFactory(Vector3f rotationAxis, float rotationAmount) {
        this.rotationAxis = rotationAxis;
        this.rotationAmount = rotationAmount;
    }

    @Override
    public void build(Spatial spatial, UnitDefinitionRepository unitDefinitionRepository, EntityControlAdapter entityControlAdapter) {
        RotationControl rotationControl = new RotationControl(this.rotationAxis, this.rotationAmount);
        spatial.addControl(rotationControl);
    }
}
