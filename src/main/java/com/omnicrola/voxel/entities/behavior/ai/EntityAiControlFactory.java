package com.omnicrola.voxel.entities.behavior.ai;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.WeaponType;
import com.omnicrola.voxel.data.units.MovementDefinition;
import com.omnicrola.voxel.data.units.ProjectileDefinition;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.data.units.WeaponDefinition;
import com.omnicrola.voxel.entities.behavior.ai.pathing.VoxelAstarPathFinder;
import com.omnicrola.voxel.entities.control.EntityControlAdapter;
import com.omnicrola.voxel.entities.control.IControlFactory;
import com.omnicrola.voxel.entities.control.construction.BuildController;
import com.omnicrola.voxel.entities.control.move.EntityMotionControl;
import com.omnicrola.voxel.entities.control.weapon.*;
import com.omnicrola.voxel.entities.control.resources.ResourceHarvestController;

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
    public void build(Spatial spatial, UnitDefinitionRepository unitDefinitionRepository, EntityControlAdapter entityControlAdapter) {

        WeaponDefinition weaponDefinition = unitDefinitionRepository.getWeaponDefinition(this.weaponId);
        ProjectileDefinition projectileDefinition = unitDefinitionRepository.getProjectileDefinition(weaponDefinition.getProjectileId());

        EntityMotionControl motionGovernor = new EntityMotionControl(this.movementDefinition, entityControlAdapter);
        IProjectileStrategy projectileFactory = createProjectileFactory(entityControlAdapter, weaponDefinition, projectileDefinition);
        WeaponsController weaponsController = new WeaponsController(entityControlAdapter.getWorldManager(), weaponDefinition, this.projectileOffset, projectileFactory);
        TargetingController targetingController = new TargetingController(entityControlAdapter.getWorldManager());
        ResourceHarvestController resourceHarvester = new ResourceHarvestController(entityControlAdapter);
        BuildController buildController = new BuildController(entityControlAdapter);

        EntityAiController entityAi = buildAiController(
                motionGovernor,
                weaponsController,
                targetingController,
                resourceHarvester,
                buildController,
                entityControlAdapter);

        spatial.addControl(motionGovernor);
        spatial.addControl(targetingController);
        spatial.addControl(weaponsController);
        spatial.addControl(resourceHarvester);
        spatial.addControl(buildController);
        spatial.addControl(entityAi);
    }

    protected EntityAiController buildAiController(EntityMotionControl motionGovernor,
                                                   WeaponsController weaponsController,
                                                   TargetingController targetingController,
                                                   ResourceHarvestController resourceHarvester,
                                                   BuildController buildController,
                                                   EntityControlAdapter entityControlAdapter) {
        IAiState holdState = buildHoldState(targetingController, weaponsController, motionGovernor);
        AiAttackTargetState attackTargetState = new AiAttackTargetState(weaponsController, motionGovernor);
        AiBuildState buildState = new AiBuildState(motionGovernor, buildController);
        AiHarvestState harvestState = new AiHarvestState(resourceHarvester, motionGovernor);
        VoxelAstarPathFinder pathFinder = new VoxelAstarPathFinder(entityControlAdapter.getTerrainManager());
        AiMoveToLocationState moveState = new AiMoveToLocationState(motionGovernor, pathFinder, entityControlAdapter.getTerrainManager());
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
                                    EntityMotionControl motionGovernor) {
        return new AiHoldPositionState(targetingController, weaponController, motionGovernor);
    }

    private IProjectileStrategy createProjectileFactory(EntityControlAdapter entityControlAdapter, WeaponDefinition weaponDefinition, ProjectileDefinition projectileDefinition) {
        if (weaponDefinition.type().equals(WeaponType.PARABOLIC)) {
            return new ParabolicProjectileStrategy(entityControlAdapter, weaponDefinition, projectileDefinition);
        } else {
            return new LinearProjectileStrategy(entityControlAdapter, weaponDefinition, projectileDefinition);
        }
    }
}
