package com.omnicrola.voxel.ui.builders;

import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.controllers.GameSettingsScreenController;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;

/**
 * Created by omnic on 4/2/2016.
 */
public class GameSettingsUiBuilder extends AbstractGuiBuilder {
    @Override
    public void build(UiAdapter uiAdapter) {

        GameSettingsScreenController controller = new GameSettingsScreenController(uiAdapter);
        String screenId = UiScreen.SETTINGS.toString();

        uiAdapter.addScreen(screenId, new ScreenBuilder(screenId, controller) {{
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
                childLayoutHorizontal();
                panel(new PanelBuilder("left-panel") {{
                    childLayoutVertical();
                    width(pixels(200));
                    height("*");
                    
                    control(createButton(UiToken.Settings.BUTTON_DISPLAY, "Graphics", 200, 40));
                    control(createButton(UiToken.Settings.BUTTON_DISPLAY, "Audio", 200, 40));
                    panel(spacerV(50));
                    control(createButton(UiToken.Settings.SAVE, "Save", 200, 40));
                    panel(spacerV(5));
                    control(createButton(UiToken.Settings.CANCEL, "Cancel", 200, 40));
                }});
                panel(new PanelBuilder("right-panel") {{
                    childLayoutVertical();
                    width("*");
                    height("*");
                    panel(createGraphicsPanel());
                    panel(createAudioPanel());
                }});
            }});
        }});
    }

    private PanelBuilder createAudioPanel() {
        return new PanelBuilder(UiToken.Settings.PANEL_AUDIO) {{
            childLayoutVertical();
            text(createText("Audio", 200));
        }};
    }

    private PanelBuilder createGraphicsPanel() {
        return new PanelBuilder(UiToken.Settings.PANEL_GRAPHICS) {{
            childLayoutVertical();
            text(createText("Graphics", 200));
        }};
    }
}
