package com.omnicrola.voxel.input.actions;

import com.jme3.collision.CollisionResult;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.entities.commands.VoxelConstructionPackage;
import com.omnicrola.voxel.input.GameMouseEvent;
import com.omnicrola.voxel.input.ICursorStrategy;
import com.omnicrola.voxel.input.SelectionGroup;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.terrain.IVoxelType;

import java.util.Optional;

/**
 * Created by omnic on 1/31/2016.
 */
public class BuildVoxelCursorStrategy implements ICursorStrategy {
    private final IGameContainer gameContainer;
    private final LevelState levelState;
    private final IVoxelType voxelType;
    private final JmeCursor buildCursor;
    private final Node cursor3d;

    public BuildVoxelCursorStrategy(IGameContainer gameContainer,
                                    LevelState levelState,
                                    IVoxelType voxelType,
                                    JmeCursor buildCursor,
                                    Geometry ghostVoxel) {
        this.gameContainer = gameContainer;
        this.levelState = levelState;
        this.voxelType = voxelType;
        this.buildCursor = buildCursor;
        this.cursor3d = new Node();
        this.cursor3d.attachChild(ghostVoxel);
    }

    @Override
    public void executePrimary(GameMouseEvent gameMouseEvent, SelectionGroup currentSelection) {
        if (!gameMouseEvent.isPressed()) {
            Optional<CollisionResult> terrainUnderCursor = this.levelState.getWorldCursor().getTerrainPositionUnderCursor();
            if (terrainUnderCursor.isPresent()) {
                buildAtMouseLocation(gameMouseEvent, currentSelection);
            }
        }
    }

    private Geometry placeGhostVoxel(Vec3i snappedLocation) {
        Geometry ghostVoxel = this.gameContainer.world().build().terrainVoxel(new ColorRGBA(0f, 1f, 0f, 0.1f));
        ghostVoxel.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Additive);
        this.gameContainer.world().attach(ghostVoxel);
        ghostVoxel.setLocalTranslation(snappedLocation.asVector3f().add(0.5f, 0.5f, 0.5f));
        return ghostVoxel;
    }

    private void buildAtMouseLocation(GameMouseEvent gameMouseEvent, SelectionGroup currentSelection) {
        Vec3i snappedLocation = this.levelState.getWorldCursor().getSnappedLocation();
        Geometry ghostVoxel = placeGhostVoxel(snappedLocation);
        VoxelConstructionPackage voxelConstructionPackage = new VoxelConstructionPackage(this.voxelType, snappedLocation, ghostVoxel);
        currentSelection.orderBuild(voxelConstructionPackage);
        if (!gameMouseEvent.isMultiSelecting()) {
            this.levelState.getWorldCursor().clearCursorStrategy();
        }
    }

    @Override
    public void executeSecondary(boolean isPressed, SelectionGroup currentSelection) {
        this.levelState.getWorldCursor().clearCursorStrategy();
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
