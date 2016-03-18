package com.omnicrola.voxel.ui;

import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.scene.Node;
import com.omnicrola.voxel.input.GameInputAction;
import com.omnicrola.voxel.input.listeners.SelectionRectangleListener;
import com.omnicrola.voxel.ui.select.UiSelectionRectangle;
import de.lessvoid.nifty.Nifty;

/**
 * Created by omnic on 2/27/2016.
 */
public class UiManager implements IUiManager {
    private Nifty niftyGui;
    private InputManager inputManager;
    private UiSelectionRectangle selectionRectangle;
    private final SelectionRectangleListener selectionRectangleListener;

    public UiManager(Nifty niftyGui, InputManager inputManager, UiSelectionRectangle selectionRectangle) {
        this.niftyGui = niftyGui;
        this.inputManager = inputManager;
        this.selectionRectangle = selectionRectangle;
        this.selectionRectangleListener = new SelectionRectangleListener(inputManager, this.selectionRectangle);
    }

    @Override
    public void changeScreen(UiScreen uiScreen) {
        this.niftyGui.gotoScreen(uiScreen.toString());
    }

    @Override
    public void attach(Node guiNode) {
        guiNode.attachChild(this.selectionRectangle);
    }

    @Override
    public void bindInput() {
        addInputListener(GameInputAction.MOUSE_PRIMARY, this.selectionRectangleListener);
        addInputListener(GameInputAction.MOUSE_MOVEMENT, this.selectionRectangleListener);
        addInputListener(GameInputAction.MOUSE_MOVEMENT, this.selectionRectangleListener);
    }

    @Override
    public void unbindInput() {
        this.inputManager.removeListener(this.selectionRectangleListener);
    }

    private void addInputListener(GameInputAction inputAction, ActionListener actionListener) {
        this.inputManager.addListener(actionListener, inputAction.toString());
    }
}
