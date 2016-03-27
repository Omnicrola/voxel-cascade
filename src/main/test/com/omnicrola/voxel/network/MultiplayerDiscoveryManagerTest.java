package com.omnicrola.voxel.network;

import com.omnicrola.voxel.server.network.ServerMulticastEmitter;
import org.junit.Test;

import java.util.List;

/**
 * Created by Eric on 3/24/2016.
 */
public class MultiplayerDiscoveryManagerTest {
    @Test
    public void testBroadcast() throws Exception {
        ServerMulticastEmitter serverMulticastEmitter = new ServerMulticastEmitter(new BroadcastPacketParser());
        serverMulticastEmitter.start();

        MultiplayerDiscoveryManager broadcastSocketPool = new MultiplayerDiscoveryManager(new BroadcastPacketParser());
        broadcastSocketPool.start();

        for (int i = 0; i < 10; i++) {
            System.out.println("Servers found: ");
            List<VoxelGameServer> activeServers = broadcastSocketPool.getActiveServers();
            activeServers.forEach(a -> System.out.print(a.getAddress() + ", "));
            System.out.println("");
            Thread.sleep(100);
        }

        serverMulticastEmitter.setIsRunning(false);
        broadcastSocketPool.setIsRunning(false);
    }
}