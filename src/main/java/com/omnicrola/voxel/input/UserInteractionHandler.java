package com.omnicrola.voxel.input;

import com.jme3.collision.CollisionResult;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.entities.EntityDefinition;
import com.omnicrola.voxel.data.level.LevelEntityGenerator;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.entities.EntityData;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.settings.VoxelGlobals;

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

    public void activateSelection() {
        WorldCursor worldCursor = getWorldCursor();
        if (this.isBuilding) {
            ColorRGBA color = this.buildType == 1 ? ColorRGBA.Orange : ColorRGBA.Green;
            Vector3f position = worldCursor.getWorldTranslation();
            Spatial entity = LevelEntityGenerator.createEntity(new EntityDefinition(position, color), gameContainer);
            this.sceneRoot.attachChild(entity);
            isBuilding = false;
        } else {
            Optional<CollisionResult> entityUnderCursor = worldCursor.getEntityUnderCursor(this.sceneRoot);
            if (entityUnderCursor.isPresent()) {
                EntityData entityData = entityUnderCursor.get().getGeometry().getUserData(VoxelGlobals.ENTITY_DATA);
                if (entityData.isTerrain()) {
                    Vector3f cursorPosition = worldCursor.getWorldTranslation();
                    getCurrentSelection().getEntityAi().moveToLocation(new Vector3f(cursorPosition));
                } else {
                    Geometry geometry = entityUnderCursor.get().getGeometry();
                    setCurrentSelection(geometry);
                }
            }
        }

    }

    private WorldCursor getWorldCursor() {
        return this.currentLevelState.getWorldCursor();
    }

    private EntityData getCurrentSelection() {
        EntityData entityData = this.currentlySelectedEntity.getUserData(VoxelGlobals.ENTITY_DATA);
        return entityData;
    }

    private void setCurrentSelection(Geometry geometry) {
        this.currentlySelectedEntity = geometry;
        for (IUserInteractionObserver observer : this.observers) {
            observer.notifyNewSelection(geometry);
        }
    }


}
