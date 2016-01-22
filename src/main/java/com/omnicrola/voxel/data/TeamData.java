package com.omnicrola.voxel.data;

import com.omnicrola.voxel.data.level.TeamDefinition;

/**
 * Created by Eric on 1/21/2016.
 */
public class TeamData {

    private int teamId;

    public TeamData(TeamDefinition teamDefinition) {
        this.teamId = teamDefinition.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamData)) return false;

        TeamData teamData = (TeamData) o;

        return teamId == teamData.teamId;

    }

    @Override
    public int hashCode() {
        return teamId;
    }
}
