package com.omnicrola.voxel.input.actions;

import com.jme3.collision.CollisionResult;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.input.GameMouseEvent;
import com.omnicrola.voxel.input.ICursorStrategy;
import com.omnicrola.voxel.input.SelectionGroup;
import com.omnicrola.voxel.input.WorldCursor;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.physics.GroundVehicleControl;

import java.util.Optional;

/**
 * Created by Eric on 1/30/2016.
 */
public class BuildUnitStrategy implements ICursorStrategy {

    private final IGameContainer gameContainer;
    private final LevelState levelState;
    private final int unitId;
    private final JmeCursor buildCursor;
    private Spatial exampleBuildTarget;

    public BuildUnitStrategy(IGameContainer gameContainer, LevelState levelState, int unitId, JmeCursor buildCursor, Spatial exampleBuildTarget) {
        this.gameContainer = gameContainer;
        this.levelState = levelState;
        this.unitId = unitId;
        this.buildCursor = buildCursor;
        this.exampleBuildTarget = exampleBuildTarget;
    }

    @Override
    public void executePrimary(GameMouseEvent gameMouseEvent, SelectionGroup currentSelection) {
        WorldCursor worldCursor = this.levelState.getWorldCursor();
        Optional<CollisionResult> terrainUnderCursor = worldCursor.getTerrainUnderCursor(this.levelState.getTerrain());
        if (terrainUnderCursor.isPresent()) {
            Spatial unit = this.gameContainer.world().build().unit(this.unitId, levelState.getPlayerTeam());
            Vector3f location = terrainUnderCursor.get().getContactPoint();
            unit.getControl(GroundVehicleControl.class).setPhysicsLocation(location.addLocal(0,1,0));
            this.levelState.addUnit(unit);
            worldCursor.clearCursorStrategy();
        }
    }

    @Override
    public void executeSecondary(boolean isPressed, SelectionGroup currentSelection) {
    }

    @Override
    public JmeCursor get2DCursor() {
        return this.buildCursor;
    }

    @Override
    public Spatial get3dCursor() {
        return this.exampleBuildTarget;
    }
}
