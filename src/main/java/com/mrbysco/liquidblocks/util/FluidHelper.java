package com.mrbysco.liquidblocks.util;

import com.mrbysco.liquidblocks.LiquidBlocks;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;

public class FluidHelper {
	private static ResourceLocation STILL_METAL = new ResourceLocation(LiquidBlocks.MOD_ID, "block/molten_block_still");
	private static ResourceLocation FLOWING_METAL = new ResourceLocation(LiquidBlocks.MOD_ID, "block/molten_block_flow");

	public static FluidAttributes.Builder createAttributes(int color) {
		return FluidAttributes.builder(STILL_METAL, FLOWING_METAL)
				.color(color)
				.rarity(Rarity.COMMON)
				.density(2000)
				.viscosity(8200)
				.temperature(1000)
				.luminosity(0);
	}
}
