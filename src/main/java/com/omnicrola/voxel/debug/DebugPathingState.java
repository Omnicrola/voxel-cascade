package com.omnicrola.voxel.debug;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResult;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.engine.states.ActivePlayState;
import com.omnicrola.voxel.entities.behavior.ai.pathing.NavigationPath;
import com.omnicrola.voxel.entities.behavior.ai.pathing.VoxelAstarPathFinder;
import com.omnicrola.voxel.input.IWorldCursor;
import com.omnicrola.voxel.input.SelectionGroup;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.terrain.highlight.HighlighterCubeCache;

import java.util.List;
import java.util.Optional;

/**
 * Created by Eric on 3/9/2016.
 */
public class DebugPathingState extends AbstractAppState {

    private VoxelGameEngine game;
    private Node rootNode;
    private VoxelAstarPathFinder pathfinder;
    private HighlighterCubeCache pathCubeCache;
    private HighlighterCubeCache secondaryCubeCache;

    public DebugPathingState(HighlighterCubeCache pathCubeCache, HighlighterCubeCache secondaryCubeCache) {
        this.pathCubeCache = pathCubeCache;
        this.secondaryCubeCache = secondaryCubeCache;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.game = (VoxelGameEngine) app;
        this.rootNode = new Node();
        setEnabled(false);
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        ActivePlayState activePlayState = this.game.getStateManager().getState(ActivePlayState.class);
        IWorldCursor worldCursor = activePlayState.getWorldCursor();
        SelectionGroup selectionGroup = worldCursor.getCurrentSelection();
        Vector3f startPoint = selectionGroup.getCenterPoint();
        Optional<CollisionResult> terrainUnderCursor = worldCursor.getTerrainPositionUnderCursor();
        if (terrainUnderCursor.isPresent()) {
            VoxelAstarPathFinder pathFinder = getPathfinder();
            Vector3f destination = terrainUnderCursor.get().getContactPoint();
            NavigationPath path = pathFinder.findPath(startPoint, destination);
            List<Vector3f> allNodesUsed = pathFinder.getAllNodesUsed();
//            drawUsedNodes(allNodesUsed);
            if (path != null) {
                drawPath(path);
            }
        }
    }

    private void drawUsedNodes(List<Vector3f> allNodesUsed) {
        this.secondaryCubeCache.reset();
        for (Vector3f location : allNodesUsed) {
            Geometry cube = secondaryCubeCache.next();
            cube.setLocalTranslation(location);
            this.rootNode.attachChild(cube);
        }
    }

    private void drawPath(NavigationPath path) {
        this.pathCubeCache.reset();
        while (path.hasNext()) {
            Vector3f location = path.next();
            Geometry cube = pathCubeCache.next();
            cube.setLocalTranslation(location);
            this.rootNode.attachChild(cube);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            enable();
        } else {
            disable();
        }
    }

    private void enable() {
        this.game.getWorldNode().getFxNode().attachChild(this.rootNode);
    }

    private void disable() {
        this.game.getWorldNode().getFxNode().detachChild(this.rootNode);
    }

    private VoxelAstarPathFinder getPathfinder() {
        if (pathfinder == null) {
            ActivePlayState activePlayState = this.game.getStateManager().getState(ActivePlayState.class);
            ITerrainManager terrainManager = activePlayState.getTerrainManager();
            this.pathfinder = new VoxelAstarPathFinder(terrainManager);

        }
        return pathfinder;
    }
}
