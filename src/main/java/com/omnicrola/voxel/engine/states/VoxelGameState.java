package com.omnicrola.voxel.engine.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.InputListener;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.omnicrola.util.Tuple;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.input.GameInputAction;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.jme.wrappers.IGameInput;
import com.omnicrola.voxel.jme.wrappers.impl.JmeApplicationWrapper;
import com.omnicrola.voxel.ui.GLabel;

import java.util.ArrayList;

/**
 * Created by omnic on 1/15/2016.
 */
public abstract class VoxelGameState extends AbstractAppState {

    private final String stateName;
    protected final Node stateRootNode;
    protected final Node stateRootUiNode;

    private JmeApplicationWrapper jmeApplicationWrapper;
    private GLabel stateLabel;
    private ArrayList<Tuple<GameInputAction, ActionListener>> stateInputBindings;

    public VoxelGameState(String stateName) {
        this.stateName = stateName;
        this.stateInputBindings = new ArrayList<>();
        this.stateRootNode = new Node();
        this.stateRootUiNode = new Node();
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.jmeApplicationWrapper = new JmeApplicationWrapper((VoxelGameEngine) app);
        voxelInitialize(this.jmeApplicationWrapper);
        GLabel stateLabel = this.jmeApplicationWrapper.gui().build().label("State: " + this.stateName, ColorRGBA.Green);
        stateLabel.setTextPosition(10, 500);
        this.stateRootUiNode.attachChild(stateLabel);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            this.jmeApplicationWrapper.gui().attach(this.stateRootUiNode);
            this.jmeApplicationWrapper.world().attach(this.stateRootNode);
            bindInput();
            voxelEnable(this.jmeApplicationWrapper);
        } else {
            this.jmeApplicationWrapper.gui().remove(this.stateRootUiNode);
            this.jmeApplicationWrapper.world().remove(this.stateRootNode);
            unbindInput();
            voxelDisable(this.jmeApplicationWrapper);
        }
    }

    private void bindInput() {
        IGameInput gameInput = this.jmeApplicationWrapper.input();
        this.stateInputBindings
                .stream()
                .forEach(p -> gameInput.bind(p.getLeft(), p.getRight()));
    }

    private void unbindInput() {
        IGameInput gameInput = this.jmeApplicationWrapper.input();
        this.stateInputBindings
                .stream()
                .forEach(p -> gameInput.unbind(p.getRight()));
    }

    @Override
    final public void stateAttached(AppStateManager stateManager) {
    }

    @Override
    final public void stateDetached(AppStateManager stateManager) {
    }

    protected void addStateInput(GameInputAction inputToBind, InputListener inputListener) {
        this.stateInputBindings.add(new Tuple(inputToBind, inputListener));
    }

    protected abstract void voxelInitialize(IGameContainer gameContainer);

    protected abstract void voxelEnable(IGameContainer gameContainer);

    protected abstract void voxelDisable(IGameContainer gameContainer);
}
