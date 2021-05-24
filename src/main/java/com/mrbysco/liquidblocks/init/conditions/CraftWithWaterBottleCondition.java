package com.mrbysco.liquidblocks.init.conditions;

import com.google.gson.JsonObject;
import com.mrbysco.liquidblocks.LiquidBlocks;
import com.mrbysco.liquidblocks.config.LiquidConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class CraftWithWaterBottleCondition implements ICondition {
	private static final ResourceLocation ID = new ResourceLocation(LiquidBlocks.MOD_ID, "craft_with_water_bottle");

	@Override
	public ResourceLocation getID() {
		return ID;
	}

	@Override
	public boolean test() {
		return LiquidConfig.COMMON.craftWithWaterBottle.get();
	}

	public static class Serializer implements IConditionSerializer<CraftWithWaterBottleCondition> {
		public static final CraftWithWaterBottleCondition.Serializer INSTANCE = new CraftWithWaterBottleCondition.Serializer();

		public void write(JsonObject json, CraftWithWaterBottleCondition value) {

		}

		public CraftWithWaterBottleCondition read(JsonObject json) {
			return new CraftWithWaterBottleCondition();
		}

		public ResourceLocation getID() {
			return CraftWithWaterBottleCondition.ID;
		}
	}
}