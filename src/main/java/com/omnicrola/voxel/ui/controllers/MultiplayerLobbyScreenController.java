package com.omnicrola.voxel.ui.controllers;

import com.google.common.eventbus.Subscribe;
import com.omnicrola.voxel.commands.ChangeScreenCommand;
import com.omnicrola.voxel.commands.StartMultiplayerGameCommand;
import com.omnicrola.voxel.data.level.LevelGeneratorTool;
import com.omnicrola.voxel.eventBus.VoxelEventBus;
import com.omnicrola.voxel.network.VoxelGameServer;
import com.omnicrola.voxel.network.events.MultiplayerLobbyJoinEvent;
import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.builders.AbstractScreenController;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;

/**
 * Created by Eric on 3/27/2016.
 */
public class MultiplayerLobbyScreenController extends AbstractScreenController {
    private UiAdapter uiAdapter;
    private VoxelGameServer currentGame;

    public MultiplayerLobbyScreenController(UiAdapter uiAdapter) {
        this.uiAdapter = uiAdapter;
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

    @Subscribe
    public void setCurrentLobbyServer(MultiplayerLobbyJoinEvent event) {
        this.currentGame = event.getMultiplayerServer();
        ui().getElement(UiToken.Multiplayer.Browse.LABEL_SERVER_IP).setText("IP: " + currentGame.getAddress());
        ui().getElement(UiToken.Multiplayer.Browse.LABEL_SERVER_NAME).setText("Name: " + currentGame.getName());
        ui().getElement(UiToken.Multiplayer.Browse.LABEL_SERVER_PLAYERS).setText("Players: " + currentGame.getPlayers());
    }

    @Override
    protected void screenOpen() {
        VoxelEventBus.INSTANCE().register(this);
    }

    @Override
    protected void screenClose() {
        VoxelEventBus.INSTANCE().unregister(this);
    }
}
