package net.grimm.eternity.client.gui;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;

public interface IOverlay {

    LayeredDraw.Layer register();

    void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker);

}
