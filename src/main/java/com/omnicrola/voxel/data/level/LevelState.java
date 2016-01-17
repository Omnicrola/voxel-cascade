package com.omnicrola.voxel.data.level;

import com.jme3.light.Light;
import com.jme3.scene.Node;
import com.omnicrola.voxel.input.WorldCursor;

/**
 * Created by omnic on 1/16/2016.
 */
public class LevelState {
    private Node terrain;
    private Node lights;
    private Node entities;
    private WorldCursor worldCursor;
    private String levelName;
    private float resources;
    private float timeElapsed;
    private Light sun;

    public LevelState(Node terrain, Light sun, Node entities, WorldCursor worldCursor, String levelName) {
        this.terrain = terrain;
        this.sun = sun;
        this.entities = entities;
        this.worldCursor = worldCursor;
        this.levelName = levelName;
        this.resources = 0;
        this.timeElapsed = 0;
    }

    public Node getEntities() {
        return entities;
    }

    public Node getLights() {
        return lights;
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

    public Light getSun() {
        return this.sun;
    }
}
