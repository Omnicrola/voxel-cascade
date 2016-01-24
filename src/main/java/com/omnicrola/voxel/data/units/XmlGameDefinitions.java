package com.omnicrola.voxel.data.units;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 1/18/2016.
 */
@XmlRootElement(name = "GameDefinitions")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlGameDefinitions {

    @XmlElementWrapper(name = "units")
    @XmlElement(name = "unit")
    protected List<UnitDefinition> units = new ArrayList<>();

    @XmlElementWrapper(name = "weapons")
    @XmlElement(name = "weapon")
    protected List<WeaponDefinition> weapons = new ArrayList<>();

    @XmlElementWrapper(name = "projectiles")
    @XmlElement(name = "projectile")
    protected List<ProjectileDefinition> projectiles = new ArrayList<>();

    @XmlElementWrapper(name = "structures")
    @XmlElement(name = "structure")
    public List<StructureDefinition> structures;
}
