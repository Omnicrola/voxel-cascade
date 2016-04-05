package com.omnicrola.voxel.network.listeners;

import com.jme3.network.Client;
import com.omnicrola.voxel.commands.ICommandProcessor;
import com.omnicrola.voxel.network.AbstractMessageListener;
import com.omnicrola.voxel.network.messages.JoinLobbyMessage;

import java.util.logging.Logger;

/**
 * Created by omnic on 4/4/2016.
 */
public class ClientLobbyJoinMessageListener extends AbstractMessageListener<JoinLobbyMessage, Client> {

    private static final Logger LOGGER = Logger.getLogger(ClientLobbyJoinMessageListener.class.getName());
    private ICommandProcessor commandProcessor;

    public ClientLobbyJoinMessageListener(ICommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
    }

    @Override
    protected void processMessage(Client connection, JoinLobbyMessage message) {
        if(message.joinWasSuccessful){
        } else {
            
        }
    }
}
