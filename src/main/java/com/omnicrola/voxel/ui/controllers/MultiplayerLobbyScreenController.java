package com.omnicrola.voxel.ui.controllers;

import com.google.common.eventbus.Subscribe;
import com.omnicrola.voxel.commands.ChangeScreenCommand;
import com.omnicrola.voxel.commands.SelectMultiplayerLevelCommand;
import com.omnicrola.voxel.commands.SelectTeamCommand;
import com.omnicrola.voxel.commands.StartMultiplayerGameCommand;
import com.omnicrola.voxel.data.level.LevelDefinition;
import com.omnicrola.voxel.data.level.LevelGeneratorTool;
import com.omnicrola.voxel.data.level.TeamDefinition;
import com.omnicrola.voxel.eventBus.VoxelEventBus;
import com.omnicrola.voxel.eventBus.events.MultiplayerLobbyMapEvent;
import com.omnicrola.voxel.network.VoxelGameServer;
import com.omnicrola.voxel.network.events.MultiplayerLobbyJoinEvent;
import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.builders.AbstractScreenController;
import com.omnicrola.voxel.ui.data.LevelWrapper;
import com.omnicrola.voxel.ui.data.TeamDisplayWrapper;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.controls.ListBoxSelectionChangedEvent;

import java.util.List;

/**
 * Created by Eric on 3/27/2016.
 */
public class MultiplayerLobbyScreenController extends AbstractScreenController {
    protected UiAdapter uiAdapter;
    protected VoxelGameServer currentGame;

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

    @NiftyEventSubscriber(id = "team-select-listbox")
    public void teamSelected(final String id, final ListBoxSelectionChangedEvent event) {
        List<TeamDisplayWrapper> selection = event.getSelection();
        if (selection.size() > 0) {
            TeamDefinition team = selection.get(0).getTeam();
            uiAdapter.sendCommand(new SelectTeamCommand(team.getId()));
        }
    }

    @NiftyEventSubscriber(id = "level-select-listbox")
    public void selectedServerChanged(String id, ListBoxSelectionChangedEvent event) {
        List<LevelWrapper> selection = event.getSelection();
        if (selection.size() >= 1) {
            LevelDefinition levelDefinition = selection.get(0).getLevelDefinition();
            System.out.println("Selected : " + levelDefinition.getName());
            this.uiAdapter.sendCommand(new SelectMultiplayerLevelCommand(levelDefinition.getUuid()));
        }
    }

    @Subscribe
    public void setCurrentLobbyServer(MultiplayerLobbyJoinEvent event) {
        this.currentGame = event.getMultiplayerServer();
        updateLobbyInformation();
    }

    @Subscribe
    void setCurrentMap(MultiplayerLobbyMapEvent event) {
        LevelDefinition levelDefinition = event.getLevel();
        ListBox<TeamDisplayWrapper> teamListbox = ui().getListBox(UiToken.Multiplayer.Lobby.TEAM_SELECTION_LISTBOX);
        teamListbox.removeAllItems(teamListbox.getItems());
        levelDefinition.getTeams().forEach(team -> {
            TeamDisplayWrapper teamDisplayWrapper = new TeamDisplayWrapper(team);
            teamListbox.addItem(teamDisplayWrapper);
        });
    }

    protected void updateLobbyInformation() {
        ui().getElement(UiToken.Multiplayer.Browse.LABEL_SERVER_IP).setText("IP: " + currentGame.getAddress());
        ui().getElement(UiToken.Multiplayer.Browse.LABEL_SERVER_NAME).setText("Name: " + currentGame.getName());
        ui().getElement(UiToken.Multiplayer.Browse.LABEL_SERVER_PLAYERS).setText("Players: " + currentGame.getPlayers());
    }

    @Override
    protected void screenOpen() {
        VoxelEventBus.INSTANCE().register(this);
        ui().getElement(UiToken.Multiplayer.Lobby.LEVEL_LISTBOX).setVisible(false);
    }

    @Override
    protected void screenClose() {
        VoxelEventBus.INSTANCE().unregister(this);
    }
}
