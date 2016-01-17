package com.omnicrola.voxel.input.listeners;

import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.input.UserInteractionHandler;

/**
 * Created by omnic on 1/17/2016.
 */
public class OrderAttackListener implements ActionListener {
    private UserInteractionHandler userInteractionHandler;

    public OrderAttackListener(UserInteractionHandler userInteractionHandler) {
        this.userInteractionHandler = userInteractionHandler;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (!isPressed) {
            this.userInteractionHandler.orderSelectionToAttack();
        }
    }
}
