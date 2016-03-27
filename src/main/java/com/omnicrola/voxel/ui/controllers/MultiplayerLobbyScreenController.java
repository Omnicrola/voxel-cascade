package com.omnicrola.voxel.ui.controllers;

import com.omnicrola.voxel.engine.GlobalGameState;
import com.omnicrola.voxel.network.VoxelGameServer;
import com.omnicrola.voxel.ui.SubscriberLink;
import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.builders.AbstractScreenController;
import com.omnicrola.voxel.ui.controllers.observers.LobbyChangeObserver;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;

/**
 * Created by Eric on 3/27/2016.
 */
public class MultiplayerLobbyScreenController extends AbstractScreenController {
    private UiAdapter uiAdapter;
    private VoxelGameServer currentGame;
    private LobbyChangeObserver lobbyChangeObserver;

    public MultiplayerLobbyScreenController(UiAdapter uiAdapter) {
        this.uiAdapter = uiAdapter;
        this.lobbyChangeObserver = new LobbyChangeObserver(this);
    }

    @NiftyEventSubscriber(id = "BUTTON_MULTIPLAYER_LOBBY_CANCEL")
    @SubscriberLink(UiToken.BUTTON_MULTIPLAYER_LOBBY_CANCEL)
    public void triggerCancel(String id, ButtonClickedEvent buttonClickedEvent) {
        this.uiAdapter.transitionTo(GlobalGameState.MULTIPLAYER_BROWSE);
    }

    @NiftyEventSubscriber(id = "BUTTON_MULTIPLAYER_LOBBY_JOIN")
    @SubscriberLink(UiToken.BUTTON_MULTIPLAYER_LOBBY_JOIN)
    public void triggerJoin(String id, ButtonClickedEvent buttonClickedEvent) {
        this.uiAdapter.transitionTo(GlobalGameState.MULTIPLAYER_CREATE);
    }

    public void setCurrentGame(VoxelGameServer multiplayerGame) {
        this.currentGame = multiplayerGame;
        ui().getElement(UiToken.LABEL_SERVER_IP).setText("IP: " + multiplayerGame.getAddress());
        ui().getElement(UiToken.LABEL_SERVER_NAME).setText("Name: " + multiplayerGame.getName());
        ui().getElement(UiToken.LABEL_SERVER_PLAYERS).setText("Players: " + multiplayerGame.getPlayers());
    }

    @Override
    protected void screenOpen() {
        this.uiAdapter.addNetworkObserver(this.lobbyChangeObserver);
    }

    @Override
    protected void screenClose() {
        this.uiAdapter.removeNetworkObserver(this.lobbyChangeObserver);
    }
}
