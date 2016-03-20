package com.omnicrola.voxel.input.actions;

import com.jme3.collision.CollisionResult;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.commands.OrderAttackLocationCommand;
import com.omnicrola.voxel.commands.OrderAttackTargetCommand;
import com.omnicrola.voxel.commands.ICommandProcessor;
import com.omnicrola.voxel.input.GameMouseEvent;
import com.omnicrola.voxel.input.ICursorStrategy;
import com.omnicrola.voxel.input.IWorldCursor;
import com.omnicrola.voxel.input.SelectionGroup;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.util.VoxelUtil;

import java.util.Optional;

/**
 * Created by omnic on 1/24/2016.
 */
public class AttackCursorStrategy implements ICursorStrategy {
    private final Node empty3dCursor;
    private JmeCursor cursor2d;
    private ICommandProcessor commandProcessor;
    private IWorldCursor worldCursor;

    public AttackCursorStrategy(IWorldCursor worldCursor, JmeCursor cursor2d, ICommandProcessor commandProcessor) {
        this.worldCursor = worldCursor;
        this.cursor2d = cursor2d;
        this.commandProcessor = commandProcessor;
        this.empty3dCursor = new Node();
    }

    @Override
    public JmeCursor get2DCursor() {
        return this.cursor2d;
    }

    @Override
    public Spatial get3dCursor() {
        return this.empty3dCursor;
    }

    @Override
    public void executePrimary(GameMouseEvent gameMouseEvent, SelectionGroup currentSelection) {
        if (!gameMouseEvent.isPressed()) {
            attackTarget(currentSelection);
        }
    }

    @Override
    public void executeSecondary(boolean isPressed, SelectionGroup currentSelection) {
        if (!isPressed) {
            attackTarget(currentSelection);
        }
    }

    private void attackTarget(SelectionGroup currentSelection) {
        Optional<CollisionResult> unitUnderCursor = worldCursor.getUnitUnderCursor();
        if (unitUnderCursor.isPresent()) {
            issueAttackTargetCommand(currentSelection, unitUnderCursor);
//            currentSelection.orderAttackTarget(unitUnderCursor.get().getGeometry());
        } else {
            Optional<CollisionResult> terrainUnderCursor = worldCursor.getTerrainPositionUnderCursor();
            if (terrainUnderCursor.isPresent()) {
                issueAttackLocationCommand(currentSelection, terrainUnderCursor);
//                currentSelection.orderAttackLocation(terrainLocation);
            }
        }
        worldCursor.clearCursorStrategy();
    }

    private void issueAttackLocationCommand(SelectionGroup currentSelection, Optional<CollisionResult> terrainUnderCursor) {
        Vector3f terrainLocation = terrainUnderCursor.get().getContactPoint();
        int[] unitIds = currentSelection.getUnitIds();
        OrderAttackLocationCommand attackLocationCommand = new OrderAttackLocationCommand(terrainLocation, unitIds);
        this.commandProcessor.addCommand(attackLocationCommand);
    }

    private void issueAttackTargetCommand(SelectionGroup currentSelection, Optional<CollisionResult> unitUnderCursor) {
        Geometry geometry = unitUnderCursor.get().getGeometry();
        int targetId = VoxelUtil.integerData(geometry, EntityDataKeys.WORLD_ID);
        int[] unitIds = currentSelection.getUnitIds();
        OrderAttackTargetCommand attackTargetCommand = new OrderAttackTargetCommand(targetId, unitIds);
        this.commandProcessor.addCommand(attackTargetCommand);
    }
}
