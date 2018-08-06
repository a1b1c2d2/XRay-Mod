package com.xray.common.reference;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockStore {

    private HashMap<String, Integer> drawableBlocks = new HashMap<>();
    private HashMap<String, BlockData> storedBlocks = new HashMap<>();

    public boolean storeBlock( int blockStateId, BlockData data ) {
        for (Map.Entry<String, BlockData> dataStore:
             storedBlocks.entrySet()) {

            if(Block.getStateId(dataStore.getValue().getState()) == blockStateId )
                return false;
        }

        this.drawableBlocks.put("UID HERE", blockStateId);
        this.storedBlocks.put("SAME UID HERE", data);
        return true;
    }

    public boolean storeContainsDefault(IBlockState state) {
        for (Map.Entry<String, BlockData> dataStore:
                storedBlocks.entrySet()) {

            if(dataStore.getValue().getState() == state.getBlock().getDefaultState() )
                return true;
        }
        return false;
    }

}
