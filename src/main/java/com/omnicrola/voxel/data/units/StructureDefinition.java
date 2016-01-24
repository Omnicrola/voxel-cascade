package com.omnicrola.voxel.data.units;

import com.jme3.math.ColorRGBA;
import com.omnicrola.voxel.entities.control.IControlFactory;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by omnic on 1/24/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class StructureDefinition {
    @XmlAttribute(name="global-id",required = true)
    protected int globalId=-1;

    @XmlElement(name = "name", required = true)
    protected String name;

    @XmlElement(name = "description")
    protected String description;

    @XmlElement(name = "texture")
    protected String modelTexture;

    @XmlElement(name = "model")
    protected String modelGeometry;

    @XmlElement(name = "color", required = true)
    protected ColorRGBA color;

    @XmlElement(name = "hitpoints", required = true)
    protected float hitpoints;

    @XmlElementWrapper(name = "controls")
    @XmlAnyElement(lax = true)
    protected List<IControlFactory> controlFactories = new ArrayList<>();

    public int getGlobalId() {
        return globalId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getModelTexture() {
        return modelTexture;
    }

    public String getModelGeometry() {
        return modelGeometry;
    }

    public ColorRGBA getColor() {
        return color;
    }

    public float getHitpoints() {
        return hitpoints;
    }

    public List<IControlFactory> getControlFactories() {
        return controlFactories;
    }
}
