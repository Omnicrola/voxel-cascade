package com.omnicrola.voxel.input.listeners;

import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.math.Vector2f;
import com.omnicrola.voxel.input.GameInputAction;
import com.omnicrola.voxel.ui.select.UiSelectionRectangle;

/**
 * Created by Eric on 2/29/2016.
 */
public class SelectionRectangleListener implements ActionListener, AnalogListener {


    private InputManager inputManager;
    private final UiSelectionRectangle uiSelectionRectangle;
    private boolean isSelecting;

    public SelectionRectangleListener(InputManager inputManager, UiSelectionRectangle uiSelectionRectangle) {
        this.inputManager = inputManager;
        this.uiSelectionRectangle = uiSelectionRectangle;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals(GameInputAction.MOUSE_PRIMARY.toString())) {
            if (!this.isSelecting) {
                Vector2f cursorPosition = this.inputManager.getCursorPosition();
                this.uiSelectionRectangle.setUpperLeft(cursorPosition);
            }
            this.uiSelectionRectangle.setVisible(isPressed);
            this.isSelecting = isPressed;
        }
    }

    @Override
    public void onAnalog(String name, float value, float tpf) {
        if (isSelecting && name.equals(GameInputAction.MOUSE_MOVEMENT.toString())) {
            Vector2f cursorPosition = this.inputManager.getCursorPosition();
            this.uiSelectionRectangle.setLowerRight(cursorPosition);
        }
    }
}
