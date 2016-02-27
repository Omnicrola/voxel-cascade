package com.omnicrola.voxel.entities.control.old;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.VectorXmlTypeAdapter;
import com.omnicrola.voxel.data.WeaponType;
import com.omnicrola.voxel.data.units.ProjectileDefinition;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.data.units.WeaponDefinition;
import com.omnicrola.voxel.entities.behavior.ai.AiHoldPositionState;
import com.omnicrola.voxel.entities.behavior.ai.AiStateMap;
import com.omnicrola.voxel.entities.behavior.ai.EntityAiController;
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
    public void build(Spatial spatial, UnitDefinitionRepository unitDefinitionRepository) {
        WeaponDefinition weaponDefinition = unitDefinitionRepository.getWeaponDefinition(this.weaponId);
        ProjectileDefinition projectileDefinition = unitDefinitionRepository.getProjectileDefinition(weaponDefinition.getProjectileId());
        IGameWorld gameWorld = gameContainer.world();

        IProjectileStrategy projectileFactory = buildProjectileFactory(gameContainer, weaponDefinition, projectileDefinition);
        WeaponsController weaponsController = new WeaponsController(gameWorld, weaponDefinition, weaponOffset, projectileFactory);
        TargetingController targetingController = new TargetingController(gameWorld);

        AiHoldPositionState holdPositionState = new AiHoldPositionState(targetingController, weaponsController, NullMotionController.NO_OP);

        AiStateMap stateMap = new AiStateMap();
        stateMap.add(holdPositionState);

        EntityAiController entityAiController = new EntityAiController(stateMap, holdPositionState);

        spatial.addControl(entityAiController);
        spatial.addControl(weaponsController);
        spatial.addControl(targetingController);
    }

    private IProjectileStrategy buildProjectileFactory(IGameContainer gameContainer, WeaponDefinition weaponDefinition, ProjectileDefinition projectileDefinition) {
        if (weaponDefinition.type().equals(WeaponType.PARABOLIC)) {
            return new ParabolicProjectileStrategy(gameContainer, weaponDefinition, projectileDefinition);
        } else {
            return new LinearProjectileStrategy(gameContainer, weaponDefinition, projectileDefinition);
        }
    }
}
