package com.omnicrola.voxel.data.level;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.TeamId;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.ui.data.TeamStatistics;
import com.omnicrola.voxel.util.VoxelUtil;

import java.util.*;

/**
 * Created by Eric on 2/6/2016.
 */
public class LevelStatistics {

    private final Map<TeamId, TeamStatistics> teamStatistics;
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
        TeamId teamId = entity.getUserData(EntityDataKeys.TEAM_DATA);
        if (teamId == null) {
            return Optional.empty();
        }
        return Optional.of(getTeamStatistics(teamId));
    }

    private boolean isUnit(Spatial entity) {
        return VoxelUtil.booleanData(entity, EntityDataKeys.IS_UNIT);
    }

    private boolean isStructure(Spatial unit) {
        return VoxelUtil.booleanData(unit, EntityDataKeys.IS_STRUCTURE);
    }

    public TeamStatistics getTeamStatistics(TeamId teamId) {
        TeamStatistics stats = this.teamStatistics.get(teamId);
        if (stats == null) {
            stats = new TeamStatistics("Team " + teamId.getId());
            this.teamStatistics.put(teamId, stats);
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

    public void addResources(TeamId teamId, float additionalResources) {
        TeamStatistics teamStatistics = getTeamStatistics(teamId);
        teamStatistics.addResourcesAcquired(additionalResources);
    }

    public void useResources(TeamId teamId, float resourcesUsed) {
        TeamStatistics teamStatistics = getTeamStatistics(teamId);
        teamStatistics.useResources(resourcesUsed);
    }
}
