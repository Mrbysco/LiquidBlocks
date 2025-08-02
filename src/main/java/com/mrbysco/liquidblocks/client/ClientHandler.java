package com.mrbysco.liquidblocks.client;

import com.mrbysco.liquidblocks.LiquidBlocks;
import com.mrbysco.liquidblocks.fluid.BlockFluidType;
import com.mrbysco.liquidblocks.init.LiquidRegistry;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class ClientHandler {
	private static final ResourceLocation STILL_METAL = LiquidBlocks.modLoc("block/molten_block_still");
	private static final ResourceLocation FLOWING_METAL = LiquidBlocks.modLoc("block/molten_block_flow");
	
	public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
		for(DeferredHolder<FluidType, ? extends FluidType> deferredHolder : LiquidRegistry.FLUID_TYPES.getEntries()) {
			if (deferredHolder.get() instanceof BlockFluidType blockFluidType) {
				event.registerFluidType(new IClientFluidTypeExtensions() {

					@Override
					public ResourceLocation getStillTexture() {
						return STILL_METAL;
					}

					@Override
					public ResourceLocation getFlowingTexture() {
						return FLOWING_METAL;
					}

					@Override
					public int getTintColor() {
						return blockFluidType.getColor();
					}

					@Override
					public Vector4f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector4f fluidFogColor) {
						int color = this.getTintColor();
						return new Vector4f((color >> 16 & 0xFF) / 255F, (color >> 8 & 0xFF) / 255F, (color & 0xFF) / 255F, fluidFogColor.w());
					}
				}, blockFluidType);
			}
		}
	}

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

	public static void registerItemColors(RegisterColorHandlersEvent.ItemTintSources event) {
		event.register(LiquidBlocks.modLoc("fluid_item"), FluidItemTint.MAP_CODEC);
	}
}
