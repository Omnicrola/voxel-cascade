package com.omnicrola.voxel.data.level;

import com.omnicrola.voxel.IDisposable;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.eventBus.VoxelEventBus;
import com.omnicrola.voxel.eventBus.events.LevelStatisticChangeEvent;
import com.omnicrola.voxel.main.VoxelException;
import com.omnicrola.voxel.ui.data.TeamStatistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Created by omnic on 1/16/2016.
 */
public class LevelState implements IDisposable {
    private final ArrayList<TeamData> teams;
    private final LevelStatistics statistics;
    private final HashMap<TeamData, Float> resources;
    private String levelName;
    private boolean hasStarted;

    public LevelState(String levelName) {
        this.teams = new ArrayList<>();
        this.levelName = levelName;
        this.statistics = new LevelStatistics();
        this.resources = new HashMap<>();
    }

    public void addTeam(TeamData team) {
        this.teams.add(team);
        this.resources.put(team, 0f);
    }

    public String getLevelName() {
        return levelName;
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

    @Override
    public void dispose() {

    }

    public List<TeamStatistics> getTeamStatistics() {
        return this.statistics.getTeamStatistics();
    }

    public TeamStatistics getTeamStatistics(TeamData teamData) {
        return this.statistics.getTeamStatistics(teamData);
    }

    public void addTime(float seconds) {
        this.statistics.addTime(seconds);
    }

    public float getTimeElapsed() {
        return this.statistics.getTimeElapsed();
    }

    public void addResouces(TeamData teamData, float additionalResources) {
        this.statistics.addResources(teamData, additionalResources);
        modifyResources(teamData, additionalResources);
        emitStatisticsChangeEvent();
    }

    private void modifyResources(TeamData teamData, float amount) {
        float currentResources = this.resources.get(teamData);
        this.resources.put(teamData, currentResources + amount);
    }

    public void removeResources(TeamData teamData, float resourcesUsed) {
        this.statistics.useResources(teamData, resourcesUsed);
        modifyResources(teamData, -resourcesUsed);
        emitStatisticsChangeEvent();
    }

    public float getResources(TeamData playerTeam) {
        return this.resources.get(playerTeam);
    }

    private void emitStatisticsChangeEvent() {
        VoxelEventBus.INSTANCE().post(new LevelStatisticChangeEvent(this));
    }

    public List<TeamData> getAllTeams() {
        return new ArrayList<>(this.teams);
    }

    public boolean hasStarted() {
        return this.hasStarted;
    }

    public void setHasStarted(boolean hasStarted) {
        this.hasStarted = hasStarted;
    }
}
