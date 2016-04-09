package com.omnicrola.voxel.engine.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.data.ILevelManager;
import com.omnicrola.voxel.data.TeamId;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.world.IGameEntity;
import com.omnicrola.voxel.world.WorldManager;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by omnic on 2/6/2016.
 */
public class AnnihilationWinConditionState extends AbstractAppState {

    private WorldManager worldManager;
    private ILevelManager levelManager;
    private AppStateManager stateManager;

    public AnnihilationWinConditionState(WorldManager worldManager, ILevelManager levelManager) {
        this.worldManager = worldManager;
        this.levelManager = levelManager;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.stateManager = stateManager;
    }

    @Override
    public void update(float tpf) {
        LevelState currentLevel = this.levelManager.getCurrentLevel();
        if (currentLevel.hasStarted()) {
            List<IGameEntity> entities = worldManager.getAllUnits();
            List<TeamId> teams = currentLevel.getAllTeams();
            Optional<TeamId> firstTeamEliminated = teams.stream()
                    .filter(t -> isEliminated(entities, t))
                    .findFirst();
            if (firstTeamEliminated.isPresent()) {
                gameOver();
            }
        }
    }

    private boolean isEliminated(List<IGameEntity> allUnits, TeamId teamId) {
        Long livingUnits = allUnits.stream()
                .filter(e -> e.isAlive())
                .filter(e -> isOnTeam(e, teamId))
                .collect(Collectors.counting());
        return livingUnits <= 0;
    }

    private void gameOver() {
        setEnabled(false);
        this.stateManager.getState(GameOverState.class).setEnabled(true);
    }

    private boolean isOnTeam(IGameEntity unit, TeamId teamId) {
        return unit.getTeam().equals(teamId);
    }

}
