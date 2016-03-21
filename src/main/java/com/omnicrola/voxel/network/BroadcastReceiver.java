package com.omnicrola.voxel.network;

import com.omnicrola.voxel.settings.GameConstants;

import java.io.IOException;
import java.net.*;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by Eric on 3/20/2016.
 */
public class BroadcastReceiver {
    private static final Logger LOGGER = Logger.getLogger(BroadcastReceiver.class.getName());
    public static final int SOCKET_TIMEOUT = 5000;

    private final ExecutorService threadPool;
    private boolean isRunning;

    public BroadcastReceiver() {
        this.threadPool = Executors.newFixedThreadPool(4);
    }

    public void start() {
        this.isRunning = true;
        LOGGER.log(Level.INFO, "Listening for servers broadcasting...");
        try {
            List<InetAddress> addresses = getBroadcastAddresses();
            listenOnAddresses(addresses);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listenOnAddresses(List<InetAddress> addresses) throws IOException {
        for (InetAddress address : addresses) {
            MulticastSocket multicastSocket = new MulticastSocket(GameConstants.SERVER_BROADCAST_PORT);
            multicastSocket.setSoTimeout(SOCKET_TIMEOUT);
            multicastSocket.joinGroup(address);
            WaitForRecieveTask waitForRecieveTask = new WaitForRecieveTask(multicastSocket);
            this.threadPool.submit(waitForRecieveTask);
        }
    }

    private List<InetAddress> getBroadcastAddresses() throws SocketException {
        Enumeration<NetworkInterface> allInterfaces = NetworkInterface.getNetworkInterfaces();
        List<InetAddress> broadcastAddresses = Collections.list(allInterfaces)
                .stream()
                .map(i -> i.getInterfaceAddresses())
                .flatMap(l -> l.stream())
                .map(a -> a.getBroadcast())
                .collect(Collectors.toList());
        LOGGER.log(Level.FINE, "Listing for broadcasts on " + broadcastAddresses.size() + " channels");
        return broadcastAddresses;
    }

    public void stop() {
        this.isRunning = false;
        LOGGER.log(Level.INFO, "Stopped listening for server broadcasts.");
    }


    private class WaitForRecieveTask implements Runnable {
        private MulticastSocket multicastSocket;

        public WaitForRecieveTask(MulticastSocket multicastSocket) {
            this.multicastSocket = multicastSocket;
        }

        @Override
        public void run() {
            try {
                byte[] buffer = new byte[256];
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                multicastSocket.receive(datagramPacket);
                System.out.println("RECIEVED BROADCAST: " + new String(datagramPacket.getData()));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (isRunning) {
                    threadPool.submit(this);
                }

            }
        }
    }
}
