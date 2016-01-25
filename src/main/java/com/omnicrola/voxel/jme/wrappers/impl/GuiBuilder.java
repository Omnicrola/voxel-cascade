package com.omnicrola.voxel.jme.wrappers.impl;

import com.jme3.font.BitmapFont;
import com.jme3.math.ColorRGBA;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.ui.GLabel;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ScreenBuilder;

/**
 * Created by omnic on 1/15/2016.
 */
public class GuiBuilder {
    private final BitmapFont guiFont;
    private final Nifty nifty;

    public GuiBuilder(VoxelGameEngine game) {
        this.nifty = game.getNiftyGui();
        this.guiFont = game.getGuiFont();
    }


    public GLabel label(String displayText, ColorRGBA fontColor) {
        return new GLabel(displayText, this.guiFont, fontColor);
    }


    public void screen(String screenName, ScreenBuilder builder) {
        this.nifty.addScreen(screenName, builder.build(nifty));
    }
}
