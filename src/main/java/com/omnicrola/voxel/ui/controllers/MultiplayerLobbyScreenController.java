package com.omnicrola.voxel.ui.controllers;

import com.omnicrola.voxel.commands.ChangeScreenCommand;
import com.omnicrola.voxel.commands.StartMultiplayerGameCommand;
import com.omnicrola.voxel.data.level.LevelGeneratorTool;
import com.omnicrola.voxel.network.VoxelGameServer;
import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiScreen;
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

    @NiftyEventSubscriber(id = "button-cancel")
    public void triggerCancel(String id, ButtonClickedEvent buttonClickedEvent) {
        this.uiAdapter.sendCommand(new ChangeScreenCommand(UiScreen.MAIN_MENU));
    }

    @NiftyEventSubscriber(id = "button-start")
    public void createGame(String id, ButtonClickedEvent buttonClickedEvent) {
        StartMultiplayerGameCommand startGameCommand = new StartMultiplayerGameCommand(LevelGeneratorTool.BASIC_LEVEL_UUID);
        this.uiAdapter.sendCommand(startGameCommand);
    }

    public void setCurrentGame(VoxelGameServer multiplayerGame) {
        this.currentGame = multiplayerGame;
        ui().getElement(UiToken.Multiplayer.Browse.LABEL_SERVER_IP).setText("IP: " + multiplayerGame.getAddress());
        ui().getElement(UiToken.Multiplayer.Browse.LABEL_SERVER_NAME).setText("Name: " + multiplayerGame.getName());
        ui().getElement(UiToken.Multiplayer.Browse.LABEL_SERVER_PLAYERS).setText("Players: " + multiplayerGame.getPlayers());
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
