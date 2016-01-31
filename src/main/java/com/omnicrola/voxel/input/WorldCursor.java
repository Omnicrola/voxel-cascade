package com.omnicrola.voxel.input;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.omnicrola.voxel.IDisposable;
import com.omnicrola.voxel.jme.wrappers.IGameInput;
import com.omnicrola.voxel.physics.CollisionDistanceComparator;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.util.VoxelUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by omnic on 1/16/2016.
 */
public class WorldCursor extends Node implements IDisposable {
    private final IGameInput inputManager;
    private final Camera camera;
    private final CollisionDistanceComparator collisionDistanceComparator;
    private ICursorStrategy cursorStrategy;
    private ICursorStrategy defaultCursorStrategy;
    private Node terrainNode;
    private SelectionGroup currentSelection;
    private List<IUserInteractionObserver> observers;

    public WorldCursor(IGameInput inputManager, Camera camera, Node terrainNode) {
        this.observers = new ArrayList<>();
        this.inputManager = inputManager;
        this.camera = camera;
        this.terrainNode = terrainNode;
        this.currentSelection = new SelectionGroup();
        this.collisionDistanceComparator = new CollisionDistanceComparator();
        this.defaultCursorStrategy = NullCursorStrategy.INSTANCE;
        clearCursorStrategy();
    }

    public void addSelectionObserver(IUserInteractionObserver selectionObserver) {
        this.observers.add(selectionObserver);
    }

    public void removeSelectionObserver(IUserInteractionObserver userInteractionObserver) {
        this.observers.remove(userInteractionObserver);
    }

    @Override
    public void updateLogicalState(float tpf) {
        Ray pickRay = getPickRay();
        setPositionToNearestVoxel(pickRay);
    }

    public void setCursorStrategy(ICursorStrategy cursorStrategy) {
        System.out.println("Set Cursor: " + cursorStrategy.getClass().getSimpleName());
        this.cursorStrategy = cursorStrategy;
        this.inputManager.setCursor(cursorStrategy.get2DCursor());
        this.detachAllChildren();
        this.attachChild(this.cursorStrategy.get3dCursor());
        System.out.println("attack cursor: "+this.cursorStrategy.get3dCursor().getName());
    }

    public void setDefaultCursorStrategy(ICursorStrategy cursorStrategy) {
        this.defaultCursorStrategy = cursorStrategy;
    }

    public void clearCursorStrategy() {
        setCursorStrategy(this.defaultCursorStrategy);
    }

    public void setVisible(boolean visible) {
        if (visible) {
            this.setCullHint(CullHint.Inherit);
        } else {
            this.setCullHint(CullHint.Always);
        }
    }

    public void executePrimary(boolean isPressed) {
        this.cursorStrategy.executePrimary(isPressed, this.currentSelection);
    }

    public void executeSecondary(boolean isPressed) {
        this.cursorStrategy.executeSecondary(isPressed, this.currentSelection);
    }

    public void clearSelection() {
        this.currentSelection = new SelectionGroup();
        notifySelectionObservers();
    }

    private void notifySelectionObservers() {
        this.observers.forEach(o -> o.notifyNewSelection(this.currentSelection));
    }

    public Optional<CollisionResult> getUnitUnderCursor(Node targetNode) {
        CollisionResults results = new CollisionResults();
        Ray pickRay = getPickRay();
        targetNode.collideWith(pickRay, results);
        return VoxelUtil.convertToStream(results)
                .filter(c -> isSelectableUnit(c))
                .sorted(this.collisionDistanceComparator)
                .findFirst();
    }

    public Optional<CollisionResult> getTerrainUnderCursor(Node terrain) {
        CollisionResults results = new CollisionResults();
        Ray pickRay = getPickRay();
        terrain.collideWith(pickRay, results);
        return VoxelUtil.convertToStream(results)
                .sorted(this.collisionDistanceComparator)
                .findFirst();
    }

    private void setPositionToNearestVoxel(Ray pickRay) {
        CollisionResults results = new CollisionResults();
        this.terrainNode.collideWith(pickRay, results);
        if (results.size() > 0) {
            Vector3f contactPoint = results.getClosestCollision().getContactPoint();
            this.setLocalTranslation(contactPoint);
        }
    }

    private Ray getPickRay() {
        Vector2f cursorPosition = this.inputManager.getCursorPosition();
        Vector3f cursor3d = this.camera.getWorldCoordinates(cursorPosition.clone(), 0f).clone();
        Vector3f direction = this.camera.getWorldCoordinates(cursorPosition.clone(), 1f).subtractLocal(cursor3d)
                .normalizeLocal();
        return new Ray(cursor3d, direction);
    }

    private boolean isSelectableUnit(CollisionResult collision) {
        Geometry geometry = collision.getGeometry();
        boolean isUnit = VoxelUtil.booleanData(geometry, EntityDataKeys.IS_UNIT);
        boolean isStructure = VoxelUtil.booleanData(geometry, EntityDataKeys.IS_STRUCTURE);
        boolean isSelectable = VoxelUtil.booleanData(geometry, EntityDataKeys.IS_SELECTABLE);
        return isSelectable && (isUnit || isStructure);
    }

    public void setCurrentSelection(SelectionGroup currentSelection) {
        this.currentSelection = currentSelection;
        notifySelectionObservers();
    }

    public SelectionGroup getCurrentSelection() {
        return currentSelection;
    }

    public void dispose() {
        this.observers.clear();
    }
}
