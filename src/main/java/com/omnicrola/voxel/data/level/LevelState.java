package com.omnicrola.voxel.data.level;


import com.jme3.scene.Node;
import com.omnicrola.voxel.engine.input.WorldCursor;

/**
 * Created by omnic on 1/16/2016.
 */
public class LevelState {
    private Node terrain;
    private WorldCursor worldCursor;
    private String levelName;
    private float resources;
    private float timeElapsed;

    public LevelState(Node terrain, WorldCursor worldCursor, String levelName) {
        this.terrain = terrain;
        this.worldCursor = worldCursor;
        this.levelName = levelName;
        this.resources = 0;
        this.timeElapsed = 0;
    }

    public void setResources(float resources) {
        this.resources = resources;
    }

    public void setTimeElapsed(float timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public String getLevelName() {
        return levelName;
    }

    public float getResources() {
        return resources;
    }

    public float getTimeElapsed() {
        return timeElapsed;
    }

    public Node getTerrain() {
        return this.terrain;
    }

    public WorldCursor getWorldCursor() {
        return worldCursor;
    }
}
