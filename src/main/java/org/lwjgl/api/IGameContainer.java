package org.lwjgl.api;

/**
 * Created by omnic on 1/10/2016.
 */
public interface IGameContainer {
    public float delta();
    public IGameInput getInput();
    public IResourceLoader getResourceLoader();
}
