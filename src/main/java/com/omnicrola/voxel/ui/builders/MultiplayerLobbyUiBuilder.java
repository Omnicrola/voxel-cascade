package com.omnicrola.voxel.ui.builders;

import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.controllers.LobbyChangeObserver;
import com.omnicrola.voxel.ui.controllers.MultiplayerLobbyScreenController;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.tools.Color;

/**
 * Created by Eric on 3/27/2016.
 */
public class MultiplayerLobbyUiBuilder extends AbstractGuiBuilder {
    @Override
    public void build(UiAdapter uiAdapter) {
        String screenName = UiScreen.MULTIPLAYER_LOBBY.toString();
        MultiplayerLobbyScreenController lobbyController = new MultiplayerLobbyScreenController(uiAdapter);
        uiAdapter.addNetworkObserver(new LobbyChangeObserver(lobbyController));

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

    private PanelBuilder centerPanel() {
        return new PanelBuilder() {{
            childLayoutVertical();
            panel(spacerV(10));
            panel(new PanelBuilder() {{
                childLayoutVertical();
                panel(serverInformationPanel());
                panel(buttonPanel());
            }});
            panel(spacerV(10));
        }};
    }

    private PanelBuilder serverInformationPanel() {
        return new PanelBuilder() {{
            childLayoutVertical();
            text(createText(UiToken.LABEL_SERVER_IP, 100));
            text(createText(UiToken.LABEL_SERVER_NAME, 100));
            text(createText(UiToken.LABEL_SERVER_PLAYERS, 100));
        }};
    }

    private PanelBuilder buttonPanel() {
        return new PanelBuilder() {{
            childLayoutHorizontal();
            control(createButton(UiToken.BUTTON_MULTIPLAYER_LOBBY_CANCEL, "Cancel", 200, 50));
            panel(new PanelBuilder() {{
                width("*");
            }});
            control(createButton(UiToken.BUTTON_MULTIPLAYER_LOBBY_JOIN, "JOIN", 200, 50));
        }};
    }
}
