package com.Mrbysco.LiquidBlocks.config;

import com.Mrbysco.LiquidBlocks.LiquidReference;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = LiquidReference.MOD_ID)
@Config.LangKey("liquidblocks.config.title")
public class LiquidConfigGen {
	@Config.Comment({"General settings"})
	public static General general = new General();
	
	@Config.Comment({"Liquid settings"})
	public static Liquid liquid = new Liquid();
	
	@Config.Comment({"Crafting settings"})
	public static Crafting crafting = new Crafting();
	
	public static class General{
		@Config.Comment("Water based liquids causing nausea when swam in [default: true]")
		public boolean liquidsCausesNausea = true;
		
		@Config.Comment("Water based liquids causing when swam in [default: true]")
		public boolean liquidsCausesSlowness = true;
		
		@Config.Comment("Turning this to true will make every bit of the liquid turn into a full block [default: true]")
		public boolean completelyFill = true;
		
		@Config.Comment("Makes liquid netherrack have a 1 in X chance of drying with fire on top (0 = disabled) [default: 30]")
		public int netherrackFireChance = 30;
		
		@Config.Comment("Makes liquid ore have a 1 in X chance of drying into an ore block (higher = less chance) [default: 64]")
		@Config.RangeInt(min = 6)
		public int oreChance = 64;
	}
	
	public static class Liquid{
		@Config.Comment("The time liquid dirt types take to solidify [default: 220]")
		public int dirtSolidifyTime = 220;
		
		@Config.Comment("The time liquid stone types take to solidify [default: 200]")
		public int stoneSolidifyTime = 200;
		
		@Config.Comment("The time liquid sandstone type takes to solidify [default: 200]")
		public int sandstoneSolidifyTime = 200;
		
		@Config.Comment("The time liquid sand types take to solidify [default: 200]")
		public int sandSolidifyTime = 200;
		
		@Config.Comment("The time liquid nether liquid types take to solidify [default: 200]")
		public int netherSolidifyTime = 200;
		
		@Config.Comment("The time liquid clay types take to solidify [default: 200]")
		public int claySolidifyTime = 200;
		
		@Config.Comment("The time liquid terracotta types take to solidify [default: 200]")
		public int terracottaSolidifyTime = 200;
		
		@Config.Comment("The time liquid concrete types take to solidify [default: 200]")
		public int concreteSolidifyTime = 200;
	}
	
	public static class Crafting{
		@Config.Comment("Enable crafting buckets with ice [default: true]")
		public boolean enableCraftingWithIce = true;

		@Config.Comment("Enable crafting buckets with a water bottle where applicable [default: true]")
	    public boolean enableCraftingWithWater = true;

		@Config.Comment("Enable crafting buckets with a bucket of the fitting liquid [default: true]")
	    public boolean enableCraftingWithBucket = true;
		
		@Config.Comment("Enables the crafting of the Liquid Ore bucket [default: true]")
		public boolean enableCraftingLiquidOre = true;
	}
	
	@Mod.EventBusSubscriber(modid = LiquidReference.MOD_ID)
	private static class EventHandler {

		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(LiquidReference.MOD_ID)) {
				ConfigManager.sync(LiquidReference.MOD_ID, Config.Type.INSTANCE);
			}
		}
	}
}