package com.omnicrola.voxel.input;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.control.EntityAiController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by omnic on 1/24/2016.
 */
public class SelectionGroup {
    private List<Spatial> selection;

    public SelectionGroup(List<Spatial> selection) {
        this.selection = selection;
    }

    public SelectionGroup() {
        this.selection = new ArrayList<>();
    }

    public int count() {
        return this.selection.size();
    }

    public void orderMoveToLocation(Vector3f vector3f) {
        this.selection
                .stream()
                .map(u -> getAi(u))
                .forEach(ai -> ai.moveToLocation(vector3f));
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
        this.selection
                .stream()
                .map(u -> getAi(u))
                .forEach(ai -> ai.attackLocation(location));
    }

    public String unitNames() {
        return this.selection.stream().map(s -> s.getName()).collect(Collectors.joining(", "));
    }

    private EntityAiController getAi(Spatial spatial) {
        return spatial.getControl(EntityAiController.class);
    }
}
