package com.omnicrola.voxel.engine.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.util.VoxelUtil;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by omnic on 2/6/2016.
 */
public class AnnihilationWinConditionState extends AbstractAppState {

    private AppStateManager stateManager;
    private ArrayList<TeamData> teams;

    public AnnihilationWinConditionState(ArrayList<TeamData> teams) {
        this.teams = teams;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.stateManager = stateManager;
        this.setEnabled(true);
    }

    @Override
    public void update(float tpf) {
        gameOver();
//        ActivePlayState activePlayState = this.stateManager.getState(ActivePlayState.class);
//        if (activePlayState != null) {
//            ArrayList<Spatial> allUnits = activePlayState.getCurrentLevelState().getAllUnits();
//            Optional<TeamData> firstTeamEliminated = this.teams.stream()
//                    .filter(t -> isEliminated(allUnits, t))
//                    .findFirst();
//            if (firstTeamEliminated.isPresent()) {
//                gameOver();
//            }
//        }
    }

    private boolean isEliminated(ArrayList<Spatial> allUnits, TeamData teamData) {
        Long livingStructures = allUnits.stream()
                .filter(s -> isStructure(s))
                .filter(s -> isAlive(s))
                .filter(s -> isOnTeam(s, teamData))
                .collect(Collectors.counting());
        return livingStructures <= 0;
    }

    private void gameOver() {
        this.stateManager.detach(this);
        GameOverState gameOverState = this.stateManager.getState(GameOverState.class);
        gameOverState.setEnabled(true);
    }

    private boolean isOnTeam(Spatial s, TeamData teamData) {
        return getTeam(s).equals(teamData);
    }

    private TeamData getTeam(Spatial spatial) {
        TeamData teamData = spatial.getUserData(EntityDataKeys.TEAM_DATA);
        return teamData;
    }

    private boolean isAlive(Spatial spatial) {
        return VoxelUtil.floatData(spatial, EntityDataKeys.HITPOINTS) > 0;
    }

    private boolean isStructure(Spatial spatial) {
        return VoxelUtil.booleanData(spatial, EntityDataKeys.IS_STRUCTURE);
    }
}
