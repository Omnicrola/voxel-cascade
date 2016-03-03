package com.omnicrola.voxel.input;

import com.jme3.asset.AssetManager;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.input.InputManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.Effect;
import com.omnicrola.voxel.input.actions.*;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.terrain.highlight.CubeFactory;
import com.omnicrola.voxel.terrain.highlight.HighlighterCubeCache;
import com.omnicrola.voxel.terrain.highlight.TerrainHighlighterControl;
import com.omnicrola.voxel.ui.Cursor2dProvider;
import com.omnicrola.voxel.ui.CursorToken;
import com.omnicrola.voxel.world.WorldManager;
import com.omnicrola.voxel.world.build.WorldEntityBuilder;

/**
 * Created by omnic on 1/30/2016.
 */
public class CursorCommandAdaptor implements ICursorCommandAdapter {

    private Cursor2dProvider cursor2dProvider;
    private InputManager inputManager;
    private WorldEntityBuilder worldEntityBuilder;
    private WorldManager worldManager;
    private ITerrainManager terrainManager;
    private AssetManager assetManager;

    public CursorCommandAdaptor(InputManager inputManager,
                                Cursor2dProvider cursor2dProvider,
                                WorldEntityBuilder worldEntityBuilder,
                                WorldManager worldManager,
                                ITerrainManager terrainManager,
                                AssetManager assetManager) {
        this.inputManager = inputManager;
        this.cursor2dProvider = cursor2dProvider;
        this.worldEntityBuilder = worldEntityBuilder;
        this.worldManager = worldManager;
        this.terrainManager = terrainManager;
        this.assetManager = assetManager;
    }

    @Override
    public SelectUnitsCursorStrategy setSelectStrategy() {
        JmeCursor defaultCursor = getCursor(CursorToken.DEFAULT);
        IWorldCursor worldCursor = this.worldManager.getWorldCursor();
        SelectUnitsCursorStrategy selectUnitsCursorStrategy = new SelectUnitsCursorStrategy(
                this, worldCursor, this.inputManager, defaultCursor);
        worldCursor.setCursorStrategy(selectUnitsCursorStrategy);
        return selectUnitsCursorStrategy;
    }

    @Override
    public void setAttackStrategy() {
        JmeCursor attackCursor = getCursor(CursorToken.ATTACK);
        IWorldCursor worldCursor = this.worldManager.getWorldCursor();
        AttackCursorStrategy attackCursorStrategy = new AttackCursorStrategy(worldCursor, attackCursor);
        worldCursor.setCursorStrategy(attackCursorStrategy);
    }

    @Override
    public void setMoveStrategy() {
        JmeCursor moveCursor = getCursor(CursorToken.MOVE);
        IWorldCursor worldCursor = this.worldManager.getWorldCursor();
        MoveSelectedUnitsStrategy moveSelectedUnitsStrategy = new MoveSelectedUnitsStrategy(worldCursor, moveCursor);
        worldCursor.setCursorStrategy(moveSelectedUnitsStrategy);
    }

    @Override
    public void setBuildStrategy(SelectionGroup selectionGroup) {
        JmeCursor buildCursor = getCursor(CursorToken.BUILD);
        EmptyBuildStrategy emptyBuildStrategy = new EmptyBuildStrategy(buildCursor);
        IWorldCursor worldCursor = this.worldManager.getWorldCursor();
        worldCursor.setCursorStrategy(emptyBuildStrategy);
    }

    @Override
    public void setBuildUnitStrategy(int unitId, float buildRadius, SelectionGroup selectionGroup) {
        JmeCursor buildCursor = getCursor(CursorToken.BUILD);
        Spatial exampleBuildTarget = createValidityCheckingModel(unitId, buildRadius, selectionGroup);
        IWorldCursor worldCursor = this.worldManager.getWorldCursor();

        BuildUnitStrategy buildUnitStrategy = new BuildUnitStrategy(
                unitId,
                buildCursor,
                exampleBuildTarget,
                worldEntityBuilder,
                worldManager);
        worldCursor.setCursorStrategy(buildUnitStrategy);
    }

    private Spatial createValidityCheckingModel(int unitId, float buildRadius, SelectionGroup selectionGroup) {
        Spatial placeholder = this.worldEntityBuilder.buildPlaceholderUnit(unitId, buildRadius, selectionGroup);
        return placeholder;
    }

    @Override
    public void setBuildVoxelStrategy(byte type) {
        JmeCursor buildCursor = getCursor(CursorToken.BUILD);
        Geometry placeholderVoxel = this.worldEntityBuilder.buildCube(ColorRGBA.Green);
        IWorldCursor worldCursor = this.worldManager.getWorldCursor();

        BuildVoxelCursorStrategy buildVoxelCursorStrategy = new BuildVoxelCursorStrategy(
                worldCursor,
                type,
                buildCursor,
                this.terrainManager,
                placeholderVoxel);
        worldCursor.setCursorStrategy(buildVoxelCursorStrategy);
    }

    @Override
    public void setHarvestStrategy() {
        JmeCursor harvestCursor = getCursor(CursorToken.HARVEST);
        IWorldCursor worldCursor = this.worldManager.getWorldCursor();

        Geometry cube = this.worldEntityBuilder.buildCube(ColorRGBA.White);
        cube.getMaterial().getAdditionalRenderState().setWireframe(true);
        cube.setLocalTranslation(0.5f, -0.5f, 0.5f);
        cube.setLocalScale(1.1f);

        CubeFactory cubeFactory = new CubeFactory(this.worldEntityBuilder);
        cubeFactory.setScale(new Vector3f(1f, 1.1f, 1f));
        cubeFactory.setColor(new ColorRGBA(0, 1, 0, 0.5f));
        HighlighterCubeCache highlighterCubeCache = new HighlighterCubeCache(cubeFactory);
        TerrainHighlighterControl terrainHighlighter = new TerrainHighlighterControl(this.worldManager.getWorldCursor(),
                this.terrainManager,
                highlighterCubeCache);

        Node cursorNode = new Node();
        cursorNode.addControl(terrainHighlighter);
        this.worldManager.addEffect(new Effect(cursorNode));

        HarvestCursorStrategy harvestStrategy = new HarvestCursorStrategy(terrainHighlighter, this.terrainManager, worldCursor, harvestCursor, cube);
        worldCursor.setCursorStrategy(harvestStrategy);
    }

    private JmeCursor getCursor(CursorToken cursorToken) {
        return this.cursor2dProvider.getCursor(cursorToken);
    }

}
