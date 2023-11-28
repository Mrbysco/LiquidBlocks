package com.mrbysco.liquidblocks.config;


import com.mrbysco.liquidblocks.LiquidBlocks;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

public class LiquidConfig {

	public static class Common {
		public final BooleanValue liquidCausesNausea;
		public final BooleanValue liquidCausesSlowness;
		public final BooleanValue completelyFill;
		public final ModConfigSpec.IntValue netherrackFireChance;
		public final ModConfigSpec.IntValue oreChance;

		public final BooleanValue craftWithIce;
		public final BooleanValue craftWithWaterBottle;
		public final BooleanValue craftWithWaterBucket;
		public final BooleanValue craftLiquidOre;

		Common(ModConfigSpec.Builder builder) {
			//General settings
			builder.comment("General settings")
					.push("general");

			liquidCausesNausea = builder
					.comment("When enabled causes water based liquid blocks to cause nausea when collided [default: true]")
					.define("liquidCausesNausea", true);

			liquidCausesSlowness = builder
					.comment("When enabled causes water based liquid blocks to cause slowness when collided [default: true]")
					.define("liquidCausesSlowness", true);

			completelyFill = builder
					.comment("When enabled causes will try to make the liquid turn every bit into a block [default: true]")
					.define("completelyFill", true);

			netherrackFireChance = builder
					.comment("Makes liquid netherrack have a 1 in X chance of drying with fire on top (0 = disabled) [default: 30]")
					.defineInRange("netherrackFireChance", 30, 1, Integer.MAX_VALUE);

			oreChance = builder
					.comment("Makes liquid ore have a 1 in X chance of drying into an ore block (higher = less chance) [default: 64]")
					.defineInRange("oreChance", 64, 6, Integer.MAX_VALUE);

			builder.pop();
			//Crafting settings
			builder.comment("Crafting settings")
					.push("crafting");

			craftWithIce = builder
					.comment("Enable crafting buckets with ice [default: true]")
					.define("craftWithIce", true);

			craftWithWaterBottle = builder
					.comment("Enable crafting buckets with a water bottle where applicable [default: true]")
					.define("craftWithWaterBottle", true);

			craftWithWaterBucket = builder
					.comment("Enable crafting buckets with a bucket of the fitting liquid [default: true]")
					.define("craftWithWaterBucket", true);

			craftLiquidOre = builder
					.comment("Enables the crafting of the Liquid Ore bucket [default: true]")
					.define("craftLiquidOre", true);


			builder.pop();
		}
	}

	public static final ModConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	@SubscribeEvent
	public static void onLoad(final ModConfigEvent.Loading configEvent) {
		LiquidBlocks.LOGGER.debug("Loaded Liquid Blocks' config file {}", configEvent.getConfig().getFileName());
	}

	@SubscribeEvent
	public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
		LiquidBlocks.LOGGER.warn("Liquid Blocks' config just got changed on the file system!");
	}
}