package com.omnicrola.voxel.network;

import com.jme3.network.AbstractMessage;
import com.jme3.network.Client;
import com.omnicrola.util.Tuple;

import java.util.ArrayList;

/**
 * Created by Eric on 2/22/2016.
 */
public class ListenerMap extends ArrayList<Tuple<AbstractMessageListener, Class<? extends AbstractMessage>>> {
    public void attach(Client networkClient) {
        this.forEach(p -> attachListener(networkClient, p.getLeft(), p.getRight()));
    }

    private void attachListener(Client networkClient, AbstractMessageListener messageListener, Class<? extends AbstractMessage> messageClass) {
        networkClient.addMessageListener(messageListener, messageClass);
    }


}
