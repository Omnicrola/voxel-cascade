package com.omnicrola.voxel.input.actions;

import com.jme3.collision.CollisionResult;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.entities.control.construction.VoxelConstructionPackage;
import com.omnicrola.voxel.entities.control.resources.VoxelQueue;
import com.omnicrola.voxel.input.GameMouseEvent;
import com.omnicrola.voxel.input.ICursorStrategy;
import com.omnicrola.voxel.input.IWorldCursor;
import com.omnicrola.voxel.input.SelectionGroup;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.terrain.highlight.ITerrainHighlighter;

import java.util.Optional;

/**
 * Created by omnic on 1/31/2016.
 */
public class BuildVoxelCursorStrategy implements ICursorStrategy {
    private byte voxelType;
    private final JmeCursor buildCursor;
    private final ITerrainManager terrainManager;
    private final Spatial placeholderVoxel;
    private final Node cursor3d;
    private ITerrainHighlighter terrainHighlighter;
    private IWorldCursor worldCursor;
    private Vector3f startLocation;

    public BuildVoxelCursorStrategy(ITerrainHighlighter terrainHighlighter,
                                    IWorldCursor worldCursor,
                                    byte voxelType,
                                    JmeCursor buildCursor,
                                    ITerrainManager terrainManager,
                                    Spatial placeholderVoxel) {
        this.terrainHighlighter = terrainHighlighter;
        this.worldCursor = worldCursor;
        this.voxelType = voxelType;
        this.buildCursor = buildCursor;
        this.terrainManager = terrainManager;
        this.placeholderVoxel = placeholderVoxel;
        this.cursor3d = new Node();
        this.cursor3d.attachChild(placeholderVoxel);
    }

    @Override
    public void executePrimary(GameMouseEvent gameMouseEvent, SelectionGroup currentSelection) {
        if (gameMouseEvent.isPressed()) {
            highlightTerrain();
        } else {
            Optional<CollisionResult> terrainUnderCursor = this.worldCursor.getTerrainPositionUnderCursor();
            if (terrainUnderCursor.isPresent()) {
                buildAtMouseLocation(gameMouseEvent, currentSelection);
            }
        }
    }

    private void highlightTerrain() {
        Optional<CollisionResult> terrainUnderCursor = this.worldCursor.getTerrainPositionUnderCursor();
        if (terrainUnderCursor.isPresent()) {
            this.startLocation = terrainUnderCursor.get().getContactPoint();
            this.terrainHighlighter.setVisible(true);
            this.terrainHighlighter.setStart(this.startLocation);
        }
    }

    private void buildAtMouseLocation(GameMouseEvent gameMouseEvent, SelectionGroup currentSelection) {
        Vec3i snappedLocation = this.worldCursor.getSnappedLocation();
        this.placeholderVoxel.setLocalTranslation(snappedLocation.asVector3f());

        VoxelQueue voxelQueue = this.terrainHighlighter.getSelection(snappedLocation.asVector3f());
        VoxelConstructionPackage voxelConstructionPackage = new VoxelConstructionPackage(
                this.terrainManager,
                this.voxelType,
                voxelQueue);

        currentSelection.orderBuild(voxelConstructionPackage);
        this.terrainHighlighter.setVisible(false);
        this.terrainHighlighter.clear();
        if (!gameMouseEvent.isMultiSelecting()) {
            this.worldCursor.clearCursorStrategy();
        }
    }

    @Override
    public void executeSecondary(boolean isPressed, SelectionGroup currentSelection) {
        this.worldCursor.clearCursorStrategy();
    }

    @Override
    public JmeCursor get2DCursor() {
        return this.buildCursor;
    }

    @Override
    public Spatial get3dCursor() {
        return this.cursor3d;
    }
}
