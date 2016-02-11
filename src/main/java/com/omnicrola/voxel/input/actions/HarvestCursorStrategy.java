package com.omnicrola.voxel.input.actions;

import com.jme3.collision.CollisionResult;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.entities.resources.VoxelHarvestTarget;
import com.omnicrola.voxel.input.GameMouseEvent;
import com.omnicrola.voxel.input.ICursorStrategy;
import com.omnicrola.voxel.input.SelectionGroup;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.terrain.VoxelData;

import java.util.Optional;

/**
 * Created by Eric on 2/10/2016.
 */
public class HarvestCursorStrategy implements ICursorStrategy {
    private final IGameContainer gameContainer;
    private LevelState levelState;
    private final JmeCursor harvestCursor;
    private final Node empty3dCursor;

    public HarvestCursorStrategy(IGameContainer gameContainer, LevelState levelState, JmeCursor harvestCursor) {
        this.gameContainer = gameContainer;
        this.levelState = levelState;
        this.harvestCursor = harvestCursor;
        this.empty3dCursor = new Node();
    }

    @Override
    public void executePrimary(GameMouseEvent mouseEvent, SelectionGroup currentSelection) {
        if (!mouseEvent.isPressed()) {
            Optional<CollisionResult> terrainPositionUnderCursor = this.levelState.getWorldCursor().getTerrainPositionUnderCursor();
            if(terrainPositionUnderCursor.isPresent()){
                Vector3f contactPoint = terrainPositionUnderCursor.get().getContactPoint();
                VoxelData voxelData = this.gameContainer.world().getVoxelAt(contactPoint);
                VoxelHarvestTarget voxelHarvestTarget = new VoxelHarvestTarget(voxelData, contactPoint);
                currentSelection.orderHarvest(voxelHarvestTarget);
            }
            this.levelState.getWorldCursor().clearCursorStrategy();
        }
    }

    @Override
    public void executeSecondary(boolean isPressed, SelectionGroup currentSelection) {
        if (!isPressed) {
            this.levelState.getWorldCursor().clearCursorStrategy();
        }
    }

    @Override
    public JmeCursor get2DCursor() {
        return this.harvestCursor;
    }

    @Override
    public Spatial get3dCursor() {
        return this.empty3dCursor;
    }
}
