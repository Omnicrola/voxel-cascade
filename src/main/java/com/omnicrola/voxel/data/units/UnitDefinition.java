package com.omnicrola.voxel.data.units;

import com.jme3.math.ColorRGBA;
import com.omnicrola.voxel.entities.control.CollisionControlFactory;
import com.omnicrola.voxel.entities.control.EntityAiControlFactory;
import com.omnicrola.voxel.entities.control.IControlFactory;
import com.omnicrola.voxel.entities.control.PhysicsControlFactory;

import javax.xml.bind.annotation.*;
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

    @XmlElement(name="name",required = true)
    protected String name;

    @XmlElement(name="description")
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

    public List<IControlFactory> getControlFactories(DefinitionRepository repository) {
        WeaponDefinition weaponDefinition = repository.getWeaponDefinition(this.weaponId);
        ProjectileDefinition projectileDefinition = repository.getProjectileDefinition(weaponDefinition.getProjectileId());

        ArrayList<IControlFactory> iControlFactories = new ArrayList<>(this.controlFactories);
        iControlFactories.add(new PhysicsControlFactory(this.mass));
        iControlFactories.add(new CollisionControlFactory());
        iControlFactories.add(new EntityAiControlFactory(weaponDefinition, projectileDefinition, this.movementDefinition));
        return iControlFactories;
    }
}
