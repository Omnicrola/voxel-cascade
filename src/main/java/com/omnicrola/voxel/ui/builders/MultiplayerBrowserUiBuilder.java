package com.omnicrola.voxel.ui.builders;

import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.controllers.AvailableServerChangeObserver;
import com.omnicrola.voxel.ui.controllers.MultiplayerBrowserController;
import de.lessvoid.nifty.builder.*;
import de.lessvoid.nifty.controls.dropdown.builder.DropDownBuilder;
import de.lessvoid.nifty.tools.Color;

/**
 * Created by Eric on 3/20/2016.
 */
public class MultiplayerBrowserUiBuilder extends AbstractGuiBuilder {
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
            text(createText(UiToken.LABEL_SERVER_NAME, 100));
            text(createText(UiToken.LABEL_SERVER_IP, 100));
            text(createText(UiToken.LABEL_SERVER_PLAYERS, 100));
        }};
    }

    private PanelBuilder buttonPanel() {
        return new PanelBuilder() {
            {
                childLayoutHorizontal();
                control(createButton(UiToken.BUTTON_MULTIPLAYER_BROWSE_CANCEL, "Cancel", 50, 200));
                panel(new PanelBuilder() {{
                    width("*");
                }});
                control(createButton(UiToken.BUTTON_MULTIPLAYER_BROWSE_JOIN, "Join", 50, 200));
            }
        };
    }


    private ControlBuilder serverCombobox() {
        return new DropDownBuilder(UiToken.MULTIPLAYER_SERVER_LIST.toString()) {{
            width("*");
            height(pixels(30));
        }};
    }
}
