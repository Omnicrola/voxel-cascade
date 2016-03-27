package com.omnicrola.voxel.network;

import com.jme3.network.serializing.Serializer;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.commands.*;
import com.omnicrola.voxel.data.level.UnitPlacement;
import com.omnicrola.voxel.network.messages.HandshakeMessage;
import com.omnicrola.voxel.network.messages.SpawnStructureMessage;
import com.omnicrola.voxel.network.messages.SpawnUnitCommand;

/**
 * Created by Eric on 2/21/2016.
 */
public class MessageSerializationInitializer {

    public static void init() {
        Serializer.registerClass(HandshakeMessage.class);
        Serializer.registerClass(SpawnUnitCommand.class);
        Serializer.registerClass(SpawnStructureMessage.class);
        Serializer.registerClass(StartMultiplayerGameCommand.class);
        Serializer.registerClass(StartLevelCommand.class);

        Serializer.registerClass(OrderMoveToLocationCommand.class);
        Serializer.registerClass(OrderAttackTargetCommand.class);
        Serializer.registerClass(OrderAttackLocationCommand.class);
        Serializer.registerClass(OrderHarvestVoxelsCommand.class);
        Serializer.registerClass(OrderBuildEntityWorldCommand.class);
        Serializer.registerClass(OrderBuildStructureWorldCommand.class);
        Serializer.registerClass(OrderBuildVoxelsCommand.class);
        Serializer.registerClass(OrderStopWorldCommand.class);

        Serializer.registerClass(UnitPlacement.class);
        Serializer.registerClass(Vec3i.class);
    }
}
