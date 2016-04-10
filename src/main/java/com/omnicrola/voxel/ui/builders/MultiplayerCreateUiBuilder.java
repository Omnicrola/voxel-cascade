package com.omnicrola.voxel.ui.builders;

import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.controllers.MultiplayerCreationScreenController;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.tools.Color;

/**
 * Created by Eric on 2/21/2016.
 */
public class MultiplayerCreateUiBuilder extends MultiplayerLobbyUiBuilder {
    public void build(UiAdapter uiAdapter) {

        String screenName = UiScreen.MULTIPLAYER_CREATE.toString();
        MultiplayerCreationScreenController multiplayerController = new MultiplayerCreationScreenController(uiAdapter);

        uiAdapter.addScreen(screenName, new ScreenBuilder(screenName) {{
            controller(multiplayerController);
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
}
