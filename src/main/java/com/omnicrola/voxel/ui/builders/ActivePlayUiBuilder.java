package com.omnicrola.voxel.ui.builders;

import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.controllers.ActivePlayScreenController;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;

/**
 * Created by Eric on 1/25/2016.
 */
public class ActivePlayUiBuilder {
    public static void build(IGameContainer gameContainer) {
        String screenName = UiScreen.ACTIVE_PLAY.toString();
        final ActivePlayScreenController activePlayScreenController = new ActivePlayScreenController();

        gameContainer.gui().build().screen(screenName, new ScreenBuilder(screenName) {{
            controller(activePlayScreenController);
            layer(new LayerBuilder("foreground") {{
                childLayoutVertical();
                backgroundColor(UiConstants.Color.TRANSPARENT);

                panel(new PanelBuilder("panel-right") {{
                    childLayoutVertical();
                    alignRight();
                    width("20%");
                    height(UiConstants.Size.ONE_HUNDRED);

                    text(new TextBuilder() {{
                        backgroundColor(UiConstants.Color.DARK_GRAY);
                        text("Time Elapsed");
                        font(UiConstants.DEFAULT_FONT);
                        width(UiConstants.Size.ONE_HUNDRED);
                        height("10%");
                    }});

                    panel(new PanelBuilder("selection-panel") {{
                        backgroundColor(UiConstants.Color.LIGHT_GRAY);
                        childLayoutVertical();
                        height("70%");
                        text(new TextBuilder() {{
                            text("selection here");
                            font(UiConstants.DEFAULT_FONT);
                            width(UiConstants.Size.ONE_HUNDRED);
                            height("10%");
                        }});
                    }});

                    panel(new PanelBuilder("action-panel") {{
                        backgroundColor(UiConstants.Color.DARK_GREEN);
                        childLayoutVertical();
                        width(UiConstants.Size.ONE_HUNDRED);
                        height("20%");

                        panel(new PanelBuilder("action-row-1") {{
                            childLayoutHorizontal();
                            width(UiConstants.Size.ONE_HUNDRED);
                            height(UiConstants.Size.ONE_THIRD);

                            control(actionButton(UiConstants.Buttons.ACTION_1, "1"));
                            control(actionButton(UiConstants.Buttons.ACTION_2, "2"));
                            control(actionButton(UiConstants.Buttons.ACTION_3, "3"));
                        }});

                        panel(new PanelBuilder("action-row-2") {{
                            childLayoutHorizontal();
                            width(UiConstants.Size.ONE_HUNDRED);
                            height(UiConstants.Size.ONE_THIRD);

                            control(actionButton(UiConstants.Buttons.ACTION_4, "4"));
                            control(actionButton(UiConstants.Buttons.ACTION_5, "5"));
                            control(actionButton(UiConstants.Buttons.ACTION_6, "6"));
                        }});

                        panel(new PanelBuilder("action-row-3") {{
                            childLayoutHorizontal();
                            width(UiConstants.Size.ONE_HUNDRED);
                            height(UiConstants.Size.ONE_THIRD);

                            control(actionButton(UiConstants.Buttons.ACTION_7, "7"));
                            control(actionButton(UiConstants.Buttons.ACTION_8, "8"));
                            control(actionButton(UiConstants.Buttons.ACTION_9, "9"));
                        }});

                    }});

                }});
            }});
        }});

    }

    private static ButtonBuilder actionButton(String id, String name) {
        return new ButtonBuilder(id, name) {{
            width(UiConstants.Size.ONE_THIRD);
            height(UiConstants.Size.ONE_HUNDRED);
            backgroundColor(UiConstants.Color.BLACK);
        }};
    }
}
