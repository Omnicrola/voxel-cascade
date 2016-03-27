package com.omnicrola.voxel.network;

import com.omnicrola.voxel.settings.GameConstants;

import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 * Created by omnic on 3/27/2016.
 */
public class BroadcastPacketParser {

    public static final int PLAYER_COUNT_INDEX = 1;
    public static final String DELIMITER = ";";

    public VoxelGameServer parse(DatagramPacket receivePacket) {
        InetAddress address = receivePacket.getAddress();
        int playerCount = getPlayerCount(receivePacket);
        return new VoxelGameServer(address, playerCount);
    }

    public DatagramPacket buildResponse(InetAddress address, int port, int playerCount) {
        String message = GameConstants.SERVER_DISCOVERY_RESPONSE + DELIMITER + playerCount;
        byte[] sendData = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
        return sendPacket;
    }

    private int getPlayerCount(DatagramPacket receivePacket) {
        String message = new String(receivePacket.getData()).trim();
        String[] data = message.split(DELIMITER);
        return Integer.valueOf(data[PLAYER_COUNT_INDEX]);
    }
}
