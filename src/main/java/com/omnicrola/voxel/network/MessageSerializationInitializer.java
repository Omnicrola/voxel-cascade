package com.omnicrola.voxel.network;

import com.jme3.network.serializing.Serializer;
import com.omnicrola.voxel.data.level.UnitPlacement;
import com.omnicrola.voxel.network.messages.HandshakeMessage;
import com.omnicrola.voxel.network.messages.LoadLevelCommand;
import com.omnicrola.voxel.network.messages.SpawnStructureMessage;
import com.omnicrola.voxel.network.messages.SpawnUnitMessage;

/**
 * Created by Eric on 2/21/2016.
 */
public class MessageSerializationInitializer {

    public static void init (){
        Serializer.registerClass(HandshakeMessage.class);
        Serializer.registerClass(LoadLevelCommand.class);
        Serializer.registerClass(SpawnUnitMessage.class);
        Serializer.registerClass(SpawnStructureMessage.class);

        Serializer.registerClass(UnitPlacement.class);
    }
}
