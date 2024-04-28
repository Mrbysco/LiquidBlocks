package com.mrbysco.liquidblocks.init;

import com.mojang.serialization.MapCodec;
import com.mrbysco.liquidblocks.LiquidBlocks;
import com.mrbysco.liquidblocks.init.conditions.CraftWithIceCondition;
import com.mrbysco.liquidblocks.init.conditions.CraftWithWaterBottleCondition;
import com.mrbysco.liquidblocks.init.conditions.CraftWithWaterBucketCondition;
import com.mrbysco.liquidblocks.init.conditions.EnableLiquidOreCondition;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class LiquidConditions {
	public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS = DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, LiquidBlocks.MOD_ID);
	public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<CraftWithIceCondition>> CRAFT_WITH_ICE = CONDITION_CODECS.register("craft_with_ice", () -> CraftWithIceCondition.CODEC);
	public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<CraftWithWaterBottleCondition>> CRAFT_WITH_WATER_BOTTLE = CONDITION_CODECS.register("craft_with_water_bottle", () -> CraftWithWaterBottleCondition.CODEC);
	public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<CraftWithWaterBucketCondition>> CRAFT_WITH_WATER_BUCKET = CONDITION_CODECS.register("craft_with_water_bucket", () -> CraftWithWaterBucketCondition.CODEC);
	public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<EnableLiquidOreCondition>> ENABLE_LIQUID_ORE = CONDITION_CODECS.register("enable_liquid_ore", () -> EnableLiquidOreCondition.CODEC);
}
