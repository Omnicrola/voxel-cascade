package org.lwjgl.api.impl;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.Color;

/**
 * Created by omnic on 1/10/2016.
 */
public class GLContextHandler {
    public void clearColorAndDepthBuffer() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void dispose() {
        Display.destroy();
    }

    public void setClearColor(Color color) {
        GL11.glClearColor(color.r, color.g, color.b, color.a);
    }

    public void synchronizeDisplay() {
        // Display.sync(this.displaySettings.getMaxFPS());
    }

    public void updateDisplay() {
        Display.update();
    }
}
