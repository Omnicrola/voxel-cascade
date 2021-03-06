package com.omnicrola.voxel.ui.builders;

import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.controllers.MainMenuScreenController;
import de.lessvoid.nifty.builder.*;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;

/**
 * Created by Eric on 1/24/2016.
 */
public class MainMenuUiBuilder implements IGuiBuilder {

    public void build(UiAdapter uiAdapter) {
        String screenName = UiScreen.MAIN_MENU.toString();
        final MainMenuScreenController mainMenuScreenController = new MainMenuScreenController(uiAdapter);

        uiAdapter.addScreen(screenName, new ScreenBuilder(screenName) {{
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
                    height(percentage(50));
                    width(percentage(75));

                    // add text
                    text(new TextBuilder() {{
                        text("A strategy game involving small colored cubes.");
                        font(UiConstants.DEFAULT_FONT);
                        wrap(true);
                        height("*");
                        width("*");
                    }});
                }});

                panel(new PanelBuilder("panel_bottom") {{
                    childLayoutHorizontal();
                    alignCenter();
                    height(percentage(25));
                    width(percentage(75));

                    panel(new PanelBuilder("panel_bottom_left") {{
                        childLayoutVertical();
                        height(percentage(50));
                        width(percentage(50));

                        // add control
                        control(new ButtonBuilder(UiToken.MainMenu.NEW_GAME, "New Game") {{
                            width(percentage(50));
                        }});
                        // add control
                        control(new ButtonBuilder(UiToken.MainMenu.BROWSE_GAMES, "Join Game") {{
                            width(percentage(50));
                        }});
                    }});

                    panel(new PanelBuilder("panel_bottom_right") {{
                        childLayoutVertical();
                        height(percentage(50));
                        width(percentage(50));

                        control(new ButtonBuilder(UiToken.MainMenu.BUTTON_SETTINGS, "Settings") {{
                            width(percentage(50));
                        }});
                        control(new ButtonBuilder(UiToken.MainMenu.BUTTON_QUIT_GAME, "Quit") {{
                            width(percentage(50));
                        }});
                    }});
                }}); // panel added
            }});
        }});
    }
}
