package com.omnicrola.voxel.data.level;

import com.jme3.light.Light;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.input.WorldCursor;

import java.util.ArrayList;

/**
 * Created by omnic on 1/16/2016.
 */
public class LevelState implements ILevelStateRead {
    private final ArrayList<TeamData> teams;
    private Node entities;
    private Node terrain;
    private WorldCursor worldCursor;
    private String levelName;
    private float resources;
    private float timeElapsed;
    private Light sun;
    private TeamData playerTeam;

    public LevelState(Node terrain, Light sun, WorldCursor worldCursor, String levelName) {
        this.teams = new ArrayList<>();
        this.terrain = terrain;
        this.entities = new Node();
        this.sun = sun;
        this.worldCursor = worldCursor;
        this.levelName = levelName;
        this.resources = 0;
        this.timeElapsed = 0;
    }

    public void addTeam(TeamData team) {
        this.teams.add(team);
    }

    public Node getUnits() {
        return entities;
    }

    public void addUnit(Spatial unit) {
        this.entities.attachChild(unit);
    }

    public void setResources(float resources) {
        this.resources = resources;
    }

    public void setTimeElapsed(float timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    @Override
    public String getLevelName() {
        return levelName;
    }

    @Override
    public float getResources() {
        return resources;
    }

    @Override
    public float getTimeElapsed() {
        return timeElapsed;
    }

    public Node getTerrain() {
        return this.terrain;
    }

    @Override
    public WorldCursor getWorldCursor() {
        return worldCursor;
    }

    public Light getSun() {
        return this.sun;
    }

    public TeamData getPlayerTeam() {
        return this.teams.get(0);
    }
}
