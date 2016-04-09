package com.omnicrola.voxel.data.level;

import com.omnicrola.voxel.IDisposable;
import com.omnicrola.voxel.data.TeamId;
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
    private final ArrayList<TeamId> teams;
    private final LevelStatistics statistics;
    private final HashMap<TeamId, Float> resources;
    private String levelName;
    private boolean hasStarted;

    public LevelState(String levelName) {
        this.teams = new ArrayList<>();
        this.levelName = levelName;
        this.statistics = new LevelStatistics();
        this.resources = new HashMap<>();
    }

    public void addTeam(TeamId team) {
        this.teams.add(team);
        this.resources.put(team, 0f);
    }

    public String getLevelName() {
        return levelName;
    }

    public TeamId getPlayerTeam() {
        return this.teams.get(0);
    }

    public TeamId getTeamById(int teamId) {
        Optional<TeamId> first = this.teams
                .stream()
                .filter(t -> hasId(t, teamId))
                .findFirst();
        if (first.isPresent()) {
            return first.get();
        } else {
            throw new VoxelException("Requested teamID was not found for this level : " + teamId);
        }
    }

    private boolean hasId(TeamId teamData, int teamId) {
        return teamData.getId() == teamId;
    }

    @Override
    public void dispose() {

    }

    public List<TeamStatistics> getTeamStatistics() {
        return this.statistics.getTeamStatistics();
    }

    public TeamStatistics getTeamStatistics(TeamId teamId) {
        return this.statistics.getTeamStatistics(teamId);
    }

    public void addTime(float seconds) {
        this.statistics.addTime(seconds);
    }

    public float getTimeElapsed() {
        return this.statistics.getTimeElapsed();
    }

    public void addResouces(TeamId teamId, float additionalResources) {
        this.statistics.addResources(teamId, additionalResources);
        modifyResources(teamId, additionalResources);
        emitStatisticsChangeEvent();
    }

    private void modifyResources(TeamId teamId, float amount) {
        float currentResources = this.resources.get(teamId);
        this.resources.put(teamId, currentResources + amount);
    }

    public void removeResources(TeamId teamId, float resourcesUsed) {
        this.statistics.useResources(teamId, resourcesUsed);
        modifyResources(teamId, -resourcesUsed);
        emitStatisticsChangeEvent();
    }

    public float getResources(TeamId playerTeam) {
        return this.resources.get(playerTeam);
    }

    private void emitStatisticsChangeEvent() {
        VoxelEventBus.INSTANCE().post(new LevelStatisticChangeEvent(this));
    }

    public List<TeamId> getAllTeams() {
        return new ArrayList<>(this.teams);
    }

    public boolean hasStarted() {
        return this.hasStarted;
    }

    public void setHasStarted(boolean hasStarted) {
        this.hasStarted = hasStarted;
    }
}
