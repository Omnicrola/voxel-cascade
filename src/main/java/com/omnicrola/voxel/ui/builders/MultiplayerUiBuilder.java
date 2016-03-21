package com.omnicrola.voxel.ui.builders;

import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.controllers.MultiplayerScreenController;
import de.lessvoid.nifty.builder.*;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;

/**
 * Created by Eric on 2/21/2016.
 */
public class MultiplayerUiBuilder implements IGuiBuilder {
    public void build(UiAdapter uiAdapter) {

        String screenName = UiScreen.MULTIPLAYER_LOAD.toString();
        MultiplayerScreenController multiplayerController = new MultiplayerScreenController(uiAdapter);

        uiAdapter.addScreen(screenName, new ScreenBuilder(screenName) {{
            controller(multiplayerController);
            layer(new LayerBuilder("background") {
                {
                    childLayoutCenter();
                    backgroundColor("#000f");
                    image(new ImageBuilder() {{
                        filename("Interface/main-menu-bg.png");
                    }});
                }
            });

            layer(new LayerBuilder("foreground") {
                {
                    childLayoutVertical();
                    backgroundColor("#0000");

                    // panel added
                    panel(new PanelBuilder("panel_top") {{
                        childLayoutCenter();
                        alignCenter();
                        height("25%");
                        width("75%");

                        // add text
                        text(new TextBuilder() {{
                            text("Multiplayer");
                            font(UiConstants.DEFAULT_FONT);
                            height("100%");
                            width("100%");
                        }});
                    }});

                    panel(new PanelBuilder("panel_bottom") {{
                        childLayoutVertical();
                        alignCenter();
                        height(percentage(75));
                        width(percentage(75));

                        control(new ButtonBuilder(UiToken.BUTTON_START_MULTIPLAYER.toString(), "Start") {{
                            alignCenter();
                            valignCenter();
                            height("40%");
                            width("50%");
                        }});

                        control(new ButtonBuilder(UiToken.BUTTON_ABORT_SERVER_CONNECT.toString(), "Abort") {{
                            alignCenter();
                            valignCenter();
                            height("40%");
                            width("50%");
                        }});

                    }});
                }
            });
        }});
    }


}
