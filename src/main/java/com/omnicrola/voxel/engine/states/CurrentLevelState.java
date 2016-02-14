package com.omnicrola.voxel.engine.states;

import com.omnicrola.voxel.data.GameXmlDataParser;
import com.omnicrola.voxel.data.level.LevelDefinition;
import com.omnicrola.voxel.data.level.LevelDefinitionRepository;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.data.level.LevelStateFactory;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.terrain.ChunkHandlerFactory;
import com.omnicrola.voxel.terrain.build.PerlinNoiseGenerator;
import com.omnicrola.voxel.terrain.VoxelTerrainGenerator;
import com.omnicrola.voxel.ui.data.TeamStatistics;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Eric on 2/6/2016.
 */
public class CurrentLevelState extends VoxelGameState implements ICurrentLevelProvider {
    private LevelState currentLevelState;
    private ArrayList<ILevelChangeObserver> observers;
    private IGameContainer gameContainer;
    private GameXmlDataParser gameDataParser;
    private LevelDefinitionRepository levelDefinitions;
    private LevelStateFactory levelStateFactory;

    public CurrentLevelState(GameXmlDataParser gameDataParser) {
        this.gameDataParser = gameDataParser;
        this.observers = new ArrayList<>();
    }

    @Override
    protected void voxelInitialize(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;

        this.levelDefinitions = this.gameDataParser.loadLevels(GameConstants.LEVEL_DEFINITIONS);
        ChunkHandlerFactory chunkHandlerFactory = new ChunkHandlerFactory(gameContainer);
        VoxelTerrainGenerator voxelTerrainGenerator = new VoxelTerrainGenerator(chunkHandlerFactory, new PerlinNoiseGenerator());
        this.levelStateFactory = new LevelStateFactory(voxelTerrainGenerator, gameContainer);
    }

    @Override
    protected void voxelEnable(IGameContainer gameContainer) {
        if (this.currentLevelState != null) {
            this.currentLevelState.attachToWorld(this.gameContainer);
        }
    }

    @Override
    protected void voxelDisable(IGameContainer gameContainer) {
        if (this.currentLevelState != null) {
            this.currentLevelState.detatchFromWorld(this.gameContainer);
        }
    }

    @Override
    public void loadLevel(UUID levelId) {
        LevelDefinition levelDefinition = levelDefinitions.getLevel(levelId);
        loadLevel(levelDefinition);
    }

    public void loadLevel(LevelDefinition newLevelDefinition) {
        if (this.currentLevelState != null) {
            this.currentLevelState.detatchFromWorld(this.gameContainer);
            this.currentLevelState.dispose();
        }
        this.currentLevelState = this.levelStateFactory.create(newLevelDefinition);
        this.currentLevelState.attachToWorld(this.gameContainer);

        this.gameContainer.gui().setCameraRotation(newLevelDefinition.getCameraOrientation());
        this.gameContainer.gui().setCameraPosition(newLevelDefinition.getCameraPosition());
        notifyObserversOfLevelChange();
    }


    private void notifyObserversOfLevelChange() {
        this.observers.forEach(o -> o.levelChanged(this.currentLevelState));
    }

    @Override
    public LevelState getCurrentLevel() {
        return this.currentLevelState;
    }

    @Override
    public void addObserver(ILevelChangeObserver levelChangeObserver) {
        this.observers.add(levelChangeObserver);
    }


    public List<TeamStatistics> getTeamStatistics() {
        return this.currentLevelState.getTeamStatistics();
    }
}
