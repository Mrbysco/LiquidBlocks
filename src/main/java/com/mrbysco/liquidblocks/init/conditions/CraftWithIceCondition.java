package com.mrbysco.liquidblocks.init.conditions;

import com.google.gson.JsonObject;
import com.mrbysco.liquidblocks.LiquidBlocks;
import com.mrbysco.liquidblocks.config.LiquidConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class CraftWithIceCondition implements ICondition {
	private static final ResourceLocation ID = new ResourceLocation(LiquidBlocks.MOD_ID, "craft_with_ice");

	@Override
	public ResourceLocation getID() {
		return ID;
	}

	@Override
	public boolean test() {
		return LiquidConfig.COMMON.craftWithIce.get();
	}

	public static class Serializer implements IConditionSerializer<CraftWithIceCondition> {
		public static final CraftWithIceCondition.Serializer INSTANCE = new CraftWithIceCondition.Serializer();

		public void write(JsonObject json, CraftWithIceCondition value) {

		}

		public CraftWithIceCondition read(JsonObject json) {
			return new CraftWithIceCondition();
		}

		public ResourceLocation getID() {
			return CraftWithIceCondition.ID;
		}
	}
}