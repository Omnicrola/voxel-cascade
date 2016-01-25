package com.omnicrola.voxel.ui.builders;

import com.jme3.math.ColorRGBA;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.jme.wrappers.impl.GuiBuilder;
import com.omnicrola.voxel.ui.GLabel;
import com.omnicrola.voxel.ui.UserInterface;
import de.lessvoid.nifty.builder.ScreenBuilder;

/**
 * Created by omnic on 1/16/2016.
 */
public class UserInterfaceBuilder {
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

        uiBuilder.screen("PlayScreen", new ScreenBuilder("Hello"));
        return rootUi;
    }
}
