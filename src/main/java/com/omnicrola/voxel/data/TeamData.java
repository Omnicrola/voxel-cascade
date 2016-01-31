package com.omnicrola.voxel.data;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.omnicrola.voxel.data.level.TeamDefinition;

import java.io.IOException;

/**
 * Created by Eric on 1/21/2016.
 */
public class TeamData implements Savable {

    public static final TeamData NEUTRAL = makeTeamData(999);

    private static TeamData makeTeamData(int teamId) {
        TeamDefinition teamDefinition = new TeamDefinition() {{
            this.id = teamId;
        }};
        return new TeamData(teamDefinition);
    }

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

    @Override
    public void write(JmeExporter ex) throws IOException {
    }

    @Override
    public void read(JmeImporter im) throws IOException {
    }

    public int getId() {
        return teamId;
    }
}
