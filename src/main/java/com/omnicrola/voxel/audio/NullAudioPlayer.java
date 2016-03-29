package com.omnicrola.voxel.audio;

/**
 * Created by Eric on 3/28/2016.
 */
public class NullAudioPlayer implements IAudioPlayer {
    public static final IAudioPlayer NO_OP = new NullAudioPlayer();

    private NullAudioPlayer() {
    }

    @Override
    public void playNewInstance() {

    }
}
