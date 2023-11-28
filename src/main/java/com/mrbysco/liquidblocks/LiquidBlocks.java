package com.mrbysco.liquidblocks;

import com.mojang.logging.LogUtils;
import com.mrbysco.liquidblocks.client.ClientHandler;
import com.mrbysco.liquidblocks.config.LiquidConfig;
import com.mrbysco.liquidblocks.init.LiquidConditions;
import com.mrbysco.liquidblocks.init.LiquidRegistry;
import com.mrbysco.liquidblocks.init.recipes.LiquidRecipes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(LiquidBlocks.MOD_ID)
public class LiquidBlocks {
	public static final String MOD_ID = "liquidblocks";
	public static final Logger LOGGER = LogUtils.getLogger();

	public LiquidBlocks() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, LiquidConfig.commonSpec);
		eventBus.register(LiquidConfig.class);

		LiquidRegistry.BLOCKS.register(eventBus);
		LiquidRegistry.ITEMS.register(eventBus);
		LiquidRegistry.FLUIDS.register(eventBus);
		LiquidRegistry.FLUID_TYPES.register(eventBus);
		LiquidRegistry.BLOCK_ENTITY_TYPES.register(eventBus);
		LiquidRegistry.CREATIVE_MODE_TABS.register(eventBus);
		LiquidRecipes.RECIPE_SERIALIZERS.register(eventBus);
		LiquidConditions.CONDITION_CODECS.register(eventBus);

		if (FMLEnvironment.dist.isClient()) {
			eventBus.addListener(ClientHandler::registerBlockColors);
			eventBus.addListener(ClientHandler::registerItemColors);
		}
	}
}
