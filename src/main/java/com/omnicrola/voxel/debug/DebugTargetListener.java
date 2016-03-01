package com.omnicrola.voxel.debug;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResult;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.omnicrola.voxel.input.IWorldCursor;

import java.util.Optional;

/**
 * Created by Eric on 2/5/2016.
 */
public class DebugTargetListener implements ActionListener {

    private IWorldCursor worldCursor;

    public DebugTargetListener(IWorldCursor worldCursor) {
        this.worldCursor = worldCursor;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (!isPressed) {
            findTerrain();
        }
    }

    private void findTerrain() {
        Optional<CollisionResult> terrainUnderCursor = this.worldCursor.getTerrainPositionUnderCursor();
        if (terrainUnderCursor.isPresent()) {
            Geometry geometry = terrainUnderCursor.get().getGeometry();
            RigidBodyControl control = geometry.getControl(RigidBodyControl.class);
            Vector3f physicsLocation = control.getPhysicsLocation();
            System.out.println("world: " + geometry.getWorldTranslation() + " physics: " + physicsLocation);
        }
    }
}
