package com.mrbysco.liquidblocks.init.conditions;

import com.google.gson.JsonObject;
import com.mrbysco.liquidblocks.LiquidBlocks;
import com.mrbysco.liquidblocks.config.LiquidConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class CraftWithWaterBucketCondition implements ICondition {
	private static final ResourceLocation ID = new ResourceLocation(LiquidBlocks.MOD_ID, "craft_with_water_bucket");

	@Override
	public ResourceLocation getID() {
		return ID;
	}

	@Override
	public boolean test() {
		return LiquidConfig.COMMON.craftWithWaterBucket.get();
	}

	public static class Serializer implements IConditionSerializer<CraftWithWaterBucketCondition> {
		public static final CraftWithWaterBucketCondition.Serializer INSTANCE = new CraftWithWaterBucketCondition.Serializer();

		public void write(JsonObject json, CraftWithWaterBucketCondition value) {

		}

		public CraftWithWaterBucketCondition read(JsonObject json) {
			return new CraftWithWaterBucketCondition();
		}

		public ResourceLocation getID() {
			return CraftWithWaterBucketCondition.ID;
		}
	}
}