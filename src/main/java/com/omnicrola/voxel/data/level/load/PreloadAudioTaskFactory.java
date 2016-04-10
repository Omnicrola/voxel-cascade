package com.omnicrola.voxel.data.level.load;

import com.omnicrola.voxel.audio.AudioRepository;
import com.omnicrola.voxel.data.level.LevelData;

/**
 * Created by Eric on 4/8/2016.
 */
public class PreloadAudioTaskFactory implements ILoadingTaskFactory {
    private AudioRepository audioRepository;

    public PreloadAudioTaskFactory(AudioRepository audioRepository) {
        this.audioRepository = audioRepository;
    }

    @Override
    public AbstractLoadTask build(LevelData levelData) {
        return new PreloadAudioTask(audioRepository, levelData);
    }
}
