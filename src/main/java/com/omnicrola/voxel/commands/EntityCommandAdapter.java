package com.omnicrola.voxel.commands;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.behavior.ai.AiMoveToLocationState;
import com.omnicrola.voxel.entities.behavior.ai.EntityAiController;
import com.omnicrola.voxel.entities.behavior.ai.NavigationGridDistributor;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Eric on 3/19/2016.
 */
public class EntityCommandAdapter {

    NavigationGridDistributor gridDistributor;

    public void moveToLocation(Vector3f location, List<Spatial> selectedUnits) {
        Iterator<Vector3f> navPoints = this.gridDistributor.distribute(location, selectedUnits);
        getEntityAiControllerStream(selectedUnits)
                .forEach(ai -> ai.setState(AiMoveToLocationState.class)
                        .setTarget(navPoints.next()));
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
