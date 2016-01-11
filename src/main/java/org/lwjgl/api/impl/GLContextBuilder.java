package org.lwjgl.api.impl;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by omnic on 1/10/2016.
 */
public class GLContextBuilder {
    public static GLContextHandler build() {
        int width = 800;
        int height = 800;
        boolean fullscreen = true;
        try {
            PixelFormat pixelFormat = new PixelFormat().withSamples(4);
            ContextAttribs contextAttribs = new ContextAttribs(3, 2)
                    .withForwardCompatible(false)
                    .withProfileCompatibility(true);
            Display.setDisplayMode(new DisplayMode(width,height));
            Display.setFullscreen(fullscreen);
            Display.setTitle("Voxel");
            Display.create(pixelFormat, contextAttribs);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        glClearColor(0f, 0f, 0f, 0f);

        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_CONSTANT_ALPHA);

        // Map the internal OpenGL coordinate system to the entire screen
        glViewport(0, 0, width, height);


        GLContextHandler glContextHandler = new GLContextHandler();

        return glContextHandler;
    }
}
