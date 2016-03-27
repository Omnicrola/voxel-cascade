package com.omnicrola.voxel.network;

import com.omnicrola.voxel.settings.GameConstants;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Eric on 3/20/2016.
 */
public class MultiplayerDiscoveryManager extends Thread {
    private static final Logger LOGGER = Logger.getLogger(MultiplayerDiscoveryManager.class.getName());
    private static final int SOCKET_TIMEOUT = 5000;
    public List<VoxelGameServer> activeServers = new ArrayList<>();
    private final Object LIST_MUTEX = new Object();
    private boolean isRunning = true;

    private BroadcastPacketParser serverPacketParser;

    public MultiplayerDiscoveryManager(BroadcastPacketParser serverPacketParser) {
        this.serverPacketParser = serverPacketParser;
    }

    public List<VoxelGameServer> getActiveServers() {
        synchronized (LIST_MUTEX) {
            return new ArrayList<>(this.activeServers);
        }
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public void run() {
        // Find the server using UDP broadcast

        while (this.isRunning) {
            try {
                final DatagramSocket socket = new DatagramSocket();
                socket.setBroadcast(true);
                socket.setSoTimeout(SOCKET_TIMEOUT);

                broadcastToDefaultIp(socket);

                // Broadcast the message over all the network interfaces
                Enumeration<NetworkInterface> allInterfaces = NetworkInterface.getNetworkInterfaces();
                Collections.list(allInterfaces)
                        .stream()
                        .filter(i -> validInterface(i))
                        .map(i -> i.getInterfaceAddresses())
                        .flatMap(l -> l.stream())
                        .map(a -> a.getBroadcast())
                        .filter(a -> a != null)
                        .forEach(a -> sentRequestPacket(a, socket));

                LOGGER.log(Level.FINE, "Done looping over all network interfaces. Now waiting for a reply!");

                waitForResponse(socket);
                socket.close();
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
    }

    private void waitForResponse(DatagramSocket socket) throws IOException {
        byte[] receieverBuffer = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receieverBuffer, receieverBuffer.length);
        socket.receive(receivePacket);

        LOGGER.log(Level.FINE, "Client got response from : " + receivePacket.getAddress().getHostAddress());

        if (isResponseMessage(receivePacket)) {
            synchronized (LIST_MUTEX) {
                if (serverIsNotAlreadyListed(receivePacket.getAddress())) {
                    this.activeServers.add(parseData(receivePacket));
                    LOGGER.log(Level.FINE, "Added server : " + receivePacket.getAddress().getHostAddress());
                    System.out.println("Added server: " + receivePacket.getAddress().getHostAddress());
                }
            }
        } else {
            String data = new String(receivePacket.getData());
            String msg = "Received a malformed broadcast packet. \n" + data + " \n " + receivePacket.getAddress().getHostAddress();
            LOGGER.log(Level.WARNING, msg);
        }
    }

    private boolean serverIsNotAlreadyListed(InetAddress address) {
        for (VoxelGameServer server : this.activeServers) {
            if (server.getAddress().equals(address.getHostAddress())) {
                return false;
            }
        }
        return true;
    }

    private VoxelGameServer parseData(DatagramPacket receivePacket) {
        return this.serverPacketParser.parse(receivePacket);
    }

    private boolean isResponseMessage(DatagramPacket receivePacket) {
        return new String(receivePacket.getData()).trim().startsWith(GameConstants.SERVER_DISCOVERY_RESPONSE);
    }

    private void sentRequestPacket(InetAddress inetAddress, DatagramSocket broadcastSocket) {
        try {
            byte[] sendData = GameConstants.SERVER_DISCOVERY_REQUEST.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, inetAddress, GameConstants.SERVER_BROADCAST_PORT);
            broadcastSocket.send(sendPacket);
            LOGGER.log(Level.FINE, "Request packet sent to: " + inetAddress.getHostAddress());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    private boolean validInterface(NetworkInterface networkInterface) {
        try {
            return !networkInterface.isLoopback() && networkInterface.isUp();
        } catch (SocketException e) {
            LOGGER.log(Level.SEVERE, null, e);
            return false;
        }
    }

    private void broadcastToDefaultIp(DatagramSocket socket) {
        byte[] sendData = GameConstants.SERVER_DISCOVERY_REQUEST.getBytes();
        try {
            InetAddress address = InetAddress.getByName(GameConstants.SERVER_BROADCAST_DEFAULT_IP);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, GameConstants.SERVER_BROADCAST_PORT);
            socket.send(sendPacket);
            LOGGER.log(Level.FINE, "Request packet sent to: " + GameConstants.SERVER_BROADCAST_DEFAULT_IP + " (DEFAULT)");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }
}
