package com.omnicrola.voxel.debug;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResult;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.engine.states.WorldManagerState;

import java.util.Optional;

/**
 * Created by Eric on 2/5/2016.
 */
public class DebugTargetListener implements ActionListener {
    private VoxelGameEngine voxelGameEngine;

    public DebugTargetListener(VoxelGameEngine voxelGameEngine) {
        this.voxelGameEngine = voxelGameEngine;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (!isPressed) {
            findTerrain();
        }
    }

    private void findTerrain() {
        LevelManager levelManager = this.voxelGameEngine.getStateManager().getState(WorldManagerState.class).getLevelManager();
        LevelState currentLevelState = levelManager.getCurrentLevel();
        Optional<CollisionResult> terrainUnderCursor = currentLevelState.getWorldCursor().getTerrainPositionUnderCursor();
        if (terrainUnderCursor.isPresent()) {
            Geometry geometry = terrainUnderCursor.get().getGeometry();
            RigidBodyControl control = geometry.getControl(RigidBodyControl.class);
            Vector3f physicsLocation = control.getPhysicsLocation();
            System.out.println("world: " + geometry.getWorldTranslation() + " physics: " + physicsLocation);
        }
    }
}
