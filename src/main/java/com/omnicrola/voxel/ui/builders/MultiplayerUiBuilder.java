package com.omnicrola.voxel.ui.builders;

import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.controllers.MultiplayerScreenController;
import de.lessvoid.nifty.builder.*;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;

/**
 * Created by Eric on 2/21/2016.
 */
public class MultiplayerUiBuilder {
    public static void build(IGameContainer gameContainer) {

        String screenName = UiScreen.MULTIPLAYER_LOAD.toString();
        MultiplayerScreenController multiplayerController = new MultiplayerScreenController(gameContainer);

        gameContainer.gui().build().screen(screenName, new ScreenBuilder(screenName) {{
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

                    panel(new PanelBuilder("panel_bottom"){{
                        childLayoutCenter();
                        alignCenter();
                        height(percentage(75));
                        width(percentage(75));

                        text(new TextBuilder(){{
                            text("Loading...");
                            font(UiConstants.DEFAULT_FONT);
                        }});

                        control(new ButtonBuilder(UiToken.BUTTON_ABORT_SERVER_CONNECT.toString(), "Abort") {{
                            alignCenter();
                            valignCenter();
                            height("50%");
                            width("50%");
                        }});

                    }});
                }
            });
        }});
    }
}
