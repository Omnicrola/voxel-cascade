package org.lwjgl.api;

/**
 * Created by omnic on 1/10/2016.
 */
public interface IGameState {
    public void initialize(IGameContainer gameContainer);
    public void load(IGameContainer gameContainer);
    public void unload(IGameContainer gameContainer);
    public void render(IGameRenderer gameRenderer);
    public void update(IGameContainer gameContainer);
}
