package com.xray.common.reference;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class BlockData {

    private IBlockState state;
    private int[] color;
    private boolean isDefault;

    public BlockData(IBlockState state, int[] color, boolean isDefault) {
        this.state = state;
        this.color = color;
        this.isDefault = isDefault;
    }

    public Block getBlock() {
        return this.state.getBlock();
    }

    public IBlockState getState() {
        return state;
    }

    public int[] getColor() {
        return color;
    }

    public boolean isDefault() {
        return isDefault;
    }
}
