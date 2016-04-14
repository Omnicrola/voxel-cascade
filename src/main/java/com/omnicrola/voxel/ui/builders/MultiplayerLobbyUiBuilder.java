package com.omnicrola.voxel.ui.builders;

import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.controllers.MultiplayerLobbyScreenController;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.chatcontrol.builder.ChatBuilder;
import de.lessvoid.nifty.controls.listbox.builder.ListBoxBuilder;
import de.lessvoid.nifty.tools.Color;

/**
 * Created by Eric on 3/27/2016.
 */
public class MultiplayerLobbyUiBuilder extends AbstractGuiBuilder {
    @Override
    public void build(UiAdapter uiAdapter) {
        String screenName = UiScreen.MULTIPLAYER_LOBBY.toString();
        MultiplayerLobbyScreenController lobbyController = new MultiplayerLobbyScreenController(uiAdapter);

        uiAdapter.addScreen(screenName, new ScreenBuilder(screenName) {{
            controller(lobbyController);
            layer(new LayerBuilder("background") {

            });
            layer(new LayerBuilder("foreground") {
                {
                    childLayoutHorizontal();
                    backgroundColor(Color.BLACK);
                    panel(spacerH(10));
                    panel(centerPanel());
                    panel(spacerH(10));
                }
            });
        }});
    }

    protected PanelBuilder centerPanel() {
        return new PanelBuilder() {{
            childLayoutVertical();
            panel(spacerV(10));
            panel(new PanelBuilder() {{
                childLayoutVertical();
                panel(new PanelBuilder() {{
                    childLayoutHorizontal();
                    panel(serverInformationPanel());
                    panel(chooseLevelPanel());
                    panel(chooseTeamPanel());
                }});
                panel(chatPanel());
                panel(buttonPanel());
            }});
            panel(spacerV(10));
        }};
    }

    private PanelBuilder chatPanel() {
        return new PanelBuilder() {{
            childLayoutHorizontal();
            set("padding", "10px");
            control(new ChatBuilder(UiToken.Play.CHAT, 10) {{
                sendLabel("Send");
            }});
        }};
    }

    private PanelBuilder chooseLevelPanel() {
        return new PanelBuilder() {{
            childLayoutVertical();
            width(pixels(300));
            set("padding", "10px");
            text(createText("Levels", 100));
            control(new ListBoxBuilder(UiToken.Multiplayer.Lobby.LEVEL_LISTBOX) {{
                width("*");
                displayItems(5);
                selectionModeSingle();
                optionalVerticalScrollbar();
                hideHorizontalScrollbar();
            }});
        }};
    }

    private PanelBuilder chooseTeamPanel() {
        return new PanelBuilder() {{
            childLayoutVertical();
            width(pixels(200));
            set("padding", "10px");

            text(createText("Teams", 100));
            control(new ListBoxBuilder(UiToken.Multiplayer.Lobby.TEAM_SELECTION_LISTBOX) {{
                width("*");
                displayItems(5);
                selectionModeSingle();
                optionalVerticalScrollbar();
                hideHorizontalScrollbar();
            }});
        }};
    }

    protected PanelBuilder serverInformationPanel() {
        return new PanelBuilder() {{
            childLayoutVertical();
            width(percentage(33));
            text(createText(UiToken.Multiplayer.Browse.LABEL_SERVER_IP, ":", 100));
            text(createText(UiToken.Multiplayer.Browse.LABEL_SERVER_NAME, ":", 100));
            text(createText(UiToken.Multiplayer.Browse.LABEL_SERVER_PLAYERS, ":", 100));
        }};
    }

    protected PanelBuilder buttonPanel() {
        return new PanelBuilder() {{
            childLayoutHorizontal();
            control(createButton(UiToken.Multiplayer.BUTTON_CANCEL, "Cancel", 200, 50));
            panel(new PanelBuilder() {{
                width("*");
            }});
            control(createButton(UiToken.Multiplayer.BUTTON_START, "Start", 200, 50));
        }};
    }
}
