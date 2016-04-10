package com.omnicrola.voxel.ui.controllers;

import com.google.common.eventbus.Subscribe;
import com.omnicrola.voxel.commands.ChangeScreenCommand;
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
import com.omnicrola.voxel.ui.nifty.IUiPanel;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.builder.ControlBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.RadioButtonGroupStateChangedEvent;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.controls.radiobutton.builder.RadioButtonBuilder;

/**
 * Created by Eric on 3/27/2016.
 */
public class MultiplayerLobbyScreenController extends AbstractScreenController {
    protected UiAdapter uiAdapter;
    protected VoxelGameServer currentGame;

    public MultiplayerLobbyScreenController(UiAdapter uiAdapter) {
        this.uiAdapter = uiAdapter;
        VoxelEventBus.INSTANCE().register(this);
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

    @NiftyEventSubscriber(id = "lobby-choose-team-radio-group")
    public void teamSelected(final String id, final RadioButtonGroupStateChangedEvent event) {
        System.out.println("RadioButton [" + event.getSelectedId() + "] is now selected. The old selection was [" + event.getPreviousSelectedId() + "]");
    }


    @Subscribe
    public void setCurrentLobbyServer(MultiplayerLobbyJoinEvent event) {
        this.currentGame = event.getMultiplayerServer();
        updateLobbyInformation();
    }

    @Subscribe
    void setCurrentMap(MultiplayerLobbyMapEvent event) {
        LevelDefinition levelDefinition = event.getLevel();
        IUiPanel teamPanel = ui().getPanel(UiToken.Multiplayer.Lobby.TEAM_LIST_PANEL);
        teamPanel.removeAllChildren();

        levelDefinition.getTeams().forEach(t -> addTeam(t, teamPanel));
    }

    private void addTeam(TeamDefinition team, IUiPanel teamPanel) {
        teamPanel.addElement(new PanelBuilder() {{
            childLayoutHorizontal();
            control(createLabel(team.getName(), "60px"));
            control(new RadioButtonBuilder("team-" + team.getId()) {{
                group(UiToken.Multiplayer.Lobby.CHOOSE_TEAM_RADIO_GROUP);
            }});
        }});
    }

    private ControlBuilder createLabel(String name, String width) {
        return new LabelBuilder("", name) {{
            width(width);
        }};
    }

    protected void updateLobbyInformation() {
        ui().getElement(UiToken.Multiplayer.Browse.LABEL_SERVER_IP).setText("IP: " + currentGame.getAddress());
        ui().getElement(UiToken.Multiplayer.Browse.LABEL_SERVER_NAME).setText("Name: " + currentGame.getName());
        ui().getElement(UiToken.Multiplayer.Browse.LABEL_SERVER_PLAYERS).setText("Players: " + currentGame.getPlayers());
    }

    @Override
    protected void screenOpen() {
        ui().getElement(UiToken.Multiplayer.Lobby.LEVEL_LISTBOX).setVisible(false);
    }

    @Override
    protected void screenClose() {
    }
}
