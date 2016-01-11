package org.lwjgl.api.impl;

import org.lwjgl.api.IGameContainer;

/**
 * Created by omnic on 1/10/2016.
 */
public class GameContainerBuilder {
    public static IGameContainer build(GLContextHandler glContext ) {
        GameInputHandler gameInputHandler = new GameInputHandler();
        GameResourceLoader gameResourceLoader = new GameResourceLoader();
        return new GameContainer(glContext, gameInputHandler, gameResourceLoader);
    }
}
