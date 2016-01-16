package com.omnicrola.voxel.ui;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;

/**
 * Created by omnic on 1/15/2016.
 */
public class GLabel extends BitmapText {
    public GLabel(String displayText, BitmapFont font, ColorRGBA fontColor) {
        super(font, false);
        this.setSize(font.getCharSet().getRenderedSize());
        this.setColor(fontColor);
        this.setText(displayText);
    }

    public void setTextPosition(float x, float y) {
        this.setLocalTranslation(x, y + this.getLineHeight(), 0); // position
    }
}
