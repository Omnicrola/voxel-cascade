package com.omnicrola.voxel.entities.control;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.VectorXmlTypeAdapter;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.data.units.WeaponDefinition;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by omnic on 2/6/2016.
 */
@XmlRootElement(name = "weapon-control")
@XmlAccessorType(XmlAccessType.FIELD)
public class AutomatedWeaponControlFactory implements IControlFactory {

    @XmlAttribute(name = "weapon-id")
    protected int weaponId;

    @XmlElement(name = "weapon-offset")
    @XmlJavaTypeAdapter(VectorXmlTypeAdapter.class)
    protected Vector3f weaponOffset;

    @Override
    public void build(Spatial spatial, IGameContainer gameContainer, UnitDefinitionRepository unitDefinitionRepository) {
        WeaponDefinition weaponDefinition = unitDefinitionRepository.getWeaponDefinition(this.weaponId);
        IGameWorld gameWorld = gameContainer.world();
        WeaponsController weaponsController = new WeaponsController(gameWorld, weaponDefinition, weaponOffset);
        TargetingController targetingController = new TargetingController(gameWorld);
        spatial.addControl(weaponsController);
        spatial.addControl(targetingController);
    }
}
