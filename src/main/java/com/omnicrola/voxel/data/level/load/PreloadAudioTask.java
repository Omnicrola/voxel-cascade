package com.omnicrola.voxel.data.level.load;

import com.omnicrola.voxel.audio.AudioRepository;
import com.omnicrola.voxel.data.level.LevelData;
import com.omnicrola.voxel.data.level.UnitPlacement;
import com.omnicrola.voxel.data.units.AudioDefinition;
import com.omnicrola.voxel.data.units.EntityAudioDefinition;
import com.omnicrola.voxel.data.units.UnitDefinition;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Eric on 4/8/2016.
 */
public class PreloadAudioTask extends AbstractLoadTask {

    private final AudioRepository audioRepository;
    private float totalSounds;
    private float soundsLoaded;

    public PreloadAudioTask(AudioRepository audioRepository,
                            LevelData levelData) {
        super(levelData);
        this.audioRepository = audioRepository;
    }

    @Override
    protected String getTaskName() {
        return "Pre-load Audio";
    }

    @Override
    protected void performLoading() {
        List<AudioDefinition> soundsToLoad = this.levelData.levelDefinition.getUnitPlacements().stream()
                .map(placement -> getUnit(placement))
                .map(unit -> unit.getAudioDefinition())
                .flatMap(audioDefinition -> getSounds(audioDefinition))
                .filter(audioDefinition -> audioDefinition != null)
                .distinct()
                .collect(Collectors.toList());

        this.totalSounds = soundsToLoad.size();
        soundsToLoad.forEach(audioDefinition -> load(audioDefinition));
    }

    @Override
    public double percentDone() {
        return soundsLoaded / totalSounds;
    }

    private void load(AudioDefinition audioDefinition) {
        this.audioRepository.preload(audioDefinition);
        this.soundsLoaded++;
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
