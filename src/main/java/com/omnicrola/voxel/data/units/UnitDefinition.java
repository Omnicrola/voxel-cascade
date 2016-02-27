package com.omnicrola.voxel.data.units;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.omnicrola.voxel.data.VectorXmlTypeAdapter;
import com.omnicrola.voxel.entities.behavior.ai.EntityAiControlFactory;
import com.omnicrola.voxel.entities.commands.IEntityCommand;
import com.omnicrola.voxel.entities.control.collision.CollisionControlFactory;
import com.omnicrola.voxel.entities.control.DeathControllerFactory;
import com.omnicrola.voxel.entities.control.unit.GroundVehicleControlFactory;
import com.omnicrola.voxel.entities.control.IControlFactory;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by omnic on 1/16/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class UnitDefinition {

    @XmlTransient
    public static final UnitDefinition NONE = new UnitDefinition();

    @XmlAttribute(name = "global-id", required = true)
    protected int globalId = -1;

    @XmlAttribute(name = "weapon-id")
    protected int weaponId;

    @XmlElement(name = "name", required = true)
    protected String name;

    @XmlElement(name = "description")
    protected String description;

    @XmlElement(name = "color", required = true)
    protected ColorRGBA color;

    @XmlElement(name = "texture")
    protected String modelTexture;

    @XmlElement(name = "model")
    protected String modelGeometry;

    @XmlElement(name = "hitpoints", required = true)
    protected float hitpoints;

    @XmlElement(name = "mass", required = true)
    protected float mass;

    @XmlElement(name = "movement", required = true)
    protected MovementDefinition movementDefinition;

    @XmlElementWrapper(name = "controls")
    @XmlAnyElement(lax = true)
    protected List<IControlFactory> controlFactories = new ArrayList<>();

    @XmlElementWrapper(name = "commands")
    @XmlAnyElement(lax = true)
    protected List<IEntityCommand> commands = new ArrayList<>();

    @XmlElementWrapper(name = "build-targets")
    @XmlAnyElement(lax = true)
    protected List<IEntityCommand> buildCommands = new ArrayList<>();

    @XmlElement(name = "weapon-offset")
    @XmlJavaTypeAdapter(VectorXmlTypeAdapter.class)
    protected Vector3f weaponEmissionOffset = new Vector3f(0, 1, 0);

    public UnitDefinition() {
    }

    public UnitDefinition(ColorRGBA color) {
        this.color = color;
    }

    public int getId() {
        return globalId;
    }

    public ColorRGBA getColor() {
        return color;
    }

    public String getModel() {
        return modelGeometry;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getHitpoints() {
        return hitpoints;
    }

    public String getTexture() {
        return modelTexture;
    }

    public List<IControlFactory> getControlFactories() {
        ArrayList<IControlFactory> controlFactories = new ArrayList<>(this.controlFactories);
        controlFactories.add(new GroundVehicleControlFactory(this.mass));
        controlFactories.add(new CollisionControlFactory());
        controlFactories.add(new CommandControlFactory(this.commands, this.buildCommands));
        controlFactories.add(new EntityAiControlFactory(this.weaponEmissionOffset, this.movementDefinition, this.weaponId));
        controlFactories.add(new DeathControllerFactory());
        return controlFactories;
    }
}
