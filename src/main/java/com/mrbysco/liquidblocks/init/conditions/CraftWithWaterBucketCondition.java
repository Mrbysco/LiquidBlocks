package com.mrbysco.liquidblocks.init.conditions;

import com.mojang.serialization.MapCodec;
import com.mrbysco.liquidblocks.config.LiquidConfig;
import net.neoforged.neoforge.common.conditions.ICondition;

public class CraftWithWaterBucketCondition implements ICondition {

	public static final CraftWithWaterBucketCondition INSTANCE = new CraftWithWaterBucketCondition();

	public static MapCodec<CraftWithWaterBucketCondition> CODEC = MapCodec.unit(INSTANCE).stable();

	@Override
	public boolean test(IContext context) {
		return LiquidConfig.COMMON.craftWithWaterBucket.get();
	}

	@Override
	public MapCodec<? extends ICondition> codec() {
		return CODEC;
	}
}