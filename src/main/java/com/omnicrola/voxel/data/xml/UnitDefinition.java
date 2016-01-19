package com.omnicrola.voxel.data.xml;

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
    private int globalId = -1;

    @XmlElement(name = "color", required = true)
    private ColorRGBA color;

    @XmlElement(name = "texture")
    private String modelTexture = "Textures/test.png";

    @XmlElement(name = "model")
    private String modelGeometry = "Models/test.obj";

    @XmlElement(name = "hitpoints", required = true)
    private float hitpoints;

    @XmlElement(name = "mass", required = true)
    private float mass;

    @XmlElement(name = "weapon-id")
    private int weaponId;

    @XmlElement(name = "movement")
    private MovementDefinition movementDefinition;

    @XmlElementWrapper(name = "controls")
    @XmlElement(name = "control")
    private List<IControlFactory> controlFactories = new ArrayList<>();

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
