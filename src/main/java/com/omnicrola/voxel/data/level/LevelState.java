package com.omnicrola.voxel.data.level;

import com.jme3.light.Light;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.IDisposable;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.input.WorldCursor;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.main.VoxelException;
import com.omnicrola.voxel.terrain.VoxelTerrainControl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Created by omnic on 1/16/2016.
 */
public class LevelState implements ILevelStateRead, IDisposable {
    private final ArrayList<TeamData> teams;
    private final ArrayList<Light> lights;
    private Node units;
    private Node terrain;
    private WorldCursor worldCursor;
    private String levelName;
    private float resources;
    private float timeElapsed;
    private IGameContainer gameContainer;
    private boolean isActive;

    public LevelState(Node terrain, WorldCursor worldCursor, String levelName) {
        this.isActive = false;
        this.teams = new ArrayList<>();
        this.terrain = terrain;
        this.units = new Node();
        this.lights = new ArrayList<>();
        this.worldCursor = worldCursor;
        this.levelName = levelName;
        this.resources = 0;
        this.timeElapsed = 0;
    }

    public void addTeam(TeamData team) {
        this.teams.add(team);
    }

    public Node getUnits() {
        return units;
    }

    public void addUnit(Spatial unit) {
        this.units.attachChild(unit);
        if (isActive) {
            this.gameContainer.physics().add(unit);
        }
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
}
