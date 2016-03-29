package com.omnicrola.voxel.audio;

import com.jme3.audio.AudioNode;

/**
 * Created by Eric on 3/28/2016.
 */
public class NodeAudioPlayer implements IAudioPlayer {
    AudioNode audioNode;

    public NodeAudioPlayer(AudioNode audioNode) {
        this.audioNode = audioNode;
    }

    @Override
    public void playNewInstance() {
        this.audioNode.playInstance();
    }
}
