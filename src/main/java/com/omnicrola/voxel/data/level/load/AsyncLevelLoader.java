package com.omnicrola.voxel.data.level.load;

import com.omnicrola.voxel.data.level.LevelData;
import com.omnicrola.voxel.data.level.LevelDefinition;
import com.omnicrola.voxel.data.level.LevelDefinitionRepository;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by Eric on 4/8/2016.
 */
public class AsyncLevelLoader {

    private static final Logger LOGGER = Logger.getLogger(AsyncLevelLoader.class.getName());

    private UUID levelToLoad;
    private ExecutorService threadPool;
    private List<ILoadingTaskFactory> parallelTaskFactories;
    private List<ILoadingTaskFactory> finalTaskFactories;

    private LevelDefinitionRepository levelDefinitionRepository;
    private UnitDefinitionRepository unitDefinitionRepository;
    private LevelData levelData;
    private List<Future<LevelData>> taskFutures;
    private List<Callable<LevelData>> finalTasks;

    public AsyncLevelLoader(List<ILoadingTaskFactory> parallelTaskFactories,
                            List<ILoadingTaskFactory> finalTaskFactories,
                            LevelDefinitionRepository levelDefinitionRepository,
                            UnitDefinitionRepository unitDefinitionRepository) {
        this.parallelTaskFactories = parallelTaskFactories;
        this.finalTaskFactories = finalTaskFactories;
        this.levelDefinitionRepository = levelDefinitionRepository;
        this.unitDefinitionRepository = unitDefinitionRepository;
    }

    public void setLevel(UUID levelToLoad) {
        this.levelToLoad = levelToLoad;
    }

    public void startLoading() {
        this.threadPool = Executors.newFixedThreadPool(4);
        LevelDefinition levelDefinition = levelDefinitionRepository.getLevel(this.levelToLoad);
        this.levelData = new LevelData(levelDefinition, unitDefinitionRepository);
        createLoadingTasks();
        createFinalTasks();
    }

    private void createLoadingTasks() {
        this.taskFutures = this.parallelTaskFactories.stream()
                .map(f -> f.build(levelData))
                .map(t -> threadPool.submit(t))
                .collect(Collectors.toList());
    }

    private void createFinalTasks() {
        this.finalTasks = this.finalTaskFactories
                .stream()
                .map(f -> f.build(levelData))
                .collect(Collectors.toList());
    }

    public void stop() {
        if (this.threadPool != null) {
            this.threadPool.shutdown();
        }
    }

    public boolean isFinished() {
        return this.taskFutures.stream().allMatch(f -> f.isDone());
    }

    public LevelData getLevelData() {
        return this.levelData;
    }

    public float updateLoadStatus() {
        float total = this.taskFutures.size();
        float finished = this.taskFutures.stream().filter(f -> f.isDone()).count();
        if (finished == total) {
            runFinalTasks();
        }
        return finished / total;
    }

    private void runFinalTasks() {
        for (Callable callable : this.finalTasks) {
            try {
                callable.call();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, null, e);
            }
        }
    }
}
