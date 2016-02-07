package com.omnicrola.voxel.ui.nifty;

import de.lessvoid.nifty.builder.ElementBuilder;

/**
 * Created by Eric on 1/30/2016.
 */
public interface IUiElement {
    void removeAllChildren();

    void addElement(ElementBuilder elementBuilder);

    void setText(String name);

    void setWidth(int width);

    int getWidth();
}
