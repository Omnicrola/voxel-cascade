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
    private final HashMap<AudioDefinition, IAudioPlayer> audioPlayers;
    IWorldNode worldNode;
    AssetManager assetManager;

    public AudioRepository(IWorldNode worldNode, AssetManager assetManager) {
        this.worldNode = worldNode;
        this.assetManager = assetManager;
        this.audioPlayers = new HashMap<>();
    }

    public IAudioPlayer getSoundPlayer(AudioDefinition audioDefinition) {
        if (audioDefinition == null) {
            return NullAudioPlayer.NO_OP;
        }
        if (this.audioPlayers.containsKey(audioDefinition)) {
            return this.audioPlayers.get(audioDefinition);
        }
        NodeAudioPlayer audioPlayer = createAudioPlayer(audioDefinition);
        return audioPlayer;
    }

    private NodeAudioPlayer createAudioPlayer(AudioDefinition audioDefinition) {
        String filename = GameConstants.DIR_AUDIO + audioDefinition.getFilename();
        AudioNode audioNode = new AudioNode(this.assetManager, filename);
        audioNode.setPositional(false);
        NodeAudioPlayer audioPlayer = new NodeAudioPlayer(audioNode);
        this.audioPlayers.put(audioDefinition, audioPlayer);
        return audioPlayer;
    }

    public void preload(AudioDefinition audioDefinition) {
        if (!this.audioPlayers.containsKey(audioDefinition)) {
            createAudioPlayer(audioDefinition);
        }
    }
}
