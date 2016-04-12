package com.omnicrola.voxel.data.level.load;

import com.omnicrola.voxel.data.level.LevelData;
import com.omnicrola.voxel.data.level.LevelDefinition;
import com.omnicrola.voxel.data.level.LevelDefinitionRepository;
import com.omnicrola.voxel.data.level.LevelSettings;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;

import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by Eric on 4/8/2016.
 */
public class AsyncLevelLoader {

    private static final Logger LOGGER = Logger.getLogger(AsyncLevelLoader.class.getName());

    private LevelSettings levelSettings;
    private ExecutorService threadPool;
    private List<ILoadingTaskFactory> parallelTaskFactories;
    private List<ILoadingTaskFactory> finalTaskFactories;

    private LevelDefinitionRepository levelDefinitionRepository;
    private UnitDefinitionRepository unitDefinitionRepository;
    private LevelData levelData;
    private List<AbstractLoadTask> finalTasks;
    private List<AbstractLoadTask> tasksInProgress;
    private boolean isFinished;

    public AsyncLevelLoader(List<ILoadingTaskFactory> parallelTaskFactories,
                            List<ILoadingTaskFactory> finalTaskFactories,
                            LevelDefinitionRepository levelDefinitionRepository,
                            UnitDefinitionRepository unitDefinitionRepository) {
        this.parallelTaskFactories = parallelTaskFactories;
        this.finalTaskFactories = finalTaskFactories;
        this.levelDefinitionRepository = levelDefinitionRepository;
        this.unitDefinitionRepository = unitDefinitionRepository;
    }

    public void setLevel(LevelSettings levelSettings) {
        this.levelSettings = levelSettings;
    }

    public void startLoading() {
        this.threadPool = Executors.newFixedThreadPool(4);
        LevelDefinition levelDefinition = levelDefinitionRepository.getLevel(this.levelSettings.getLevelId());
        this.levelData = new LevelData(levelDefinition, levelSettings, unitDefinitionRepository);
        createLoadingTasks();
        createFinalTasks();
    }

    private void createLoadingTasks() {
        this.tasksInProgress = this.parallelTaskFactories
                .stream()
                .map(f -> f.build(levelData))
                .collect(Collectors.toList());

        this.tasksInProgress.forEach(t -> threadPool.submit(t));
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
        return this.isFinished;
    }

    public LevelData getLevelData() {
        return this.levelData;
    }

    public float updateLoadStatus() {
        if (allTasksAreComplete()) {
            this.isFinished = true;
            runFinalTasks();
        }
        return getTaskProgress();
    }

    private boolean allTasksAreComplete() {
        return this.tasksInProgress.stream().allMatch(t -> t.isDone());
    }

    private float getTaskProgress() {
        OptionalDouble average = this.tasksInProgress.stream().mapToDouble(f -> f.percentDone()).average();
        return (float) (average.isPresent() ? average.getAsDouble() : 0.0);
    }

    private void runFinalTasks() {
        for (AbstractLoadTask loadTask : this.finalTasks) {
            try {
                loadTask.call();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, null, e);
            }
        }
    }
}
