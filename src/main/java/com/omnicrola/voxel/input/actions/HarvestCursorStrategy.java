package com.omnicrola.voxel.input.actions;

import com.jme3.collision.CollisionResult;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.control.resources.VoxelHarvestTarget;
import com.omnicrola.voxel.input.GameMouseEvent;
import com.omnicrola.voxel.input.ICursorStrategy;
import com.omnicrola.voxel.input.IWorldCursor;
import com.omnicrola.voxel.input.SelectionGroup;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.terrain.data.VoxelData;
import com.omnicrola.voxel.terrain.highlight.ITerrainHighlighter;

import java.util.Optional;

/**
 * Created by Eric on 2/10/2016.
 */
public class HarvestCursorStrategy implements ICursorStrategy {
    private final ITerrainHighlighter terrainHighlighter;
    private ITerrainManager terrainManager;
    private IWorldCursor worldCursor;
    private final JmeCursor cursor2d;
    private final Geometry cursor3d;

    public HarvestCursorStrategy(ITerrainHighlighter terrainHighlighter,
                                 ITerrainManager terrainManager,
                                 IWorldCursor worldCursor,
                                 JmeCursor cursor2d,
                                 Geometry cursorNode) {
        this.terrainHighlighter = terrainHighlighter;
        this.terrainManager = terrainManager;
        this.worldCursor = worldCursor;
        this.cursor2d = cursor2d;
        this.cursor3d = cursorNode;
    }

    @Override
    public void executePrimary(GameMouseEvent mouseEvent, SelectionGroup currentSelection) {
        if (mouseEvent.isPressed()) {
            Optional<CollisionResult> terrainPositionUnderCursor = this.worldCursor.getTerrainPositionUnderCursor();
            if (terrainPositionUnderCursor.isPresent()) {
                this.terrainHighlighter.setVisible(true);
                Vector3f contactPoint = terrainPositionUnderCursor.get().getContactPoint();
                this.terrainHighlighter.setStart(contactPoint);
            }
        } else {
            Optional<CollisionResult> terrainPositionUnderCursor = this.worldCursor.getTerrainPositionUnderCursor();
            if (terrainPositionUnderCursor.isPresent()) {
                Vector3f contactPoint = terrainPositionUnderCursor.get().getContactPoint().subtract(0, 1, 0);
                VoxelData voxelData = this.terrainManager.getVoxelAt(contactPoint);
                VoxelHarvestTarget voxelHarvestTarget = new VoxelHarvestTarget(voxelData, contactPoint);
                currentSelection.orderHarvest(voxelHarvestTarget);
            }
            this.terrainHighlighter.setVisible(false);
            this.worldCursor.clearCursorStrategy();
            this.terrainHighlighter.clear();
        }
    }

    @Override
    public void executeSecondary(boolean isPressed, SelectionGroup currentSelection) {
        if (!isPressed) {
            this.worldCursor.clearCursorStrategy();
        }
    }

    @Override
    public JmeCursor get2DCursor() {
        return this.cursor2d;
    }

    @Override
    public Spatial get3dCursor() {
        return this.cursor3d;
    }
}
