package com.Mrbysco.LiquidBlocks.init.conditions;

import java.util.function.BooleanSupplier;

import com.Mrbysco.LiquidBlocks.config.LiquidConfigGen;
import com.google.gson.JsonObject;

import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class craftingWaterBucketCondition implements IConditionFactory{

	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {
		return () -> LiquidConfigGen.crafting.enableCraftingWithBucket;
	}
}