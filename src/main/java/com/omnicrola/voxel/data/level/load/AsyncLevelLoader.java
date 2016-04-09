package com.omnicrola.voxel.data.level.load;

import com.omnicrola.voxel.data.level.LevelData;
import com.omnicrola.voxel.data.level.LevelDefinition;
import com.omnicrola.voxel.data.level.LevelDefinitionRepository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * Created by Eric on 4/8/2016.
 */
public class AsyncLevelLoader {

    private UUID levelToLoad;
    private ExecutorService threadPool;
    private List<ILoadingTaskFactory> taskFactories;
    private LevelDefinitionRepository levelDefinitionRepository;
    private LevelData leveData;
    private List<Future<LevelData>> taskFutures;

    public AsyncLevelLoader(List<ILoadingTaskFactory> taskFactories,
                            LevelDefinitionRepository levelDefinitionRepository) {
        this.taskFactories = taskFactories;
        this.levelDefinitionRepository = levelDefinitionRepository;
    }

    public void setLevel(UUID levelToLoad) {
        this.levelToLoad = levelToLoad;
    }

    public void startLoading() {
        this.threadPool = Executors.newFixedThreadPool(4);
        LevelDefinition levelDefinition = levelDefinitionRepository.getLevel(this.levelToLoad);
        createLoadingTasks(levelDefinition);
    }

    private void createLoadingTasks(LevelDefinition levelDefinition) {
        this.leveData = new LevelData();
        this.taskFutures = this.taskFactories.stream()
                .map(f -> f.build(levelDefinition))
                .map(t -> threadPool.submit(t))
                .collect(Collectors.toList());
    }

    public void stop() {
        this.threadPool.shutdown();
    }

    public boolean isFinished() {
        return this.taskFutures.stream().allMatch(f -> f.isDone());
    }

    public LevelData getLevelData() {
        return this.leveData;
    }
}
