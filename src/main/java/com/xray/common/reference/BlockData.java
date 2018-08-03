package com.xray.common.reference;

import net.minecraft.block.state.IBlockState;

public class BlockData {

    private IBlockState state;
    private int[] color;
    private boolean isEnabled;

    public BlockData(IBlockState state, int[] color, boolean isEnabled) {
        this.state = state;
        this.color = color;
        this.isEnabled = isEnabled;
    }

    public IBlockState getState() {
        return state;
    }

    public int[] getColor() {
        return color;
    }

    public boolean isEnabled() {
        return isEnabled;
    }
}
