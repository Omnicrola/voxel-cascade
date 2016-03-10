package com.omnicrola.voxel.input;

import com.jme3.collision.CollisionResult;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.util.Vec3i;

import java.util.List;
import java.util.Optional;

/**
 * Created by Eric on 2/29/2016.
 */
public interface IWorldCursor {
    void executePrimary(GameMouseEvent gameMouseEvent);

    void executeSecondary(boolean isPressed);

    void setCursorStrategy(ICursorStrategy cursorStrategy);

    Optional<CollisionResult> getTerrainPositionUnderCursor();

    void clearCursorStrategy();

    Vec3i getSnappedLocation();

    Optional<CollisionResult> getUnitUnderCursor();

    void setCurrentSelection(SelectionGroup selectionGroup);

    List<Spatial> selectAllUnitsIn(ScreenRectangle screenRectangle);

    void setDefaultCursorStrategy(ICursorStrategy cursorStrategy);

    void clearSelection();

    void setVisible(boolean isVisible);

    void addSelectionObserver(IUserSelectionObserver observer);

    void attachTo(Node node);

    SelectionGroup getCurrentSelection();
}
