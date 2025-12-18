package com.mrbysco.liquidblocks.init.conditions;

import com.mojang.serialization.MapCodec;
import com.mrbysco.liquidblocks.config.LiquidConfig;
import net.neoforged.neoforge.common.conditions.ICondition;

public class CraftWithWaterBottleCondition implements ICondition {

	public static final CraftWithWaterBottleCondition INSTANCE = new CraftWithWaterBottleCondition();

	public static MapCodec<CraftWithWaterBottleCondition> CODEC = MapCodec.unit(INSTANCE).stable();

	@Override
	public boolean test(IContext context) {
		return LiquidConfig.COMMON.craftWithWaterBottle.get();
	}

	@Override
	public MapCodec<? extends ICondition> codec() {
		return CODEC;
	}
}