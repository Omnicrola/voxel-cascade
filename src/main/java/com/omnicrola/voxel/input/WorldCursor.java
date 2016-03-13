package com.omnicrola.voxel.input;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.IDisposable;
import com.omnicrola.voxel.jme.wrappers.IGameInput;
import com.omnicrola.voxel.physics.CollisionDistanceComparator;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.util.VoxelUtil;
import com.omnicrola.voxel.world.IWorldNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by omnic on 1/16/2016.
 */
public class WorldCursor extends Node implements IWorldCursor, IDisposable {
    private final IGameInput inputManager;
    private final Camera camera;
    private final CollisionDistanceComparator collisionDistanceComparator;
    private ICursorStrategy cursorStrategy;
    private ICursorStrategy defaultCursorStrategy;
    private SelectionGroup currentSelection;
    private List<IUserSelectionObserver> observers;
    private SelectionFrustrumFactory selectionFrustrumFactory;
    private IWorldNode worldRootNode;

    public WorldCursor(IGameInput inputManager,
                       Camera camera,
                       SelectionFrustrumFactory selectionFrustrumFactory,
                       IWorldNode worldRootNode) {
        this.selectionFrustrumFactory = selectionFrustrumFactory;
        this.worldRootNode = worldRootNode;
        this.observers = new ArrayList<>();
        this.inputManager = inputManager;
        this.camera = camera;
        this.currentSelection = new SelectionGroup();
        this.collisionDistanceComparator = new CollisionDistanceComparator();
        this.defaultCursorStrategy = NullCursorStrategy.INSTANCE;
        clearCursorStrategy();
    }

    public void addSelectionObserver(IUserSelectionObserver selectionObserver) {
        this.observers.add(selectionObserver);
    }

    public void removeSelectionObserver(IUserSelectionObserver userInteractionObserver) {
        this.observers.remove(userInteractionObserver);
    }

    @Override
    public void updateLogicalState(float tpf) {
        super.updateLogicalState(tpf);
        Ray pickRay = getPickRay();
        setPositionToNearestVoxel(pickRay);
        boolean hasChanged = this.currentSelection.update(tpf);
        if (hasChanged) {
            notifySelectionUpdated();
        }
    }

    private void setPositionToNearestVoxel(Ray pickRay) {
        CollisionResults results = new CollisionResults();
        this.worldRootNode.getTerrainNode().collideWith(pickRay, results);
        if (results.size() > 0) {
            Vector3f contactPoint = results.getClosestCollision().getContactPoint();
            contactPoint.setX((float) Math.round(contactPoint.x) - 0.5f);
            contactPoint.setY((float) Math.round(contactPoint.y));
            contactPoint.setZ((float) Math.round(contactPoint.z) - 0.5f);
            this.setLocalTranslation(contactPoint);
        }
    }

    public void setCursorStrategy(ICursorStrategy cursorStrategy) {
        this.cursorStrategy = cursorStrategy;
        this.inputManager.setCursor(cursorStrategy.get2DCursor());
        this.detachAllChildren();
        this.attachChild(this.cursorStrategy.get3dCursor());
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

    public void executePrimary(GameMouseEvent gameMouseEvent) {
        this.cursorStrategy.executePrimary(gameMouseEvent, this.currentSelection);
    }

    public void executeSecondary(boolean isPressed) {
        this.cursorStrategy.executeSecondary(isPressed, this.currentSelection);
    }

    public void clearSelection() {
        this.currentSelection = new SelectionGroup();
        notifySelectionChanged();
    }

    private void notifySelectionChanged() {
        this.observers.forEach(o -> o.notifyNewSelection(this.currentSelection));
    }

    private void notifySelectionUpdated() {
        this.observers.forEach(o -> o.notifySelectionUpdated(this.currentSelection));
    }

    public Optional<CollisionResult> getUnitUnderCursor() {
        CollisionResults results = new CollisionResults();
        Ray pickRay = getPickRay();
        this.worldRootNode.getUnitsNode().collideWith(pickRay, results);
        return VoxelUtil.convertToStream(results)
                .filter(c -> isSelectableUnit(c.getGeometry()))
                .sorted(this.collisionDistanceComparator)
                .findFirst();
    }

    public Optional<CollisionResult> getTerrainPositionUnderCursor() {
        CollisionResults results = new CollisionResults();
        Ray pickRay = getPickRay();
        this.worldRootNode.getTerrainNode().collideWith(pickRay, results);
        return VoxelUtil.convertToStream(results)
                .sorted(this.collisionDistanceComparator)
                .findFirst();
    }

    private Ray getPickRay() {
        Vector2f cursorPosition = this.inputManager.getCursorPosition();
        Vector3f cursor3d = this.camera.getWorldCoordinates(cursorPosition.clone(), 0f).clone();
        Vector3f direction = this.camera.getWorldCoordinates(cursorPosition.clone(), 1f).subtractLocal(cursor3d)
                .normalizeLocal();
        return new Ray(cursor3d, direction);
    }

    private boolean isSelectableUnit(Spatial spatial) {
        boolean isUnit = VoxelUtil.booleanData(spatial, EntityDataKeys.IS_UNIT);
        boolean isStructure = VoxelUtil.booleanData(spatial, EntityDataKeys.IS_STRUCTURE);
        boolean isSelectable = VoxelUtil.booleanData(spatial, EntityDataKeys.IS_SELECTABLE);
        return isSelectable && (isUnit || isStructure);
    }

    public void setCurrentSelection(SelectionGroup currentSelection) {
        this.currentSelection = currentSelection;
        notifySelectionChanged();
    }

    public SelectionGroup getCurrentSelection() {
        return currentSelection;
    }

    public void dispose() {
        this.observers.clear();
    }

    public Vec3i getSnappedLocation() {
        Vector3f p = this.getLocalTranslation();
        return Vec3i.fromVec3(p);
    }

    public List<Spatial> selectAllUnitsIn(ScreenRectangle screenRectangle) {
        SelectionFrustrum selectionFrustrum = this.selectionFrustrumFactory.build(screenRectangle);
        List<Spatial> children = this.worldRootNode.getUnitsNode().getChildren();

        List<Spatial> collect = children
                .stream()
                .filter(s -> isSelectableUnit(s))
                .filter(s -> selectionFrustrum.isContained(s.getWorldTranslation()))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public void attachTo(Node node) {
        node.attachChild(this);
    }
}
