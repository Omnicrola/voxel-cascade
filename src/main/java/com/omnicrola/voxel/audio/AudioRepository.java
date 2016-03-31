package com.omnicrola.voxel.audio;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.omnicrola.voxel.data.units.AudioDefinition;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.world.IWorldNode;

import java.util.HashMap;

/**
 * Created by Eric on 3/28/2016.
 */
public class AudioRepository {
    private final HashMap<AudioDefinition, IAudioPlayer> audioNodes;
    IWorldNode worldNode;
    AssetManager assetManager;

    public AudioRepository(IWorldNode worldNode, AssetManager assetManager) {
        this.worldNode = worldNode;
        this.assetManager = assetManager;
        this.audioNodes = new HashMap<>();
    }

    public IAudioPlayer getSoundPlayer(AudioDefinition audioDefinition) {
        if (audioDefinition == null) {
            return NullAudioPlayer.NO_OP;
        }
        if (this.audioNodes.containsKey(audioDefinition)) {
            return this.audioNodes.get(audioDefinition);
        }
        String filename = GameConstants.DIR_AUDIO + audioDefinition.getFilename();
        AudioNode audioNode = new AudioNode(this.assetManager, filename);
        audioNode.setPositional(false);
        NodeAudioPlayer audioPlayer = new NodeAudioPlayer(audioNode);
        this.audioNodes.put(audioDefinition, audioPlayer);
        return audioPlayer;
    }
}
