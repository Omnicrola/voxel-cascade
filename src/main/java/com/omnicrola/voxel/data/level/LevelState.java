package com.omnicrola.voxel.data.level;

import com.jme3.light.Light;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.input.WorldCursor;
import com.omnicrola.voxel.main.VoxelException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by omnic on 1/16/2016.
 */
public class LevelState implements ILevelStateRead {
    private final ArrayList<TeamData> teams;
    private final ArrayList<Light> lights;
    private Node entities;
    private Node terrain;
    private WorldCursor worldCursor;
    private String levelName;
    private float resources;
    private float timeElapsed;

    public LevelState(Node terrain, WorldCursor worldCursor, String levelName) {
        this.teams = new ArrayList<>();
        this.terrain = terrain;
        this.entities = new Node();
        this.lights = new ArrayList<>();
        this.worldCursor = worldCursor;
        this.levelName = levelName;
        this.resources = 0;
        this.timeElapsed = 0;
    }

    public void addTeam(TeamData team) {
        System.out.println("add team: " + team.getId());
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

    public List<Light> getLights() {
        return this.lights;
    }

    public TeamData getPlayerTeam() {
        return this.teams.get(0);
    }

    public TeamData getTeamById(int teamId) {
        Optional<TeamData> first = this.teams
                .stream()
                .filter(t -> hasId(t, teamId))
                .findFirst();
        if (first.isPresent()) {
            return first.get();
        } else {
            throw new VoxelException("Requested teamID was not found for this level : " + teamId);
        }
    }

    private boolean hasId(TeamData teamData, int teamId) {
        return teamData.getId() == teamId;
    }

    public void addLight(Light light) {
        this.lights.add(light);
    }
}
