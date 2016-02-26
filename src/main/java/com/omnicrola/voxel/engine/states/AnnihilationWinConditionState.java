package com.omnicrola.voxel.engine.states;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.util.VoxelUtil;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by omnic on 2/6/2016.
 */
public class AnnihilationWinConditionState extends VoxelGameState {

    private ArrayList<TeamData> teams;
    private IGameContainer gameContainer;

    public AnnihilationWinConditionState(ArrayList<TeamData> teams) {
        this.teams = teams;
    }

    @Override
    protected void voxelInitialize(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;
        setEnabled(true);
    }

    @Override
    protected void voxelEnable(IGameContainer gameContainer) {

    }

    @Override
    protected void voxelDisable(IGameContainer gameContainer) {

    }

    @Override
    public void update(float tpf) {
        LevelManager currentLevelState = this.gameContainer.getState(LevelManager.class);
        if (currentLevelState != null) {
            ArrayList<Spatial> allUnits = currentLevelState.getCurrentLevel().getAllEntities();
            Optional<TeamData> firstTeamEliminated = this.teams.stream()
                    .filter(t -> isEliminated(allUnits, t))
                    .findFirst();
            if (firstTeamEliminated.isPresent()) {
                gameOver();
            }
        }
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
        setEnabled(false);
        this.gameContainer.enableState(GameOverState.class);
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
