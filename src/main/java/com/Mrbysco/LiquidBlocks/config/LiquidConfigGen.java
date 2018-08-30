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
	@Config.Comment({"Liquid settings"})
	public static Liquid liquid = new Liquid();
	
	@Config.Comment({"Crafting settings"})
	public static Crafting crafting = new Crafting();
	
	public static class Liquid{
		@Config.Comment("The time liquid dirt types takes to solidify [default: 220]")
		public int dirtSolidifyTime = 220;
		
		@Config.Comment("Liquid dirt causing nausea when swam in [default: true]")
		public boolean dirtCausesNausea = true;
		
		@Config.Comment("Liquid dirt causing slowness when swam in [default: true]")
		public boolean dirtCausesSlowness = true;
		
		@Config.Comment("The time liquid stone types takes to solidify [default: 200]")
		public int stoneSolidifyTime = 200;
	}
	
	public static class Crafting{
		@Config.Comment("Enable crafting buckets with ice [default: true]")
		public boolean enableCraftingWithIce = true;

		@Config.Comment("Enable crafting buckets with a water bottle where applicable [default: true]")
	    public boolean enableCraftingWithWater = true;

		@Config.Comment("Enable crafting buckets with a bucket of the fitting liquid [default: true]")
	    public boolean enableCraftingWithBucket = true;
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