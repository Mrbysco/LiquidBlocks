package com.mrbysco.liquidblocks.client;

import com.mrbysco.liquidblocks.init.LiquidRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.registries.RegistryObject;

public class ClientHandler {
	public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
		for (RegistryObject<Block> blockObject : LiquidRegistry.BLOCKS.getEntries()) {
			event.register((state, getter, pos, index) -> {
				return 0xFF3F76E4;
//				if (getter != null && pos != null) {
//					FluidState fluidState = getter.getFluidState(pos);
//					return RenderProperties.get(fluidState).getColorTint(fluidState, getter, pos);
//				} else return 0xAF7FFFD4;
			}, blockObject.get());
		}
	}
}
