package com.xray.common.reference;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BlockStore {

    private HashMap<String, Integer> drawableBlocks = new HashMap<>();
    private HashMap<String, BlockData> storedBlocks = new HashMap<>();

    public HashMap<String, Integer> getDrawableBlocks() {
        return this.drawableBlocks;
    }

    // NOTE: this function shouldn't be used in most instants other than to display the blocks
    public HashMap<String, BlockData> getStoredBlocks() {
        return this.storedBlocks;
    }

    public boolean storeBlock( int blockStateId, BlockData data ) {
        for (Map.Entry<String, BlockData> dataStore:
             storedBlocks.entrySet()) {

            if(Block.getStateId(dataStore.getValue().getState()) == blockStateId )
                return false;
        }

        UUID uniqueKey = UUID.randomUUID();
        this.drawableBlocks.put(uniqueKey.toString(), blockStateId);
        this.storedBlocks.put(uniqueKey.toString(), data);
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

    public boolean removeDrawableBlock( String uid ) {
        return this.drawableBlocks.remove( uid ) != null;
    }

    public boolean removeBlock( String uid ) {
        if( !this.removeDrawableBlock( uid ) )
            return false;

        return this.storedBlocks.remove( uid ) != null;
    }


}
