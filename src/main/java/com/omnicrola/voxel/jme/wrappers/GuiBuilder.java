package com.omnicrola.voxel.jme.wrappers;

import com.jme3.font.BitmapFont;
import com.jme3.math.ColorRGBA;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.ui.GLabel;

/**
 * Created by omnic on 1/15/2016.
 */
public class GuiBuilder {
    private final BitmapFont guiFont;

    public GuiBuilder(VoxelGameEngine game) {
        this.guiFont = game.getGuiFont();
    }

    public GLabel label(String displayText, ColorRGBA fontColor) {
        return new GLabel(displayText, this.guiFont, fontColor);
    }
}
