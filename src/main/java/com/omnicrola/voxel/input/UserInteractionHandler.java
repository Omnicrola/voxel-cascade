package com.omnicrola.voxel.input;

import com.jme3.collision.CollisionResult;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.entities.control.EntityAiController;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.settings.EntityDataKeys;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by omnic on 1/16/2016.
 */
public class UserInteractionHandler {

    private final ArrayList<IUserInteractionObserver> observers;
    private LevelState currentLevelState;
    private int buildType;
    private Node sceneRoot;
    private IGameContainer gameContainer;
    private boolean isBuilding;
    private Geometry currentlySelectedEntity;

    public UserInteractionHandler(Node sceneRoot, IGameContainer gameContainer) {
        this.sceneRoot = sceneRoot;
        this.gameContainer = gameContainer;
        this.observers = new ArrayList<>();
    }

    public void setLevel(LevelState level) {
        this.currentLevelState = level;
    }

    public void setBuildMode(int buildMode) {
        this.buildType = buildMode;
        this.isBuilding = true;
    }

    public void addSelectionListener(IUserInteractionObserver interactionObserver) {
        this.observers.add(interactionObserver);
    }

    public void orderSelectionToMove() {
        if (hasSelection()) {
            Optional<CollisionResult> entityUnderCursor = getEntityUnderCursor();
            if (entityUnderCursor.isPresent()) {
                Vector3f location = entityUnderCursor.get().getContactPoint();
                Optional<EntityAiController> currentAi = getCurrentAi();
                if (currentAi.isPresent()) {
                    currentAi.get().moveToLocation(location);
                }
            }
        }
    }

    public void orderSelectionToStop() {
        if (hasSelection()) {
            Optional<EntityAiController> currentAi = getCurrentAi();
            if (currentAi.isPresent()) {
                currentAi.get().stop();
            }
        }
    }

    public void orderSelectionToAttack() {
        if (hasSelection()) {
            Optional<CollisionResult> entityUnderCursor = getEntityUnderCursor();
            if (entityUnderCursor.isPresent()) {
                CollisionResult collisionResult = entityUnderCursor.get();
                Optional<EntityAiController> currentAi = getCurrentAi();
                if (currentAi.isPresent()) {
                    Boolean isTerrain = collisionResult.getGeometry().getUserData(EntityDataKeys.IS_TERRAIN);
                    Boolean isSelectable = collisionResult.getGeometry().getUserData(EntityDataKeys.IS_SELECTABLE);
                    if (isTerrain != null && isTerrain) {
                        currentAi.get().attackLocation(collisionResult.getContactPoint());
                    } else if (isSelectable != null && isSelectable) {
                        currentAi.get().attackEntity(collisionResult.getGeometry());
                    }
                }
            }
        }
    }

    private Optional<CollisionResult> getEntityUnderCursor() {
        return getWorldCursor().getEntityUnderCursor(this.sceneRoot);
    }

    public void clearSelection() {
        setCurrentSelection(null);
    }

    public void activateSelection() {
        if (this.isBuilding) {
            Spatial entity = this.gameContainer.world().build().unit(this.buildType, this.currentLevelState.getPlayerTeam());
            this.sceneRoot.attachChild(entity);
            isBuilding = false;
        } else {
            Optional<CollisionResult> entityUnderCursor = getEntityUnderCursor();
            if (entityUnderCursor.isPresent()) {
                CollisionResult collisionResult = entityUnderCursor.get();
                Geometry selection = collisionResult.getGeometry();
                Boolean isTerrain = selection.getUserData(EntityDataKeys.IS_TERRAIN);
                Boolean isSelectable = selection.getUserData(EntityDataKeys.IS_SELECTABLE);
                if (isTerrain != null && isTerrain) {
                    clearSelection();
                } else if (isSelectable != null && isSelectable) {
                    setCurrentSelection(selection);
                }
            }
        }
    }

    private WorldCursor getWorldCursor() {
        return this.currentLevelState.getWorldCursor();
    }

    private void setCurrentSelection(Geometry geometry) {
        this.currentlySelectedEntity = geometry;
        for (IUserInteractionObserver observer : this.observers) {
            observer.notifyNewSelection(geometry);
        }
    }

    private Optional<EntityAiController> getCurrentAi() {
        EntityAiController control = this.currentlySelectedEntity.getControl(EntityAiController.class);
        return Optional.ofNullable(control);
    }

    private boolean hasSelection() {
        return this.currentlySelectedEntity != null;
    }
}
