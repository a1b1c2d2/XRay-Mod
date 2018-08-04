package com.xray.client.gui;

import com.xray.client.xray.XrayController;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.fml.client.GuiScrollingList;

public class GuiListScrollable extends GuiScrollingList {
    private final static int HEIGHT = 50;

    public GuiListScrollable(GuiList parent) {
        super(parent.mc, 202, 215, parent.height / 2 - 105, parent.height / 2 + 80, parent.width / 2 - 138, HEIGHT, parent.width, parent.height);
    }

    @Override
    protected int getSize() {
        return XrayController.blockStore.getBlocks().size();
    }

    @Override
    protected void elementClicked(int index, boolean doubleClick) {

    }

    @Override
    protected boolean isSelected(int index) {
        return false;
    }

    @Override
    protected void drawBackground() {

    }

    @Override
    protected void drawSlot(int slotIdx, int entryRight, int slotTop, int slotBuffer, Tessellator tess) {

    }
}
