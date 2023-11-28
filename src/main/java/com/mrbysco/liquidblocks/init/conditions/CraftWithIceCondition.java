package com.mrbysco.liquidblocks.init.conditions;

import com.mojang.serialization.Codec;
import com.mrbysco.liquidblocks.LiquidBlocks;
import com.mrbysco.liquidblocks.config.LiquidConfig;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ICondition;

public class CraftWithIceCondition implements ICondition {

	public static final CraftWithIceCondition INSTANCE = new CraftWithIceCondition();

	public static Codec<CraftWithIceCondition> CODEC = Codec.unit(INSTANCE).stable();

	private static final ResourceLocation ID = new ResourceLocation(LiquidBlocks.MOD_ID, "craft_with_ice");

	@Override
	public boolean test(IContext context) {
		return LiquidConfig.COMMON.craftWithIce.get();
	}

	@Override
	public Codec<? extends ICondition> codec() {
		return CODEC;
	}

	@Override
	public String toString() {
		return "craft_with_ice";
	}
}