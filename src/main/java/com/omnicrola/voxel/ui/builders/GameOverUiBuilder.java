package com.omnicrola.voxel.ui.builders;

import com.omnicrola.voxel.data.ILevelManager;
import com.omnicrola.voxel.jme.wrappers.IGameGui;
import com.omnicrola.voxel.jme.wrappers.IStateManager;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.controllers.GameOverScreenController;
import de.lessvoid.nifty.builder.*;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.scrollpanel.builder.ScrollPanelBuilder;
import de.lessvoid.nifty.tools.Color;

/**
 * Created by Eric on 2/6/2016.
 */
public class GameOverUiBuilder {
    public static void build(IGameGui gameGui, IStateManager stateManager, ILevelManager currentLevelProvider) {
        String screenName = UiScreen.GAME_OVER.toString();
        GameOverScreenController gameOverScreenController = new GameOverScreenController(stateManager,currentLevelProvider);

        gameGui.build().screen(screenName, new ScreenBuilder(screenName) {{
            controller(gameOverScreenController);
            layer(new LayerBuilder("foreground") {{
                backgroundColor(UiConstants.Colors.TRANSPARENT);
                childLayoutVertical();

                panel(new PanelBuilder("top-spacer") {{
                    width(percentage(100));
                    height(percentage(10));
                }});

                panel(new PanelBuilder("center-panel") {{
                    alignCenter();
                    childLayoutVertical();
                    backgroundColor(new Color(0, 0, 0, 0.6f));
                    onActiveEffect(new EffectBuilder("border") {{
                        effectValue("border", "5px");
                        color(Color.WHITE);
                    }});
                    width(percentage(90));
                    height(percentage(80));
                    text(new TextBuilder() {{
                        text("Game Over");
                        font(UiConstants.DEFAULT_FONT);
                        width(percentage(100));
                        height(pixels(20));
                    }});
                    text(new TextBuilder() {{
                        text("Team Results");
                        font(UiConstants.DEFAULT_FONT);
                        width(percentage(100));
                        height(pixels(20));
                    }});
                    panel(new PanelBuilder() {{
                        childLayoutHorizontal();
                        alignCenter();
                        width(percentage(90));
                        height(pixels(20));
                        text(new TextBuilder() {{
                            text("Elapsed: ");
                            font(UiConstants.DEFAULT_FONT);
                        }});
                        text(new TextBuilder(UiToken.ELAPSED_TIME.toString()) {{
                            text("--:--:--");
                            font(UiConstants.DEFAULT_FONT);
                        }});
                    }});
                    control(new ScrollPanelBuilder(UiToken.TEAM_RESULTS_CONTAINER.toString()) {{
                        parameter("horizontal", "false");
                        childLayoutVertical();
                        height("*");
                        width("*");
                        style("autoscroll");
                        panel(new PanelBuilder(UiToken.TEAM_RESULTS_PANEL.toString()) {{
                            childLayoutVertical();
                            x(pixels(0));
                            y(pixels(0));
                        }});
                    }});
                    panel(new PanelBuilder("button-panel") {{
                        width(percentage(100));
                        childLayoutVertical();
                        control(new ButtonBuilder(UiToken.BUTTON_MAIN_MENU.toString()) {{
                            label("Main Menu");
                            alignRight();
                            width(pixels(100));
                            height(pixels(40));
                        }});
                    }});

                }});

            }});
        }});
    }
}
