package com.mrbysco.liquidblocks.init.conditions;

import com.google.gson.JsonObject;
import com.mrbysco.liquidblocks.LiquidBlocks;
import com.mrbysco.liquidblocks.config.LiquidConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class EnableLiquidOreCondition implements ICondition {
	private static final ResourceLocation ID = new ResourceLocation(LiquidBlocks.MOD_ID, "enable_liquid_ore");

	@Override
	public ResourceLocation getID() {
		return ID;
	}

	@Override
	public boolean test() {
		return LiquidConfig.COMMON.craftLiquidOre.get();
	}

	public static class Serializer implements IConditionSerializer<EnableLiquidOreCondition> {
		public static final EnableLiquidOreCondition.Serializer INSTANCE = new EnableLiquidOreCondition.Serializer();

		public void write(JsonObject json, EnableLiquidOreCondition value) {

		}

		public EnableLiquidOreCondition read(JsonObject json) {
			return new EnableLiquidOreCondition();
		}

		public ResourceLocation getID() {
			return EnableLiquidOreCondition.ID;
		}
	}
}