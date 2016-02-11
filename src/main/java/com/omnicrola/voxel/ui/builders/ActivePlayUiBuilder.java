package com.omnicrola.voxel.ui.builders;

import com.omnicrola.voxel.engine.states.ICurrentLevelProvider;
import com.omnicrola.voxel.jme.wrappers.IGameGui;
import com.omnicrola.voxel.ui.controllers.UiLevelChangeObserver;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.controllers.ActivePlayScreenController;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.scrollpanel.builder.ScrollPanelBuilder;

/**
 * Created by Eric on 1/25/2016.
 */
public class ActivePlayUiBuilder {
    public static void build(IGameGui gameGui, ICurrentLevelProvider levelProvider) {
        String screenName = UiScreen.ACTIVE_PLAY.toString();

        final ActivePlayScreenController activePlayScreenController = new ActivePlayScreenController();
        levelProvider.addObserver(new UiLevelChangeObserver(activePlayScreenController));

        gameGui.build().screen(screenName, new ScreenBuilder(screenName) {{
            controller(activePlayScreenController);
            layer(new LayerBuilder("foreground") {{
                childLayoutHorizontal();
                backgroundColor(UiConstants.Colors.TRANSPARENT);

                panel(new PanelBuilder("panel-main") {{
                    childLayoutVertical();
                    width(percentage(80));

                    panel(new PanelBuilder("header-panel") {{
                        width(percentage(100));
                        height(pixels(50));
                        childLayoutHorizontal();

                        text(new TextBuilder() {{
                            text("Resources: ");
                            font(UiConstants.DEFAULT_FONT);
                        }});
                        text(new TextBuilder(UiToken.RESOURCE_AMOUNT.toString()) {{
                            text("0");
                            font(UiConstants.DEFAULT_FONT);
                        }});
                    }});
                }});

                panel(new PanelBuilder("panel-right") {{
                    childLayoutVertical();
                    alignRight();
                    width(percentage(20));
                    height(UiConstants.Size.ONE_HUNDRED);

                    text(new TextBuilder() {{
                        backgroundColor(UiConstants.Colors.DARK_GRAY);
                        text("Time Elapsed");
                        font(UiConstants.DEFAULT_FONT);
                        width(UiConstants.Size.ONE_HUNDRED);
                        height("10%");
                    }});

                    panel(new PanelBuilder("selection-panel") {{
                        backgroundColor(UiConstants.Colors.LIGHT_GRAY);
                        childLayoutVertical();
                        height("*");
                        text(new TextBuilder() {{
                            text("Selected");
                            font(UiConstants.DEFAULT_FONT);
                            width(percentage(100));
                        }});
                        control(new ScrollPanelBuilder("selection-scroll-panel") {{
                            parameter("horizontal", "false");
                            childLayoutVertical();
                            height("*");
                            width("*");
                            style("autoscroll");
                            panel(new PanelBuilder(UiToken.SELECTION_PANEL.toString()) {{
                                childLayoutVertical();
                                x(pixels(0));
                                y(pixels(0));
                                text(new TextBuilder() {{
                                    text("Item 1");
                                    font(UiConstants.DEFAULT_FONT);
                                    width(percentage(100));
                                    height("20px");
                                }});
                                text(new TextBuilder() {{
                                    text("Item 2");
                                    font(UiConstants.DEFAULT_FONT);
                                    width(percentage(100));
                                    height("20px");
                                }});
                                text(new TextBuilder() {{
                                    text("Item 3");
                                    font(UiConstants.DEFAULT_FONT);
                                    width(percentage(100));
                                    height("20px");
                                }});
                            }});
                        }});
                    }});

                    panel(new PanelBuilder("action-panel") {{
                        backgroundColor(UiConstants.Colors.DARK_GREEN);
                        childLayoutVertical();
                        width(UiConstants.Size.ONE_HUNDRED);
                        height("20%");

                        panel(new PanelBuilder("action-row-1") {{
                            childLayoutHorizontal();
                            width(UiConstants.Size.ONE_HUNDRED);
                            height(UiConstants.Size.ONE_THIRD);

                            control(actionButton(UiToken.ACTION_1, "Move"));
                            control(actionButton(UiToken.ACTION_2, "Attack"));
                            control(actionButton(UiToken.ACTION_3, "Stop"));
                        }});

                        panel(new PanelBuilder("action-row-2") {{
                            childLayoutHorizontal();
                            width(UiConstants.Size.ONE_HUNDRED);
                            height(UiConstants.Size.ONE_THIRD);

                            control(actionButton(UiToken.ACTION_4, "4"));
                            control(actionButton(UiToken.ACTION_5, "5"));
                            control(actionButton(UiToken.ACTION_6, "6"));
                        }});

                        panel(new PanelBuilder("action-row-3") {{
                            childLayoutHorizontal();
                            width(UiConstants.Size.ONE_HUNDRED);
                            height(UiConstants.Size.ONE_THIRD);

                            control(actionButton(UiToken.ACTION_7, "7"));
                            control(actionButton(UiToken.ACTION_8, "8"));
                            control(actionButton(UiToken.ACTION_9, "9"));
                        }});
                    }});
                }});
            }});
        }});
    }

    private static ButtonBuilder actionButton(UiToken token, String name) {
        return new ButtonBuilder(token.toString(), name) {{
            width(percentage(33));
            height(percentage(100));
        }};
    }
}
