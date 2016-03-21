package com.omnicrola.voxel.network;

import com.omnicrola.voxel.engine.IActionQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Eric on 3/20/2016.
 */
public class ServerBroadcastReceiver {
    private static final Logger LOGGER = Logger.getLogger(ServerBroadcastReceiver.class.getName());

    private final List<INetworkObserver> observers;
    private IActionQueue actionQueue;
    private BroadcastReceiver broadcastReceiverThread;

    public ServerBroadcastReceiver(IActionQueue actionQueue) {
        this.actionQueue = actionQueue;
        this.observers = new ArrayList<>();
    }

    public void start() {
        if (this.broadcastReceiverThread == null) {
            this.broadcastReceiverThread = new BroadcastReceiver();
            this.broadcastReceiverThread.start();
        } else {
            LOGGER.log(Level.WARNING, "Broadcast receiver already started.");
        }
    }

    public void stop() {
        if (this.broadcastReceiverThread != null) {
            this.broadcastReceiverThread.stop();
            this.broadcastReceiverThread = null;
        }
    }

    public void addObserver(INetworkObserver networkObserver) {
        this.observers.add(networkObserver);
    }
}
