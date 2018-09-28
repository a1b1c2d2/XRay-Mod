package com.xray.client.gui;

import com.xray.common.XRay;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by MiKeY on 07/07/17.
 */
public class GuiBlocks extends GuiContainer {
    private RenderItem render;
    private GuiBlocksScrollable blockList;
    private List<IBlockState> blocks;
    private GuiTextField search;
    private String lastSearched = "";
    private int selected = -1;

    private static final int BUTTON_CANCEL = 0;

    GuiBlocks() {
        super(false);
        this.blocks = XRay.guiBlockList;
    }

    boolean blockSelected(int index) {
        return index == this.selected;
    }

    void selectBlock(int index)
    {
        if (index == this.selected)
            return;

        this.selected = index;
        mc.player.closeScreen();
//        mc.displayGuiScreen( new GuiAdd( blocks.get( this.selected ) ) );
    }

    @Override
    public void initGui() {
        this.render = this.itemRender;
        this.blockList = new GuiBlocksScrollable( this, this.blocks );

        search = new GuiTextField(150, getFontRender(), width / 2 - 100, height / 2 + 85, 140, 18);
        search.setFocused(true);
        search.setCanLoseFocus(true);

        this.buttonList.add( new GuiButton( BUTTON_CANCEL, width / 2 +43, height / 2 + 84, 60, 20, I18n.format("xray.single.cancel")) );
    }

    @Override
    public void actionPerformed( GuiButton button )
    {
        switch(button.id)
        {
            case BUTTON_CANCEL:
                mc.player.closeScreen();
                mc.displayGuiScreen( new GuiList() );
                break;

            default:
                break;
        }
    }

    @Override
    protected void keyTyped( char charTyped, int hex ) throws IOException
    {
        super.keyTyped(charTyped, hex);
        search.textboxKeyTyped(charTyped, hex);
    }

    @Override
    public void updateScreen()
    {
        search.updateCursorCounter();

        if(!search.getText().equals(lastSearched))
            reloadBlocks();
    }

    private void reloadBlocks() {
        blocks = new ArrayList<>();
        List<IBlockState> tmpBlocks = new ArrayList<>();
        for( IBlockState block : XRay.guiBlockList ) {
            if( block.getBlock().getLocalizedName().toLowerCase().contains( search.getText().toLowerCase() ) )
//		    || block.getDisplayName().toLowerCase().contains( search.getText().toLowerCase() ) )
                tmpBlocks.add(block);
        }
        blocks = tmpBlocks;
        this.blockList.updateBlockList( blocks );
        lastSearched = search.getText();
    }

    @Override
    public void drawScreen( int x, int y, float f )
    {
        super.drawScreen(x, y, f);
        search.drawTextBox();
        this.blockList.drawScreen( x,  y,  f );
    }

    @Override
    public void mouseClicked( int x, int y, int button ) throws IOException
    {
        super.mouseClicked( x, y, button );
        search.mouseClicked(x, y, button );
        this.blockList.handleMouseInput(x, y);
    }

    Minecraft getMinecraftInstance() {
        return this.mc;
    }

    RenderItem getRender() {
        return this.render;
    }
}
