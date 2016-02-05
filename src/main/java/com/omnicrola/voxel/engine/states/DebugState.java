package com.omnicrola.voxel.engine.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.debug.Grid;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.engine.processors.WireframeProcessor;
import com.omnicrola.voxel.input.GameInputAction;

/**
 * Created by Eric on 2/4/2016.
 */
public class DebugState extends AbstractAppState {

    private VoxelGameEngine game;
    private WireframeProcessor wireframeProcessor;

    private class ToggleDebugListener implements ActionListener {

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (!isPressed) {
                System.out.println("toggle debug");
                setEnabled(!isEnabled());
            }
        }
    }

    private Node rootNode;
    private AssetManager assetManager;

    @Override
    public void initialize(AppStateManager stateManager, Application game) {
        this.game = (VoxelGameEngine) game;
        super.initialize(stateManager, game);
        this.assetManager = game.getAssetManager();
        this.rootNode = new Node();
        buildCoordinateAxes(new Vector3f());
        buildGrid(new Vector3f(), 10, ColorRGBA.White);

        this.wireframeProcessor = new WireframeProcessor(game.getAssetManager());
        game.getViewPort().addProcessor(wireframeProcessor);

        game.getInputManager().addListener(new ToggleDebugListener(), GameInputAction.TOGGLE_DEBUG.toString());
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
            this.wireframeProcessor.setEnabled(enabled);
        if (enabled) {
            this.game.getRootNode().attachChild(this.rootNode);
        } else {
            this.game.getRootNode().detachChild(this.rootNode);
        }
    }

    private void buildCoordinateAxes(Vector3f pos) {
        Arrow arrow = new Arrow(Vector3f.UNIT_X);
        arrow.setLineWidth(4); // make arrow thicker
        buildShape(arrow, ColorRGBA.Red).setLocalTranslation(pos);

        arrow = new Arrow(Vector3f.UNIT_Y);
        arrow.setLineWidth(4); // make arrow thicker
        buildShape(arrow, ColorRGBA.Green).setLocalTranslation(pos);

        arrow = new Arrow(Vector3f.UNIT_Z);
        arrow.setLineWidth(4); // make arrow thicker
        buildShape(arrow, ColorRGBA.Blue).setLocalTranslation(pos);
    }

    private Geometry buildShape(Mesh shape, ColorRGBA color) {
        Geometry g = new Geometry("coordinate axis", shape);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.getAdditionalRenderState().setWireframe(true);
        mat.setColor("Color", color);
        g.setMaterial(mat);
        rootNode.attachChild(g);
        return g;
    }

    private Geometry buildGrid(Vector3f pos, int size, ColorRGBA color) {
        Geometry g = new Geometry("wireframe grid", new Grid(size, size, 0.2f));
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.getAdditionalRenderState().setWireframe(true);
        mat.setColor("Color", color);
        g.setMaterial(mat);
        g.center().move(pos);
        rootNode.attachChild(g);
        return g;
    }
}
