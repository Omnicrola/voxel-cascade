package com.omnicrola.voxel.data.level.load;

import com.omnicrola.voxel.data.level.LevelData;

/**
 * Created by omnic on 4/13/2016.
 */
public class SetPlayerTeamTaskFactory implements ILoadingTaskFactory {
    @Override
    public AbstractLoadTask build(LevelData levelData) {
        return new SetPlayerTeamLoadTask(levelData);
    }
}
