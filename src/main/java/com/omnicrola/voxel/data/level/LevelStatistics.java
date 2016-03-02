package com.omnicrola.voxel.data.level;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.ui.data.TeamStatistics;
import com.omnicrola.voxel.util.VoxelUtil;

import java.util.*;

/**
 * Created by Eric on 2/6/2016.
 */
public class LevelStatistics {

    private final Map<TeamData, TeamStatistics> teamStatistics;
    private float timeElapsed;

    public LevelStatistics() {
        this.teamStatistics = new HashMap<>();
        this.timeElapsed = 0;
    }

    public List<TeamStatistics> getTeamStatistics() {
        return new ArrayList<>(this.teamStatistics.values());
    }

    public float getTimeElapsed() {
        return timeElapsed;
    }

    public void addEntity(Spatial entity) {
        Optional<TeamStatistics> stats = getStatsForUnit(entity);
        if (stats.isPresent()) {
            if (isStructure(entity)) {
                stats.get().increaseStructuresBuilt();
            }
            if (isUnit(entity)) {
                stats.get().incrementUnitsBuilt();
            }
        }
    }

    private Optional<TeamStatistics> getStatsForUnit(Spatial entity) {
        TeamData teamData = entity.getUserData(EntityDataKeys.TEAM_DATA);
        if (teamData == null) {
            return Optional.empty();
        }
        return Optional.of(getTeamStatistics(teamData));
    }

    private boolean isUnit(Spatial entity) {
        return VoxelUtil.booleanData(entity, EntityDataKeys.IS_UNIT);
    }

    private boolean isStructure(Spatial unit) {
        return VoxelUtil.booleanData(unit, EntityDataKeys.IS_STRUCTURE);
    }

    public TeamStatistics getTeamStatistics(TeamData teamData) {
        TeamStatistics stats = this.teamStatistics.get(teamData);
        if (stats == null) {
            stats = new TeamStatistics("Team " + teamData.getId());
            this.teamStatistics.put(teamData, stats);
        }
        return stats;
    }

    public void entityDied(Spatial spatial) {
        Optional<TeamStatistics> statsForEntity = getStatsForUnit(spatial);
        if (statsForEntity.isPresent()) {
            if (isStructure(spatial)) {
                statsForEntity.get().increaseStructuresLost();
            }
            if (isUnit(spatial)) {
                statsForEntity.get().increaseUnitsLost();
            }
        }
    }

    public void addTime(float seconds) {
        this.timeElapsed += seconds;
    }

    public void addResources(TeamData teamData, float additionalResources) {
        TeamStatistics teamStatistics = getTeamStatistics(teamData);
        teamStatistics.addResourcesAcquired(additionalResources);
    }

    public void useResources(TeamData teamData, float resourcesUsed) {
        TeamStatistics teamStatistics = getTeamStatistics(teamData);
        teamStatistics.useResources(resourcesUsed);
    }
}
