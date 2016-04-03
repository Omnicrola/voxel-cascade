package com.omnicrola.voxel.ui.builders;

import com.omnicrola.voxel.ui.UiAdapter;
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
public class GameOverUiBuilder implements IGuiBuilder {
    public void build(UiAdapter uiAdapter) {
        String screenName = UiScreen.GAME_OVER.toString();
        GameOverScreenController gameOverScreenController = new GameOverScreenController(uiAdapter);

        uiAdapter.addScreen(screenName, new ScreenBuilder(screenName) {{
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
                        text(new TextBuilder(UiToken.GameOver.ELAPSED_TIME) {{
                            text("--:--:--");
                            font(UiConstants.DEFAULT_FONT);
                        }});
                    }});
                    control(new ScrollPanelBuilder(UiToken.GameOver.TEAM_RESULTS_CONTAINER) {{
                        parameter("horizontal", "false");
                        childLayoutVertical();
                        height("*");
                        width("*");
                        style("autoscroll");
                        panel(new PanelBuilder(UiToken.GameOver.TEAM_RESULTS_PANEL) {{
                            childLayoutVertical();
                            x(pixels(0));
                            y(pixels(0));
                        }});
                    }});
                    panel(new PanelBuilder("button-panel") {{
                        width(percentage(100));
                        childLayoutVertical();
                        control(new ButtonBuilder(UiToken.GameOver.BUTTON_MAIN_MENU) {{
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
