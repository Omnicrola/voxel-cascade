package com.omnicrola.voxel.input.actions;

import com.jme3.collision.CollisionResult;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.commands.ICommandProcessor;
import com.omnicrola.voxel.commands.OrderMoveToLocationCommand;
import com.omnicrola.voxel.input.GameMouseEvent;
import com.omnicrola.voxel.input.ICursorStrategy;
import com.omnicrola.voxel.input.IWorldCursor;
import com.omnicrola.voxel.input.SelectionGroup;

import java.util.Optional;

/**
 * Created by omnic on 1/24/2016.
 */
public class MoveSelectedUnitsStrategy implements ICursorStrategy {

    private final Node empty3dCursor;
    protected IWorldCursor worldCursor;
    private JmeCursor cursor2d;
    private ICommandProcessor worldCommandProcessor;

    public MoveSelectedUnitsStrategy(IWorldCursor worldCursor,
                                     JmeCursor cursor2d,
                                     ICommandProcessor worldCommandProcessor) {
        this.worldCursor = worldCursor;
        this.cursor2d = cursor2d;
        this.worldCommandProcessor = worldCommandProcessor;
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
            moveToLocation(currentSelection);
            this.worldCursor.clearCursorStrategy();
        }
    }

    @Override
    public void executeSecondary(boolean isPressed, SelectionGroup currentSelection) {
        if (!isPressed) {
            moveToLocation(currentSelection);
            this.worldCursor.clearCursorStrategy();
        }
    }

    protected void moveToLocation(SelectionGroup currentSelection) {
        Optional<CollisionResult> terrainUnderCursor = this.worldCursor.getTerrainPositionUnderCursor();
        if (terrainUnderCursor.isPresent()) {
            Vector3f location = terrainUnderCursor.get().getContactPoint();
            OrderMoveToLocationCommand moveUnitsCommand = new OrderMoveToLocationCommand(location, currentSelection.getUnitIds());
            this.worldCommandProcessor.addCommand(moveUnitsCommand);
//            currentSelection.orderMoveToLocation(location);
        }
    }
}
