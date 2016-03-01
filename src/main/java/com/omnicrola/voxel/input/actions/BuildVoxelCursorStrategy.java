package com.omnicrola.voxel.input.actions;

import com.jme3.collision.CollisionResult;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.entities.commands.VoxelConstructionPackage;
import com.omnicrola.voxel.input.GameMouseEvent;
import com.omnicrola.voxel.input.ICursorStrategy;
import com.omnicrola.voxel.input.IWorldCursor;
import com.omnicrola.voxel.input.SelectionGroup;
import com.omnicrola.voxel.terrain.ITerrainManager;

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
    private IWorldCursor worldCursor;

    public BuildVoxelCursorStrategy(IWorldCursor worldCursor,
                                    byte voxelType,
                                    JmeCursor buildCursor,
                                    ITerrainManager terrainManager,
                                    Spatial placeholderVoxel) {
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
        if (!gameMouseEvent.isPressed()) {
            Optional<CollisionResult> terrainUnderCursor = this.worldCursor.getTerrainPositionUnderCursor();
            if (terrainUnderCursor.isPresent()) {
                buildAtMouseLocation(gameMouseEvent, currentSelection);
            }
        }
    }
    private void buildAtMouseLocation(GameMouseEvent gameMouseEvent, SelectionGroup currentSelection) {
        Vec3i snappedLocation = this.worldCursor.getSnappedLocation();
        this.placeholderVoxel.setLocalTranslation(snappedLocation.asVector3f());
        VoxelConstructionPackage voxelConstructionPackage = new VoxelConstructionPackage(
                this.terrainManager,
                this.voxelType,
                snappedLocation,
                this.placeholderVoxel);
        currentSelection.orderBuild(voxelConstructionPackage);
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
