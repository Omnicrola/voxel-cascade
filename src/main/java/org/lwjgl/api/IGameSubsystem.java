package org.lwjgl.api;

/**
 * Created by omnic on 1/10/2016.
 */
public interface IGameSubsystem {

    public void update(IGameContainer gameContainer);
    public void render(IGameContainer gameContainer);
    public void load(IGameContainer gameContainer);
    public void unload(IGameContainer gameContainer);
}
