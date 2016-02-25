package com.omnicrola.voxel.network;

import com.jme3.network.serializing.Serializer;
import com.omnicrola.voxel.network.messages.HandshakeMessage;
import com.omnicrola.voxel.network.messages.LoadLevelMessage;

/**
 * Created by Eric on 2/21/2016.
 */
public class MessageSerializationInitializer {

    public static void init (){
        Serializer.registerClass(HandshakeMessage.class);
        Serializer.registerClass(LoadLevelMessage.class);
    }
}
