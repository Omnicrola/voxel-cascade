package com.omnicrola.voxel.input;

import com.jme3.collision.CollisionResult;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by omnic on 1/16/2016.
 */
public class UserInteractionHandler {

    private final ArrayList<IUserInteractionObserver> observers;
    private LevelState currentLevelState;
    private IGameContainer gameContainer;
    private SelectionGroup currentlySelected;

    public UserInteractionHandler(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;
        this.currentlySelected = new SelectionGroup();
        this.observers = new ArrayList<>();
    }

    public void setLevel(LevelState level) {
        this.currentLevelState = level;
    }

    public void addSelectionListener(IUserInteractionObserver interactionObserver) {
        this.observers.add(interactionObserver);
    }

    public void clearSelection() {
        setCurrentSelection(null);
    }

    public void selectPoint() {
        Optional<CollisionResult> entityUnderCursor = getUnitUnderCursor();
        if (entityUnderCursor.isPresent()) {
            CollisionResult collisionResult = entityUnderCursor.get();
            Geometry selection = collisionResult.getGeometry();
            setCurrentSelection(selection);
        }
    }

    public void selectArea(ScreenRectangle screenRectangle) {
        List<Spatial> selection = this.gameContainer.world().selectAllUnitsIn(screenRectangle);
        this.currentlySelected = new SelectionGroup(selection);
        notifyObserversOfSelection();
    }

    public void orderSelectionToMove() {
        if (hasSelection()) {
            Optional<Vector3f> terrainLocation = getTerrainPointUnderCursor();
            if (terrainLocation.isPresent()) {
                this.currentlySelected.orderMoveToLocation(terrainLocation.get());
            }
        }
    }

    public void orderSelectionToStop() {
        if (hasSelection()) {
            this.currentlySelected.orderStop();
        }
    }

    public void orderSelectionToAttack() {
        if (hasSelection()) {
            Optional<CollisionResult> entityUnderCursor = getUnitUnderCursor();
            if (entityUnderCursor.isPresent()) {
                CollisionResult collisionResult = entityUnderCursor.get();
                this.currentlySelected.orderAttackTarget(collisionResult.getGeometry());
            } else {
                Optional<Vector3f> terrainPoint = getTerrainPointUnderCursor();
                if (terrainPoint.isPresent()) {
                    this.currentlySelected.orderAttackLocation(terrainPoint.get());
                }
            }
        }
    }

    private Optional<CollisionResult> getUnitUnderCursor() {
        return getWorldCursor().getUnitUnderCursor(this.currentLevelState.getUnits());
    }

    private Optional<Vector3f> getTerrainPointUnderCursor() {
        Optional<CollisionResult> entityUnderCursor = getWorldCursor().getTerrainUnderCursor(this.currentLevelState.getTerrain());
        if (entityUnderCursor.isPresent()) {
            return Optional.of(entityUnderCursor.get().getContactPoint());
        } else {
            return Optional.empty();
        }
    }

    private WorldCursor getWorldCursor() {
        return this.currentLevelState.getWorldCursor();
    }

    private void setCurrentSelection(Geometry geometry) {
        this.currentlySelected = new SelectionGroup(Arrays.asList(geometry));
        notifyObserversOfSelection();
    }

    private void notifyObserversOfSelection() {
        for (IUserInteractionObserver observer : this.observers) {
            observer.notifyNewSelection(this.currentlySelected);
        }
    }

    private boolean hasSelection() {
        return this.currentlySelected.count() > 0;
    }
}
