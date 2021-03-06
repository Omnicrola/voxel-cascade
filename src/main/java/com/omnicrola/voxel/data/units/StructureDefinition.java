package com.omnicrola.voxel.data.units;

import com.jme3.math.ColorRGBA;
import com.omnicrola.voxel.entities.commands.IEntityCommand;
import com.omnicrola.voxel.entities.control.DeathControllerFactory;
import com.omnicrola.voxel.entities.control.IControlFactory;
import com.omnicrola.voxel.entities.control.collision.CollisionControlFactory;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by omnic on 1/24/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class StructureDefinition {

    @XmlTransient
    public static final StructureDefinition NONE = new StructureDefinition();

    @XmlAttribute(name = "global-id", required = true)
    protected int globalId = -1;

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

    @XmlElement(name = "build-cost")
    public float buildCost;

    @XmlElement(name = "hitpoints", required = true)
    protected float hitpoints;

    @XmlElementWrapper(name = "commands")
    @XmlAnyElement(lax = true)
    protected List<IEntityCommand> commands = new ArrayList<>();

    @XmlElementWrapper(name = "build-targets")
    @XmlAnyElement(lax = true)
    protected List<IEntityCommand> buildCommands = new ArrayList<>();

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

    public String getTexture() {
        return modelTexture;
    }

    public String getModel() {
        return modelGeometry;
    }

    public ColorRGBA getColor() {
        return color;
    }

    public float getHitpoints() {
        return hitpoints;
    }

    public float getBuildCost() {
        return buildCost;
    }

    public List<IControlFactory> getControlFactories() {
        ArrayList<IControlFactory> controlFactories = new ArrayList<>(this.controlFactories);
        controlFactories.add(new CollisionControlFactory());
        controlFactories.add(new CommandControlFactory(this.commands, buildCommands));
        controlFactories.add(new DeathControllerFactory());
        return controlFactories;
    }
}
