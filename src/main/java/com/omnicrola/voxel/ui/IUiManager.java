package com.omnicrola.voxel.ui;

import com.jme3.scene.Node;

/**
 * Created by Eric on 2/24/2016.
 */
public interface IUiManager {
    void changeScreen(UiScreen uiScreen);

    void attach(Node guiNode);

    void bindInput();

    void unbindInput();
}
