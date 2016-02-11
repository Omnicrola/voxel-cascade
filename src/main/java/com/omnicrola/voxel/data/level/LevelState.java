package com.omnicrola.voxel.data.level;

import com.jme3.light.Light;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.IDisposable;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.engine.states.AnnihilationWinConditionState;
import com.omnicrola.voxel.input.WorldCursor;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.main.VoxelException;
import com.omnicrola.voxel.terrain.VoxelTerrainControl;
import com.omnicrola.voxel.ui.data.TeamStatistics;

import java.util.*;

/**
 * Created by omnic on 1/16/2016.
 */
public class LevelState implements IDisposable {
    private final ArrayList<TeamData> teams;
    private final ArrayList<Light> lights;
    private final ArrayList<Spatial> allEntities;
    private final LevelStatistics statistics;
    private final HashMap<TeamData, Float> resources;
    private Node units;
    private Node terrain;
    private WorldCursor worldCursor;
    private String levelName;
    private IGameContainer gameContainer;
    private boolean isActive;

    public LevelState(Node terrain, WorldCursor worldCursor, String levelName) {
        this.isActive = false;
        this.teams = new ArrayList<>();
        this.terrain = terrain;
        this.units = new Node();
        this.lights = new ArrayList<>();
        this.worldCursor = worldCursor;
        this.allEntities = new ArrayList<>();
        this.levelName = levelName;
        this.statistics = new LevelStatistics();
        this.resources = new HashMap<>();
    }

    public void addTeam(TeamData team) {
        this.teams.add(team);
        this.resources.put(team, 0f);
    }

    public Node getUnitsNode() {
        return units;
    }

    public ArrayList<Spatial> getAllEntities() {
        return new ArrayList<>(allEntities);
    }

    public void addEntity(Spatial unit) {
        this.units.attachChild(unit);
        this.allEntities.add(unit);
        this.statistics.addEntity(unit);
        if (isActive) {
            this.gameContainer.physics().add(unit);
        }
    }

    public String getLevelName() {
        return levelName;
    }

    public Node getTerrainNode() {
        return this.terrain;
    }

    public WorldCursor getWorldCursor() {
        return worldCursor;
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

    @Override
    public void dispose() {
        this.getWorldCursor().dispose();
        recursivelyDisposeNode(this.terrain);
        recursivelyDisposeNode(this.units);
    }

    private void recursivelyDisposeNode(Node node) {
        Iterator<Spatial> children = node.getChildren().iterator();
        node.detachAllChildren();
        while (children.hasNext()) {
            Spatial child = children.next();
            if (child instanceof Node) {
                recursivelyDisposeNode((Node) child);
            }
        }
    }

    public void attachToWorld(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;
        this.isActive = true;
        this.gameContainer.world().attachUnits(this.units);
        this.gameContainer.world().attachTerrain(this.terrain);
        this.gameContainer.world().attachLights(this.lights);
        this.gameContainer.world().attach(this.worldCursor);
        this.gameContainer.addState(new AnnihilationWinConditionState(this.teams));
    }

    public void detatchFromWorld(IGameContainer gameContainer) {
        this.isActive = false;
        this.gameContainer.world().detatchUnits(this.units);
        this.gameContainer.world().detatchTerrain(this.terrain);
        this.gameContainer.world().detatchLights(this.lights);
        this.gameContainer.world().detatch(this.worldCursor);
    }

    public void createVoxel(byte voxel, Vec3i location) {
        VoxelTerrainControl control = this.terrain.getControl(VoxelTerrainControl.class);
        control.setVoxel(voxel, location);
    }

    public List<TeamStatistics> getTeamStatistics() {
        return this.statistics.getTeamStatistics();
    }

    public void unitDestroyed(Spatial spatial) {
        this.units.detachChild(spatial);
        this.allEntities.remove(spatial);
        this.statistics.entityDied(spatial);
    }

    public void addTime(float seconds) {
        this.statistics.addTime(seconds);
    }

    public float getTimeElapsed() {
        return this.statistics.getTimeElapsed();
    }

    public void addResouces(TeamData teamData, float additionalResources) {
        this.statistics.addResources(teamData, additionalResources);
        float currentResources = this.resources.get(teamData);
        this.resources.put(teamData, currentResources + additionalResources);
    }
}
