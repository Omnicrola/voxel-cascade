package com.omnicrola.voxel.entities.control.weapon;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.audio.IAudioPlayer;
import com.omnicrola.voxel.entities.commands.IDeathAction;

/**
 * Created by Eric on 3/28/2016.
 */
public class PlayAudioDeathAction implements IDeathAction {

    IAudioPlayer deathSound;

    public PlayAudioDeathAction(IAudioPlayer deathSound) {
        this.deathSound = deathSound;
    }

    @Override
    public void destruct(Spatial parentSpatial) {
        this.deathSound.playNewInstance();
    }
}
