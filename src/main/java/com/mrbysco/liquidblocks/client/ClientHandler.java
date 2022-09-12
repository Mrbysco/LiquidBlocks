package com.mrbysco.liquidblocks.client;

import com.mrbysco.liquidblocks.init.LiquidRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.registries.RegistryObject;

public class ClientHandler {
	public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
		for (RegistryObject<Block> blockObject : LiquidRegistry.BLOCKS.getEntries()) {
			event.register((state, getter, pos, tintIndex) -> {
				if (getter != null && pos != null) {
					FluidState fluidState = getter.getFluidState(pos);
					return IClientFluidTypeExtensions.of(fluidState).getTintColor(fluidState, getter, pos);
				} else return 0xFFFFFFFF;
			}, blockObject.get());
		}
	}

	public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
		for (RegistryObject<Item> itemObject : LiquidRegistry.ITEMS.getEntries()) {
			event.register((stack, tintIndex) -> {
				if (tintIndex != 1) return 0xFFFFFFFF;
				return FluidUtil.getFluidContained(stack)
						.map(fluidStack -> IClientFluidTypeExtensions.of(fluidStack.getFluid()).getTintColor(fluidStack))
						.orElse(0xFFFFFFFF);
			}, itemObject.get());
		}
	}
}
