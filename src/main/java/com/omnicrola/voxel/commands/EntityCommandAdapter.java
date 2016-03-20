package com.omnicrola.voxel.commands;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.behavior.ai.*;
import com.omnicrola.voxel.entities.control.resources.VoxelHarvestTarget;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Eric on 3/19/2016.
 */
public class EntityCommandAdapter {

    NavigationGridDistributor gridDistributor;

    public void orderMoveToLocation(Vector3f location, List<Spatial> selectedUnits) {
        Iterator<Vector3f> navPoints = this.gridDistributor.distribute(location, selectedUnits);
        getEntityAiControllerStream(selectedUnits)
                .forEach(ai -> ai.setState(AiMoveToLocationState.class)
                        .setTarget(navPoints.next()));
    }

    public void orderStop(List<Spatial> units) {
        getEntityAiControllerStream(units).forEach(ai -> ai.setState(AiStopState.class));
    }

    public void orderAttackLocation(List<Spatial> units, Vector3f location) {
        Iterator<Vector3f> navPoints = this.gridDistributor.distribute(location, units);
        getEntityAiControllerStream(units).forEach(ai -> ai.setState(AiMoveToLocationState.class).setTarget(navPoints.next()));
    }

    public void orderAttackTarget(List<Spatial> units, Spatial target) {
        getEntityAiControllerStream(units).forEach(ai -> ai.setState(AiAttackTargetState.class).setTarget(target));
    }

    public void orderHarvest(List<Spatial> units, VoxelHarvestTarget voxelHarvestTarget) {
        getEntityAiControllerStream(units)
                .forEach(ai -> ai.setState(AiHarvestState.class)
                        .setTarget(voxelHarvestTarget));
    }

    private Stream<EntityAiController> getEntityAiControllerStream(List<Spatial> selectedUnits) {
        return selectedUnits
                .stream()
                .map(u -> getAi(u))
                .filter(ai -> ai != null);
    }

    private EntityAiController getAi(Spatial spatial) {
        return spatial.getControl(EntityAiController.class);
    }


}
