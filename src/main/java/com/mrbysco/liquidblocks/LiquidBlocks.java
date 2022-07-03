package com.mrbysco.liquidblocks;

import com.mojang.logging.LogUtils;
import com.mrbysco.liquidblocks.client.ClientHandler;
import com.mrbysco.liquidblocks.config.LiquidConfig;
import com.mrbysco.liquidblocks.init.LiquidConditions;
import com.mrbysco.liquidblocks.init.LiquidRegistry;
import com.mrbysco.liquidblocks.init.recipes.LiquidRecipes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(LiquidBlocks.MOD_ID)
public class LiquidBlocks {
	public static final String MOD_ID = "liquidblocks";
	public static final Logger LOGGER = LogUtils.getLogger();

	public LiquidBlocks() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, LiquidConfig.commonSpec);
		eventBus.register(LiquidConfig.class);

		eventBus.register(new LiquidConditions());

		LiquidRegistry.BLOCKS.register(eventBus);
		LiquidRegistry.ITEMS.register(eventBus);
		LiquidRegistry.FLUIDS.register(eventBus);
		LiquidRegistry.FLUID_TYPES.register(eventBus);
		LiquidRegistry.BLOCK_ENTITIES.register(eventBus);
		LiquidRecipes.RECIPE_SERIALIZERS.register(eventBus);


		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			eventBus.addListener(ClientHandler::registerBlockColors);
		});
	}
}
