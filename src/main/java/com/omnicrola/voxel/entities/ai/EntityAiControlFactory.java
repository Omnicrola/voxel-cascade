package com.omnicrola.voxel.entities.ai;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.WeaponType;
import com.omnicrola.voxel.data.units.MovementDefinition;
import com.omnicrola.voxel.data.units.ProjectileDefinition;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.data.units.WeaponDefinition;
import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.entities.control.*;
import com.omnicrola.voxel.entities.resources.ResourceHarvestController;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;

/**
 * Created by omnic on 1/17/2016.
 */

public class EntityAiControlFactory implements IControlFactory {

    private final MovementDefinition movementDefinition;
    private int weaponId;
    private Vector3f projectileOffset;

    public EntityAiControlFactory(Vector3f projectileOffset,
                                  MovementDefinition movementDefinition,
                                  int weaponId) {
        this.projectileOffset = projectileOffset;
        this.movementDefinition = movementDefinition;
        this.weaponId = weaponId;
    }

    @Override
    public void build(Spatial spatial, IGameContainer gameContainer, UnitDefinitionRepository unitDefinitionRepository) {
        IGameWorld gameWorld = gameContainer.world();
        WeaponDefinition weaponDefinition = unitDefinitionRepository.getWeaponDefinition(this.weaponId);
        ProjectileDefinition projectileDefinition = unitDefinitionRepository.getProjectileDefinition(weaponDefinition.getProjectileId());
        LevelManager currentLevelState = gameContainer.getState(LevelManager.class);

        MotionGovernorControl motionGovernor = new MotionGovernorControl(this.movementDefinition);
        IProjectileStrategy projectileFactory = createProjectileFactory(gameContainer, weaponDefinition, projectileDefinition);
        WeaponsController weaponsController = new WeaponsController(gameContainer.world(), weaponDefinition, this.projectileOffset, projectileFactory);
        TargetingController targetingController = new TargetingController(gameWorld);
        ResourceHarvestController resourceHarvester = new ResourceHarvestController(currentLevelState, gameContainer);
        BuildController buildController = new BuildController(gameContainer, currentLevelState);

        EntityAiController entityAi = buildAiController(
                motionGovernor,
                weaponsController,
                targetingController,
                resourceHarvester,
                buildController);

        spatial.addControl(motionGovernor);
        spatial.addControl(targetingController);
        spatial.addControl(weaponsController);
        spatial.addControl(resourceHarvester);
        spatial.addControl(buildController);
        spatial.addControl(entityAi);
    }

    protected EntityAiController buildAiController(MotionGovernorControl motionGovernor,
                                                 WeaponsController weaponsController,
                                                 TargetingController targetingController,
                                                 ResourceHarvestController resourceHarvester,
                                                 BuildController buildController) {
        IAiState holdState = buildHoldState(targetingController, weaponsController, motionGovernor);
        AiAttackTargetState attackTargetState = new AiAttackTargetState(weaponsController, motionGovernor);
        AiBuildState buildState = new AiBuildState(motionGovernor, buildController);
        AiHarvestState harvestState = new AiHarvestState(resourceHarvester, motionGovernor);
        AiMoveToLocationState moveState = new AiMoveToLocationState(motionGovernor);
        AiStopState stopState = new AiStopState();

        AiStateMap states = new AiStateMap();
        states.add(holdState);
        states.add(attackTargetState);
        states.add(buildState);
        states.add(harvestState);
        states.add(moveState);
        states.add(stopState);
        return new EntityAiController(states, holdState);
    }

    private IAiState buildHoldState(TargetingController targetingController,
                                    WeaponsController weaponController,
                                    MotionGovernorControl motionGovernor) {
        return new AiHoldPositionState(targetingController, weaponController, motionGovernor);
    }

    private IProjectileStrategy createProjectileFactory(IGameContainer gameContainer, WeaponDefinition weaponDefinition, ProjectileDefinition projectileDefinition) {
        if (weaponDefinition.type().equals(WeaponType.PARABOLIC)) {
            return new ParabolicProjectileStrategy(gameContainer, weaponDefinition, projectileDefinition);
        } else {
            return new LinearProjectileStrategy(gameContainer, weaponDefinition, projectileDefinition);
        }
    }
}
