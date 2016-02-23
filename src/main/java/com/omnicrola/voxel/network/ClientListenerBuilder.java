package com.omnicrola.voxel.network;

import com.omnicrola.util.Tuple;
import com.omnicrola.voxel.engine.IActionQueue;
import com.omnicrola.voxel.engine.states.WorldManagerState;
import com.omnicrola.voxel.network.listeners.ClientCommandListener;
import com.omnicrola.voxel.network.listeners.ClientHandshakeListener;
import com.omnicrola.voxel.network.messages.HandshakeMessage;
import com.omnicrola.voxel.network.messages.LoadLevelMessage;

/**
 * Created by Eric on 2/22/2016.
 */
public class ClientListenerBuilder {
    public ListenerMap build(ClientNetworkState clientNetworkState,
                             WorldManagerState worldManagerState,
                             IActionQueue actionQueue) {
        ListenerMap listeners = new ListenerMap();

        ClientHandshakeListener clientHandshakeListener = new ClientHandshakeListener(clientNetworkState);
        ClientCommandListener clientCommandListener = new ClientCommandListener(worldManagerState, actionQueue);

        listeners.add(new Tuple<>(clientHandshakeListener, HandshakeMessage.class));
        listeners.add(new Tuple<>(clientCommandListener, LoadLevelMessage.class));
        return listeners;
    }
}
