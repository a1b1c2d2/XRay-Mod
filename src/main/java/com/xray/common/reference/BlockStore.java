package com.xray.common.reference;

import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.List;

public class BlockStore {

    public final HashMap<ResourceLocation, BlockData> blocks = new HashMap<>();

    public final HashMap<ResourceLocation, List<BlockData>> blockRef = new HashMap<>();

}
