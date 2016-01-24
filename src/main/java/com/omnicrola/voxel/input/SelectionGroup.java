package com.omnicrola.voxel.input;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.ai.NavigationGridDistributor;
import com.omnicrola.voxel.entities.control.EntityAiController;
import com.omnicrola.voxel.entities.control.MotionGovernorControl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by omnic on 1/24/2016.
 */
public class SelectionGroup {
    private final NavigationGridDistributor gridDistributor;
    private List<Spatial> selection;

    public SelectionGroup(List<Spatial> selection) {
        this.selection = selection;
        this.gridDistributor = new NavigationGridDistributor(this);
    }

    public SelectionGroup() {
        this(new ArrayList<>());
    }

    public int count() {
        return this.selection.size();
    }

    public void orderMoveToLocation(Vector3f vector3f) {
        Iterator<Vector3f> navPoints = this.gridDistributor.distribute(vector3f);
        this.selection
                .stream()
                .map(u -> getAi(u))
                .forEach(ai -> ai.moveToLocation(navPoints.next()));
    }

    public void orderStop() {
        this.selection
                .stream()
                .map(u -> getAi(u))
                .forEach(ai -> ai.stop());
    }

    public void orderAttackTarget(Geometry target) {
        this.selection
                .stream()
                .map(u -> getAi(u))
                .forEach(ai -> ai.attackTarget(target));
    }

    public void orderAttackLocation(Vector3f location) {
        Iterator<Vector3f> navPoints = this.gridDistributor.distribute(location);
        this.selection
                .stream()
                .map(u -> getAi(u))
                .forEach(ai -> ai.attackLocation(navPoints.next()));
    }

    public String unitNames() {
        return this.selection.stream().map(s -> s.getName()).collect(Collectors.joining(", "));
    }

    private EntityAiController getAi(Spatial spatial) {
        return spatial.getControl(EntityAiController.class);
    }

    public float getLargestUnitSize() {
        Optional<Float> largest = this.selection
                .stream()
                .map(u -> getPersonalRadius(u))
                .sorted()
                .findFirst();
        if (largest.isPresent()) {
            return largest.get();
        } else {
            return 1.0f;
        }
    }

    private float getPersonalRadius(Spatial spatial) {
        MotionGovernorControl motionControl = spatial.getControl(MotionGovernorControl.class);
        return motionControl.getPersonalRadius();
    }
}
