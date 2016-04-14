package com.omnicrola.voxel.data.level.load;

import com.omnicrola.voxel.data.level.LevelData;
import com.omnicrola.voxel.network.INetworkManager;

/**
 * Created by omnic on 4/13/2016.
 */
public class SetPlayerTeamLoadTask extends AbstractLoadTask {

    private double percentDone;

    public SetPlayerTeamLoadTask(LevelData levelData) {
        super(levelData);
    }

    @Override
    protected String getTaskName() {
        return "Set player team";
    }

    @Override
    protected void performLoading() {

        this.percentDone = 1.0f;
    }

    @Override
    public double percentDone() {
        return this.percentDone;
    }
}
