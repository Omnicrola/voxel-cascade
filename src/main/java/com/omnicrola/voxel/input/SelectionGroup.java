package com.omnicrola.voxel.input;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

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
    }

    public void orderStop() {
    }

    public void orderAttackTarget(Geometry geometry) {
    }

    public void orderAttackLocation(Vector3f vector3f) {

    }

    public String unitNames() {
        return this.selection.stream().map(s -> s.getName()).collect(Collectors.joining(", "));
    }
}
