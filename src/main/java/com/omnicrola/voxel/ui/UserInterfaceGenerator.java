package com.omnicrola.voxel.ui;

import com.jme3.math.ColorRGBA;
import com.omnicrola.voxel.jme.wrappers.impl.GuiBuilder;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;

/**
 * Created by omnic on 1/16/2016.
 */
public class UserInterfaceGenerator {
    public static UserInterface createPlayUi(IGameContainer gameContainer) {
        GuiBuilder uiBuilder = gameContainer.gui().build();
        GLabel selectedLabel = uiBuilder.label("Selected:", ColorRGBA.White);
        GLabel positionLabel = uiBuilder.label("Position:", ColorRGBA.White);
        GLabel velocityLabel = uiBuilder.label("Position:", ColorRGBA.White);

        UserInterface rootUi = new UserInterface(selectedLabel, positionLabel, velocityLabel);
        rootUi.attachChild(selectedLabel);
        rootUi.attachChild(positionLabel);
        rootUi.attachChild(velocityLabel);
        selectedLabel.setLocalTranslation(10, 600, 0);
        positionLabel.setLocalTranslation(10, 580, 0);
        velocityLabel.setLocalTranslation(10, 560, 0);

        return rootUi;
    }
}
