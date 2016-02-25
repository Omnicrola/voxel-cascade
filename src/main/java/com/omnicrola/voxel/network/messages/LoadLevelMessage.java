package com.omnicrola.voxel.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.omnicrola.voxel.data.level.LevelDefinition;
import com.omnicrola.voxel.data.level.LevelDefinitionRepository;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.ui.IUiManager;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.world.MessagePackage;
import com.omnicrola.voxel.world.IWorldMessage;

import java.util.UUID;

/**
 * Created by Eric on 2/24/2016.
 */
@Serializable
public class LoadLevelMessage extends AbstractMessage implements IWorldMessage {
    private String levelUuid;

    {

        setReliable(true);
    }

    private long targetTic;

    public LoadLevelMessage() {
    }

    public LoadLevelMessage(String levelUuid) {
        this.levelUuid = levelUuid;
    }

    @Override
    public void execute(MessagePackage messagePackage) {
        LevelDefinitionRepository levelDefinitionRepository = messagePackage.getLevelDefinitionRepository();
        LevelDefinition level = levelDefinitionRepository.getLevel(UUID.fromString(this.levelUuid));

        ITerrainManager terrainManager = messagePackage.getVoxelTerrainManager();
        terrainManager.globalReset();
        terrainManager.load(level.getTerrain());
        IUiManager uiManager = messagePackage.getUiManager();
        uiManager.changeScreen(UiScreen.ACTIVE_PLAY);
    }

    @Override
    public long getTargetTic() {
        return this.targetTic;
    }
}
