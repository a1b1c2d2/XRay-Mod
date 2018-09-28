package com.xray.client.gui;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.GuiScrollingList;

import java.util.Collection;
import java.util.List;

public class GuiBlocksScrollable extends GuiScrollingList {

    private static final int HEIGHT = 35;
    private final GuiBlocks parent;
    private List<IBlockState> blockList;

    GuiBlocksScrollable(GuiBlocks parent, List<IBlockState> blockList) {
        super( parent.getMinecraftInstance(), 202, 210, parent.height / 2 - 105, parent.height / 2 + 80, parent.width / 2 - 100, HEIGHT, parent.width, parent.height);

        this.parent = parent;
        this.blockList = blockList;
    }

    @Override
    protected int getSize() {
        return this.blockList.size();
    }

    @Override
    protected void elementClicked(int index, boolean doubleClick) {
        this.parent.selectBlock( index );
    }

    @Override
    protected boolean isSelected(int index) {
        return parent.blockSelected( index );
    }

    @Override
    protected void drawBackground() {
    }

    @Override
    protected int getContentHeight() {
        return (this.getSize() * HEIGHT);
    }

    @Override
    protected void drawSlot(int idx, int right, int top, int height, Tessellator tess) {
        IBlockState block = blockList.get( idx );
        if( block.getBlock().equals(Blocks.AIR) )
            return;

        FontRenderer font = this.parent.getFontRender();

        ItemStack stack = new ItemStack(block.getBlock(), 1, 0);

        ResourceLocation name = block.getBlock().getRegistryName();

        font.drawString(block.getBlock().getUnlocalizedName(), this.left + 30 , top +  7, 0xFFFFFF);
        font.drawString(name == null ? "Unset by mod author" : name.toString(), this.left + 30 , top + 17, 0xD1CFCF);

        RenderHelper.enableGUIStandardItemLighting();
        this.parent.getRender().renderItemIntoGUI(stack, this.left + 5, top+7);
        RenderHelper.disableStandardItemLighting();
    }

    void updateBlockList(List<IBlockState> blocks) {
	    blockList = blocks;
    }
}
