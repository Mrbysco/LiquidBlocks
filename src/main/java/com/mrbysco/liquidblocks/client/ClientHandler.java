package com.mrbysco.liquidblocks.client;

import com.mrbysco.liquidblocks.LiquidBlocks;
import com.mrbysco.liquidblocks.fluid.BlockFluidType;
import com.mrbysco.liquidblocks.init.LiquidBlockReg;
import com.mrbysco.liquidblocks.init.LiquidRegistry;
import net.minecraft.client.Camera;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterFluidModelsEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.joml.Vector4f;

import java.util.List;

@EventBusSubscriber(Dist.CLIENT)
public class ClientHandler {
	private static final Identifier STILL_METAL = LiquidBlocks.modLoc("block/molten_block_still");
	private static final Identifier FLOWING_METAL = LiquidBlocks.modLoc("block/molten_block_flow");

	@SubscribeEvent
	public static void registerFluidModels(final RegisterFluidModelsEvent event) {
		for (LiquidBlockReg blockReg : LiquidRegistry.LIQUID_BLOCK_REG_LIST) {
			event.register(new FluidModel.Unbaked(
					new Material(STILL_METAL),
					new Material(FLOWING_METAL),
					null,
					new LiquidTintSource()), blockReg.getSource(), blockReg.getFlowing());
		}
	}

	@SubscribeEvent
	public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
		for (DeferredHolder<FluidType, ? extends FluidType> deferredHolder : LiquidRegistry.FLUID_TYPES.getEntries()) {
			if (deferredHolder.get() instanceof BlockFluidType blockFluidType) {
				event.registerFluidType(new IClientFluidTypeExtensions() {

					@Override
					public void modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance,
					                           float darkenWorldAmount, Vector4f fluidFogColor) {
						int color = blockFluidType.getColor();
						fluidFogColor.set(new Vector4f(
								(color >> 16 & 0xFF) / 255F,
								(color >> 8 & 0xFF) / 255F,
								(color & 0xFF) / 255F, fluidFogColor.w())
						);
					}

				}, blockFluidType);
			}
		}
	}

	@SubscribeEvent
	public static void registerBlockColors(RegisterColorHandlersEvent.BlockTintSources event) {
		for (var blockObject : LiquidRegistry.BLOCKS.getEntries()) {
			event.register(List.of(
							liquidBlock()
					),
					blockObject.get()
			);
		}
	}

	private static BlockTintSource liquidBlock() {
		return new BlockTintSource() {
			@Override
			public int color(BlockState state) {
				return -1;
			}

			@Override
			public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
				FluidState fluidState = level.getFluidState(pos);
				if (!fluidState.isEmpty() && fluidState.getFluidType() instanceof BlockFluidType blockFluidType) {
					return blockFluidType.getColor();
				}
				return 0xFFFFFFFF;
			}
		};
	}

	@SubscribeEvent
	public static void registerItemColors(RegisterColorHandlersEvent.ItemTintSources event) {
		event.register(LiquidBlocks.modLoc("fluid_item"), FluidItemTint.MAP_CODEC);
	}
}
