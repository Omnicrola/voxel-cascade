package com.omnicrola.voxel.ui.builders;

import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.controllers.AvailableServerChangeObserver;
import com.omnicrola.voxel.ui.controllers.MultiplayerBrowserController;
import de.lessvoid.nifty.builder.*;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.dropdown.builder.DropDownBuilder;
import de.lessvoid.nifty.tools.Color;

/**
 * Created by Eric on 3/20/2016.
 */
public class MultiplayerBrowserUiBuilder implements IGuiBuilder {
    @Override
    public void build(UiAdapter uiAdapter) {
        String screenName = UiScreen.MULTIPLAYER_BROWSE.toString();
        MultiplayerBrowserController multiplayerController = new MultiplayerBrowserController(uiAdapter);
        uiAdapter.addNetworkObserver(new AvailableServerChangeObserver(multiplayerController));

        uiAdapter.addScreen(screenName, new ScreenBuilder(screenName) {{
            controller(multiplayerController);
            layer(new LayerBuilder("background") {

            });
            layer(new LayerBuilder("foreground") {{
                childLayoutHorizontal();
                backgroundColor(Color.BLACK);
                panel(new PanelBuilder() {{
                    width(percentage(33));
                }});
                panel(new PanelBuilder() {{
                    width(percentage(34));
                    childLayoutVertical();
                    panel(new PanelBuilder() {{
                        height(percentage(10));
                    }});
                    panel(new PanelBuilder() {
                        {
                            height(percentage(80));
                            childLayoutVertical();

                            control(serverCombobox());
                            panel(serverInformationPanel());
                            panel(buttonPanel());
                        }
                    });
                    panel(new PanelBuilder() {{
                        height(percentage(10));
                    }});
                }});
                panel(new PanelBuilder() {{
                    width(percentage(33));
                }});
            }});
        }});
    }

    private PanelBuilder serverInformationPanel() {
        return new PanelBuilder() {{
            childLayoutVertical();
            text(createText(UiToken.LABEL_SERVER_NAME));
            text(createText(UiToken.LABEL_SERVER_IP));
            text(createText(UiToken.LABEL_SERVER_PLAYERS));
        }};
    }

    private TextBuilder createText(UiToken id) {
        return new TextBuilder(id.toString()) {{
            text(":");
            font(UiConstants.DEFAULT_FONT);
            width(pixels(100));
        }};
    }

    private PanelBuilder buttonPanel() {
        return new PanelBuilder() {{
            childLayoutHorizontal();
            control(new ButtonBuilder(UiToken.BUTTON_MULTIPLAYER_BROWSE_CANCEL.toString(), "Cancel") {{
                alignCenter();
                valignCenter();
                height(pixels(50));
                width(pixels(200));
            }});
            panel(new PanelBuilder() {{
                width("*");
            }});
            control(new ButtonBuilder(UiToken.BUTTON_MULTIPLAYER_BROWSE_JOIN.toString(), "Join") {{
                alignCenter();
                valignCenter();
                height(pixels(50));
                width(pixels(200));
            }});

        }};
    }

    private ControlBuilder serverCombobox() {
        return new DropDownBuilder(UiToken.MULTIPLAYER_SERVER_LIST.toString()){{
            width("*");
            height(pixels(30));
        }};
    }
}
