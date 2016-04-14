package com.omnicrola.voxel.ui.nifty;

import de.lessvoid.nifty.builder.ElementBuilder;

/**
 * Created by Eric on 4/10/2016.
 */
public interface IUiPanel {
    void removeAllChildren();

    void addElement(ElementBuilder elementBuilder);

    void setVisible(boolean isVisible);

}
