package com.mrbysco.liquidblocks.client;

import com.mrbysco.liquidblocks.init.LiquidRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public class ClientHandler {
	public static void registerBlockColors(ColorHandlerEvent.Block event) {
		for (RegistryObject<Block> blockObject : LiquidRegistry.BLOCKS.getEntries()) {
			event.getBlockColors().register((state, getter, pos, tintIndex) -> {
				if (getter != null && pos != null) {
					FluidState fluidState = getter.getFluidState(pos);
					return fluidState.getType().getAttributes().getColor(getter, pos);
				} else return 0xFFFFFFFF;
			}, blockObject.get());
		}
	}

	public static void registerItemColors(ColorHandlerEvent.Item event) {
		for (RegistryObject<Item> itemObject : LiquidRegistry.ITEMS.getEntries()) {
			event.getItemColors().register((stack, tintIndex) -> {
				if (tintIndex != 1) return 0xFFFFFFFF;
				Optional<FluidStack> containedFluid = FluidUtil.getFluidContained(stack);
				if (containedFluid.isPresent()) {
					FluidStack fluidStack = containedFluid.get();
					return fluidStack.getFluid().getAttributes().getColor(fluidStack);
				} else {
					return 0xFFFFFFFF;
				}
			}, itemObject.get());
		}
	}
}
