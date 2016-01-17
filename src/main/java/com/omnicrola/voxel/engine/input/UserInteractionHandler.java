package com.omnicrola.voxel.engine.input;

import com.jme3.collision.CollisionResult;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.entities.EntityDefinition;
import com.omnicrola.voxel.data.level.LevelEntityGenerator;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.engine.entities.EntityData;
import com.omnicrola.voxel.engine.entities.commands.MoveToLocationCommand;
import com.omnicrola.voxel.engine.entities.control.CommandQueueControl;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.settings.VoxelGlobals;

import java.util.Optional;

/**
 * Created by omnic on 1/16/2016.
 */
public class UserInteractionHandler {

    private LevelState currentLevelState;
    private int buildType;
    private Node sceneRoot;
    private IGameContainer gameContainer;
    private boolean isBuilding;
    private Geometry currentlySelectedEntity;

    public UserInteractionHandler(Node sceneRoot, IGameContainer gameContainer) {
        this.sceneRoot = sceneRoot;
        this.gameContainer = gameContainer;
    }

    public void setLevel(LevelState level) {
        this.currentLevelState = level;
    }

    public void setBuildMode(int buildMode) {
        this.buildType = buildMode;
        this.isBuilding = true;
    }

    public void activateSelection() {
        if (this.isBuilding) {
            System.out.println("build");
            ColorRGBA color = this.buildType == 1 ? ColorRGBA.Orange : ColorRGBA.Green;
            Vector3f position = this.currentLevelState.getWorldCursor().getWorldTranslation();
            Spatial entity = LevelEntityGenerator.createEntity(new EntityDefinition(position, color), gameContainer);
            this.sceneRoot.attachChild(entity);
            isBuilding = false;
        } else {
            Optional<CollisionResult> entityUnderCursor = this.currentLevelState.getWorldCursor().getEntityUnderCursor(this.sceneRoot);
            if (entityUnderCursor.isPresent()) {
                EntityData entityData = entityUnderCursor.get().getGeometry().getUserData(VoxelGlobals.ENTITY_DATA);
                if (entityData.isTerrain()) {
                    Vector3f cursorPosition = currentLevelState.getWorldCursor().getWorldTranslation();
                    CommandQueueControl control = this.currentlySelectedEntity.getControl(CommandQueueControl.class);
                    control.addCommand(new MoveToLocationCommand(new Vector3f(cursorPosition)));
                    System.out.println("move to position");
                } else {
                    Geometry geometry = entityUnderCursor.get().getGeometry();
                    this.currentlySelectedEntity = geometry;
                    System.out.println("select: " + geometry.getName() + " -> " + entityUnderCursor.get().getContactPoint());
                }
            }
        }

    }
}
