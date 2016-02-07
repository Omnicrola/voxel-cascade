package com.omnicrola.voxel.ui.builders;

import com.omnicrola.voxel.engine.states.IGameStatisticProvider;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.controllers.GameOverScreenController;
import de.lessvoid.nifty.builder.*;
import de.lessvoid.nifty.controls.scrollpanel.builder.ScrollPanelBuilder;
import de.lessvoid.nifty.tools.Color;

/**
 * Created by Eric on 2/6/2016.
 */
public class GameOverUiBuilder {
    public static void build(IGameContainer gameContainer, IGameStatisticProvider statisticProvider) {
        String screenName = UiScreen.GAME_OVER.toString();
        GameOverScreenController gameOverScreenController = new GameOverScreenController(statisticProvider);

        gameContainer.gui().build().screen(screenName, new ScreenBuilder(screenName) {{
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
                    control(new ScrollPanelBuilder("scroll-panel") {{
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

                }});

            }});
        }});
    }
}
