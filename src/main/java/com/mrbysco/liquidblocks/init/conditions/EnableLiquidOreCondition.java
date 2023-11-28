package com.mrbysco.liquidblocks.init.conditions;

import com.mojang.serialization.Codec;
import com.mrbysco.liquidblocks.LiquidBlocks;
import com.mrbysco.liquidblocks.config.LiquidConfig;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ICondition;

public class EnableLiquidOreCondition implements ICondition {

	public static final EnableLiquidOreCondition INSTANCE = new EnableLiquidOreCondition();

	public static Codec<EnableLiquidOreCondition> CODEC = Codec.unit(INSTANCE).stable();
	private static final ResourceLocation ID = new ResourceLocation(LiquidBlocks.MOD_ID, "enable_liquid_ore");

	@Override
	public boolean test(IContext context) {
		return LiquidConfig.COMMON.craftLiquidOre.get();
	}

	@Override
	public Codec<? extends ICondition> codec() {
		return CODEC;
	}
}