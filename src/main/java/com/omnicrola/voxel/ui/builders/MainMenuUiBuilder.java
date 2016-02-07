package com.omnicrola.voxel.ui.builders;

import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.controllers.MainMenuScreenController;
import de.lessvoid.nifty.builder.*;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;

/**
 * Created by Eric on 1/24/2016.
 */
public class MainMenuUiBuilder {

    public static void build(IGameContainer gameContainer) {
        String screenName = UiScreen.MAIN_MENU.toString();
        final MainMenuScreenController mainMenuScreenController = new MainMenuScreenController(gameContainer);

        gameContainer.gui().build().screen(screenName, new ScreenBuilder(screenName) {{
            controller(mainMenuScreenController);
            layer(new LayerBuilder("background") {
                {
                    childLayoutCenter();
                    backgroundColor("#000f");
                    image(new ImageBuilder() {{
                        filename("Interface/main-menu-bg.png");
                    }});
                }
            });
            layer(new LayerBuilder("foreground") {{
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
                        text("Voxel Cascade");
                        font(UiConstants.DEFAULT_FONT);
                        height("100%");
                        width("100%");
                    }});
                }});

                panel(new PanelBuilder("panel_mid") {{
                    childLayoutCenter();
                    alignCenter();
                    height("50%");
                    width("75%");

                    // add text
                    text(new TextBuilder() {{
                        text("A strategy game involving small colored cubes.");
                        font(UiConstants.DEFAULT_FONT);
                        wrap(true);
                        height("100%");
                        width("100%");
                    }});
                }});

                panel(new PanelBuilder("panel_bottom") {{
                    childLayoutHorizontal();
                    alignCenter();
                    height("25%");
                    width("75%");

                    panel(new PanelBuilder("panel_bottom_left") {{
                        childLayoutCenter();
                        valignCenter();
                        height("50%");
                        width("50%");

                        // add control
                        control(new ButtonBuilder(UiToken.BUTTON_START.toString(), "Start") {{
                            alignCenter();
                            valignCenter();
                            height("50%");
                            width("50%");
                        }});
                    }});

                    panel(new PanelBuilder("panel_bottom_right") {{
                        childLayoutCenter();
                        valignCenter();
                        height("50%");
                        width("50%");

                        // add control
                        control(new ButtonBuilder("QuitButton", "Quit") {{
                            alignCenter();
                            valignCenter();
                            height("50%");
                            width("50%");
                        }});
                    }});
                }}); // panel added
            }});
        }});
    }
}
