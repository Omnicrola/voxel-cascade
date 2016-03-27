package com.omnicrola.voxel.server.network;

import com.omnicrola.voxel.network.BroadcastPacketParser;
import com.omnicrola.voxel.settings.GameConstants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Eric on 3/24/2016.
 */
public class ServerMulticastEmitter extends Thread {
    private static final Logger LOGGER = Logger.getLogger(ServerMulticastEmitter.class.getName());

    private static final String BROADCAST_IP = "0.0.0.0";

    private boolean isRunning = true;
    private BroadcastPacketParser broadcastPacketParser;

    public ServerMulticastEmitter(BroadcastPacketParser broadcastPacketParser) {
        this.broadcastPacketParser = broadcastPacketParser;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public void run() {
        DatagramSocket socket;
        try {
            LOGGER.log(Level.INFO, "Server starting on " + BROADCAST_IP + ":" + GameConstants.SERVER_BROADCAST_PORT);
            socket = new DatagramSocket(GameConstants.SERVER_BROADCAST_PORT, InetAddress.getByName(BROADCAST_IP));
            socket.setBroadcast(true);

            while (this.isRunning) {
                LOGGER.log(Level.FINE, "Server waiting for broadcast packets...");
                DatagramPacket packetReceived = getDatagramPacket(socket);

                if (isDiscoveryRequest(packetReceived)) {
                    DatagramPacket sendPacket = this.broadcastPacketParser.buildResponse(packetReceived.getAddress(), packetReceived.getPort(), 0);
                    socket.send(sendPacket);
                    LOGGER.log(Level.FINE, "Sent packet back to : " + sendPacket.getAddress().getHostAddress());
                }
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private boolean isDiscoveryRequest(DatagramPacket packet) {
        return new String(packet.getData()).trim().equals(GameConstants.SERVER_DISCOVERY_REQUEST);
    }

    private DatagramPacket getDatagramPacket(DatagramSocket socket) throws IOException {
        byte[] recvBuf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
        socket.receive(packet);

        LOGGER.log(Level.FINE, "Server received discovery packet from " + packet.getAddress().getHostAddress());
        LOGGER.log(Level.FINE, "Packet data: " + new String(packet.getData()));

        return packet;
    }


}
