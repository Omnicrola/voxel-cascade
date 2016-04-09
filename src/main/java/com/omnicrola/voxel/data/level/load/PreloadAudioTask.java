package com.omnicrola.voxel.data.level.load;

import com.omnicrola.voxel.audio.AudioRepository;
import com.omnicrola.voxel.data.level.LevelData;
import com.omnicrola.voxel.data.level.LevelDefinition;
import com.omnicrola.voxel.data.level.UnitPlacement;
import com.omnicrola.voxel.data.units.AudioDefinition;
import com.omnicrola.voxel.data.units.EntityAudioDefinition;
import com.omnicrola.voxel.data.units.UnitDefinition;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by Eric on 4/8/2016.
 */
public class PreloadAudioTask extends AbstractLoadTask {
    private final AudioRepository audioRepository;
    private final LevelDefinition levelDefinition;

    public PreloadAudioTask(AudioRepository audioRepository,
                            LevelDefinition levelDefinition,
                            LevelData levelData) {
        super(levelData);
        this.audioRepository = audioRepository;
        this.levelDefinition = levelDefinition;
    }

    @Override
    protected void performLoading() {
        this.levelDefinition.getUnitPlacements().stream()
                .map(placement -> getUnit(placement))
                .map(unit -> unit.getAudioDefinition())
                .flatMap(audioDefinition -> getSounds(audioDefinition))
                .filter(audioDefinition -> audioDefinition != null)
                .distinct()
                .forEach(audioDefinition -> load(audioDefinition));
    }

    private void load(AudioDefinition audioDefinition) {
        this.audioRepository.preload(audioDefinition);
    }

    private Stream<AudioDefinition> getSounds(EntityAudioDefinition audioDefinition) {
        return Arrays.asList(audioDefinition.getAmbientLoop(),
                audioDefinition.getDeathSound(),
                audioDefinition.getSpawnSound(),
                audioDefinition.getTakeDamageSound()).stream();
    }

    private UnitDefinition getUnit(UnitPlacement placement) {
        return this.levelData.unitDefinitionRepository.getUnitDefinition(placement.getUnitId());
    }
}
