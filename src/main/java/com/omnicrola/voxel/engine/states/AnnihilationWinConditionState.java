package com.omnicrola.voxel.engine.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.entities.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by omnic on 2/6/2016.
 */
public class AnnihilationWinConditionState extends AbstractAppState {

    private ArrayList<TeamData> teams;
    private VoxelGameEngine voxelGameEngine;
    private AppStateManager stateManager;

    public AnnihilationWinConditionState(ArrayList<TeamData> teams) {
        this.teams = teams;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        this.stateManager = stateManager;
        super.initialize(stateManager, app);
        this.voxelGameEngine = (VoxelGameEngine) app;
    }

    @Override
    public void update(float tpf) {
//        WorldManagerState worldManagerState = this.stateManager.getState(WorldManagerState.class);
//        if (worldManagerState != null) {
//            List<Unit> units = worldManagerState.getAllUnits();
//            Optional<TeamData> firstTeamEliminated = this.teams.stream()
//                    .filter(t -> isEliminated(units, t))
//                    .findFirst();
//            if (firstTeamEliminated.isPresent()) {
//                gameOver();
//            }
//        }
    }

    private boolean isEliminated(List<Unit> allUnits, TeamData teamData) {
        Long livingStructures = allUnits.stream()
                .filter(s -> s.isAlive())
                .filter(s -> isOnTeam(s, teamData))
                .collect(Collectors.counting());
        return livingStructures <= 0;
    }

    private void gameOver() {
        setEnabled(false);
        this.stateManager.getState(GameOverState.class).setEnabled(true);
    }

    private boolean isOnTeam(Unit unit, TeamData teamData) {
        return unit.getTeam().equals(teamData);
    }

}
