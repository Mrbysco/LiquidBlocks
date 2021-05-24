package com.mrbysco.liquidblocks.init;

import com.mrbysco.liquidblocks.LiquidBlocks;
import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

public class LiquidTags {
	public static final IOptionalNamedTag<Block> ORES = tag("ores");

	private static IOptionalNamedTag<Block> tag(String name) {
		return BlockTags.createOptional(new ResourceLocation(LiquidBlocks.MOD_ID, name));
	}
}
