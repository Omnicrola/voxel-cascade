package com.omnicrola.voxel.network;

import com.jme3.network.serializing.Serializer;
import com.omnicrola.voxel.commands.*;
import com.omnicrola.voxel.data.level.UnitPlacement;
import com.omnicrola.voxel.network.messages.HandshakeMessage;
import com.omnicrola.voxel.network.messages.LoadLevelCommand;
import com.omnicrola.voxel.network.messages.SpawnStructureMessage;
import com.omnicrola.voxel.network.messages.SpawnUnitCommand;

/**
 * Created by Eric on 2/21/2016.
 */
public class MessageSerializationInitializer {

    public static void init() {
        Serializer.registerClass(HandshakeMessage.class);
        Serializer.registerClass(LoadLevelCommand.class);
        Serializer.registerClass(SpawnUnitCommand.class);
        Serializer.registerClass(SpawnStructureMessage.class);
        Serializer.registerClass(StartLevelCommand.class);

        Serializer.registerClass(MoveUnitsCommand.class);
        Serializer.registerClass(AttackTargetCommand.class);
        Serializer.registerClass(AttackLocationCommand.class);
        Serializer.registerClass(HarvestVoxelsCommand.class);
        Serializer.registerClass(BuildEntityWorldCommand.class);
        Serializer.registerClass(BuildStructureWorldCommand.class);

        Serializer.registerClass(UnitPlacement.class);
    }
}
