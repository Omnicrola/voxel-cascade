package com.omnicrola.voxel.data;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.omnicrola.voxel.data.level.TeamDefinition;

import java.io.IOException;

/**
 * Created by Eric on 1/21/2016.
 */
public class TeamId implements Savable {

    public static final TeamId NEUTRAL = create(999);

    public static TeamId create(int teamId) {
        TeamDefinition teamDefinition = new TeamDefinition() {{
            this.id = teamId;
        }};
        return new TeamId(teamDefinition);
    }

    private int teamId;

    public TeamId(TeamDefinition teamDefinition) {
        this.teamId = teamDefinition.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamId)) return false;

        TeamId teamId = (TeamId) o;

        return this.teamId == teamId.teamId;
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
