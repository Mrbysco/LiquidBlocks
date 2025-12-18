package com.mrbysco.liquidblocks.init.conditions;

import com.mojang.serialization.MapCodec;
import com.mrbysco.liquidblocks.config.LiquidConfig;
import net.neoforged.neoforge.common.conditions.ICondition;

public class EnableLiquidOreCondition implements ICondition {

	public static final EnableLiquidOreCondition INSTANCE = new EnableLiquidOreCondition();

	public static MapCodec<EnableLiquidOreCondition> CODEC = MapCodec.unit(INSTANCE).stable();

	@Override
	public boolean test(IContext context) {
		return LiquidConfig.COMMON.craftLiquidOre.get();
	}

	@Override
	public MapCodec<? extends ICondition> codec() {
		return CODEC;
	}
}