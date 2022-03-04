package com.mrbysco.liquidblocks.init;

import com.mrbysco.liquidblocks.LiquidBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class LiquidTags {
	public static final TagKey<Block> ORES = BlockTags.create(new ResourceLocation(LiquidBlocks.MOD_ID, "ores"));
}
