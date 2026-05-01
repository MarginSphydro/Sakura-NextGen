package dev.sakura.client.gui.panel.panel;

import dev.sakura.client.gui.panel.PanelLayout;
import dev.sakura.client.gui.panel.PanelState;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.MouseButtonEvent;

public class TopBarPanel {

    protected final PanelState state;

    public TopBarPanel(PanelState state) {
        this.state = state;
    }

    public void render(GuiGraphicsExtractor GuiGraphicsExtractor, PanelLayout.Rect bounds, int mouseX, int mouseY, float partialTick) {
    }

    public boolean mouseClicked(MouseButtonEvent event, boolean isDoubleClick) {
        return false;
    }
}
