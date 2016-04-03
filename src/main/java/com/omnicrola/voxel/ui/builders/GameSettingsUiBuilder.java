package com.omnicrola.voxel.ui.builders;

import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.controllers.GameSettingsScreenController;
import de.lessvoid.nifty.builder.*;
import de.lessvoid.nifty.controls.checkbox.builder.CheckboxBuilder;
import de.lessvoid.nifty.controls.dropdown.builder.DropDownBuilder;
import de.lessvoid.nifty.controls.slider.builder.SliderBuilder;
import de.lessvoid.nifty.controls.tabs.builder.TabBuilder;
import de.lessvoid.nifty.controls.tabs.builder.TabGroupBuilder;

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
                panel(spacerH(10));
                panel(new PanelBuilder("center-panel") {
                    {
                        childLayoutVertical();
                        panel(spacerV(10));
                        panel(buildSettingsPanel());
                        panel(buildBottomButtonPanel());
                        panel(spacerV(10));
                    }
                });
                panel(spacerH(10));
            }});
        }});
    }

    private PanelBuilder buildBottomButtonPanel() {
        return new PanelBuilder("bottom-panel") {{
            childLayoutHorizontal();
            height(percentage(10));
            panel(new PanelBuilder() {{
                childLayoutHorizontal();
                width(percentage(50));
                alignLeft();
                control(createButton(UiToken.Settings.CANCEL, "Cancel", 200, 40));
            }});
            panel(new PanelBuilder() {{
                childLayoutHorizontal();
                width(percentage(50));
                alignRight();
                control(createButton(UiToken.Settings.SAVE, "Save", 200, 40));
            }});
        }};
    }

    private PanelBuilder buildSettingsPanel() {
        return new PanelBuilder("top-panel") {{
            childLayoutVertical();
            height(percentage(70));
            control(new ControlBuilder("tabs-panel") {{
                control(new TabGroupBuilder("tabs-control") {{
                    control(new TabBuilder("tab_1", "Display") {{
                        panel(createGraphicsPanel());
                    }});
                    control(new TabBuilder("tab_2", "Audio") {{
                        panel(createAudioPanel());
                    }});
                }});
            }});
        }};
    }

    private PanelBuilder createAudioPanel() {
        return new PanelBuilder(UiToken.Settings.PANEL_AUDIO) {{
            childLayoutVertical();
            text(createText("Audio", 200));
            panel(optionPair("Master Volume:", createSlider(UiToken.Settings.SLIDER_MASTER_VOLUME)));
        }};
    }

    private ControlBuilder createSlider(String id) {
        return new SliderBuilder(id, false) {{
            min(0);
            max(100);
            stepSize(1);
            buttonStepSize(10);
        }};
    }

    private PanelBuilder createGraphicsPanel() {
        return new PanelBuilder(UiToken.Settings.PANEL_GRAPHICS) {{
            childLayoutVertical();
            text(createText("Graphics", 200));
            panel(optionPair("Resolution:", createResolutionDropdown()));
            panel(optionPair("Fullscreen:", new CheckboxBuilder(UiToken.Settings.CHECKBOX_FULLSCREEN)));
            panel(optionPair("Anti-alias:", createAntiAliasDropdown()));
            panel(optionPair("Display Shadows:", new CheckboxBuilder(UiToken.Settings.CHECKBOX_SHADOWS)));
        }};
    }

    private ControlBuilder createAntiAliasDropdown() {
        return new DropDownBuilder(UiToken.Settings.DROPDOWN_ANTI_ALIAS);
    }

    private PanelBuilder optionPair(String labelText, ControlBuilder optionControl) {
        return new PanelBuilder() {{
            childLayoutHorizontal();
            text(new TextBuilder() {{
                text(labelText);
                font(UiConstants.DEFAULT_FONT);
                width(percentage(50));
                height(pixels(30));
            }});
            panel(new PanelBuilder() {{
                childLayoutHorizontal();
                width(percentage(50));
                control(optionControl);
            }});
        }};
    }

    private ControlBuilder createResolutionDropdown() {
        return new DropDownBuilder(UiToken.Settings.DROPDOWN_RESOLUTIONS) {{
            set("displayItems", "10");
        }};
    }
}
