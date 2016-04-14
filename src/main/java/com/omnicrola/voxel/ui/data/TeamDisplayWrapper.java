package com.omnicrola.voxel.ui.data;

import com.omnicrola.voxel.data.level.TeamDefinition;

/**
 * Created by omnic on 4/13/2016.
 */
public class TeamDisplayWrapper {
    private TeamDefinition team;

    public TeamDisplayWrapper(TeamDefinition team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Team " + this.team.getId();
    }

    public TeamDefinition getTeam() {
        return team;
    }
}
