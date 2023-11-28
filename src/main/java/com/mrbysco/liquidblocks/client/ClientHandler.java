package com.mrbysco.liquidblocks.client;

import com.mrbysco.liquidblocks.init.LiquidRegistry;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidUtil;

public class ClientHandler {
	public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
		for (var blockObject : LiquidRegistry.BLOCKS.getEntries()) {
			event.register((state, getter, pos, tintIndex) -> {
				if (getter != null && pos != null) {
					FluidState fluidState = getter.getFluidState(pos);
					return IClientFluidTypeExtensions.of(fluidState).getTintColor(fluidState, getter, pos);
				} else return 0xFFFFFFFF;
			}, blockObject.get());
		}
	}

	public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
		for (var itemObject : LiquidRegistry.ITEMS.getEntries()) {
			event.register((stack, tintIndex) -> {
				if (tintIndex != 1) return 0xFFFFFFFF;
				return FluidUtil.getFluidContained(stack)
						.map(fluidStack -> IClientFluidTypeExtensions.of(fluidStack.getFluid()).getTintColor(fluidStack))
						.orElse(0xFFFFFFFF);
			}, itemObject.get());
		}
	}
}
