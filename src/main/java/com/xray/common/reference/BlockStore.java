package com.xray.common.reference;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BlockStore {

    public final HashMap<ResourceLocation, List<BlockData>> blocks = new HashMap<>();

    public HashMap<ResourceLocation, List<BlockData>> getBlocks() {
        return blocks;
    }

    /**
     * Simply adds a block to the existing map by either pushing upon an existing
     * key or creating a new key and initialising a new {@link ArrayList}
     *
     * @param location resource location of the block
     * @param data additional data for each block
     */
    public void addBlock(ResourceLocation location, BlockData data) {
        if( !this.blocks.containsKey( location ) )
            this.blocks.put(location, new ArrayList<BlockData>() {{ add(data); }} );

        // If key does not exist add
        this.blocks.get(location).add(data);
    }

    /**
     * Provides a simple solution for handling the removal of blocks / states
     *
     * @param location the resource location / block you wish to remove from.
     * @param state the state you wish to remove
     * @return whether or not the block was removed
     */
    public boolean removeBlock(ResourceLocation location, IBlockState state) {
        if( !this.blocks.containsKey(location) )
            return false;

        for (BlockData data:
             this.blocks.get(location)) {

            if( data.getState() == state )
                this.blocks.get(location).remove(data);
        }

        // This is to avoid us having to try and scan for blocks that don't have states
        if( this.blocks.get(location).size() == 0 )
            this.blocks.remove(location);

        return true;
    }
}
