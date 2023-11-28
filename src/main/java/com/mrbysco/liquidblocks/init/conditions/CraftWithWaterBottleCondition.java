package com.mrbysco.liquidblocks.init.conditions;

import com.mojang.serialization.Codec;
import com.mrbysco.liquidblocks.LiquidBlocks;
import com.mrbysco.liquidblocks.config.LiquidConfig;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ICondition;

public class CraftWithWaterBottleCondition implements ICondition {

	public static final CraftWithWaterBottleCondition INSTANCE = new CraftWithWaterBottleCondition();

	public static Codec<CraftWithWaterBottleCondition> CODEC = Codec.unit(INSTANCE).stable();
	private static final ResourceLocation ID = new ResourceLocation(LiquidBlocks.MOD_ID, "craft_with_water_bottle");

	@Override
	public boolean test(IContext context) {
		return LiquidConfig.COMMON.craftWithWaterBottle.get();
	}

	@Override
	public Codec<? extends ICondition> codec() {
		return CODEC;
	}
}