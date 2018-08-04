package com.xray.client.gui;

import com.xray.client.xray.XrayController;
import com.xray.common.reference.BlockData;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.GuiScrollingList;

public class GuiListScrollable extends GuiScrollingList {
    private final static int HEIGHT = 50;
    private GuiList parent;

    GuiListScrollable(GuiList parent) {
        super(parent.mc, 202, 215, parent.height / 2 - 105, parent.height / 2 + 80, parent.width / 2 - 138, HEIGHT, parent.width, parent.height);
        this.parent = parent;
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
        BlockData blockData = XrayController.blockStore.getBlocks().entrySet().toArray()[slotIdx];
        FontRenderer font = parent.mc.fontRenderer;

//        ResourceLocation name = block.getItem().getRegistryName();

        font.drawString(block.getDisplayName(), this.left + 30 , top +  7, 0xFFFFFF);
        font.drawString(name == null ? "Unset by mod author" : name.toString(), this.left + 30 , top + 17, 0xD1CFCF);

//        RenderHelper.enableGUIStandardItemLighting();
//        this.parent.getRender().renderItemAndEffectIntoGUI(block, this.left + 5, top+7);
//        RenderHelper.disableStandardItemLighting();
    }
}
