package org.lwjgl.api.impl;

import org.lwjgl.api.*;

/**
 * Created by omnic on 1/10/2016.
 */
public class GameContainer implements IGameContainer {
    private GLContextHandler glContext;
    private IGameInput gameInput;
    private IResourceLoader resourceLoader;

    public GameContainer(GLContextHandler glContext, IGameInput gameInput, IResourceLoader resourceLoader) {
        this.glContext = glContext;
        this.gameInput = gameInput;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public float delta() {
        return 0;
    }

    @Override
    public IGameInput getInput() {
        return this.gameInput;
    }

    @Override
    public IResourceLoader getResourceLoader() {
        return this.resourceLoader;
    }

}
