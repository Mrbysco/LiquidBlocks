package com.mrbysco.liquidblocks.init;

import com.mrbysco.liquidblocks.LiquidBlocks;
import com.mrbysco.liquidblocks.blocks.LiquidBlockBlock;
import com.mrbysco.liquidblocks.blocks.LiquidOreBlock;
import com.mrbysco.liquidblocks.fluid.BlockFluidType;
import com.mrbysco.liquidblocks.fluid.LiquidBlockFluid;
import com.mrbysco.liquidblocks.item.LiquidBucketItem;
import com.mrbysco.liquidblocks.util.FluidHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class LiquidBlockReg {
	private static final ResourceLocation STILL_METAL = LiquidBlocks.modLoc("block/molten_block_still");
	private static final ResourceLocation FLOWING_METAL = LiquidBlocks.modLoc("block/molten_block_flow");

	private final String name;
	private final DeferredHolder<FluidType, FluidType> fluidType;
	private DeferredHolder<Fluid, BaseFlowingFluid> source;
	private DeferredHolder<Fluid, BaseFlowingFluid> flowing;
	private DeferredBlock<LiquidBlock> fluidblock;
	private DeferredItem<LiquidBucketItem> bucket;

	@Nonnull
	public String getName() {
		return name;
	}

	@Nonnull
	public DeferredHolder<FluidType, FluidType> getFluidType() {
		return fluidType;
	}

	@Nonnull
	public DeferredHolder<Fluid, BaseFlowingFluid> getSourceRegistry() {
		return source;
	}

	@Nonnull
	public BaseFlowingFluid getSource() {
		return source.get();
	}

	@Nonnull
	public DeferredHolder<Fluid, BaseFlowingFluid> getFlowing() {
		return flowing;
	}

	@Nonnull
	public LiquidBlock getFluidblock() {
		return fluidblock.get();
	}

	public DeferredItem<LiquidBucketItem> getBucketRegistry() {
		return bucket;
	}

	public Item getBucket() {
		return bucket.get();
	}

	public static BaseFlowingFluid.Properties createProperties(Supplier<FluidType> type, Supplier<BaseFlowingFluid> still, Supplier<BaseFlowingFluid> flowing,
	                                                           DeferredItem<LiquidBucketItem> bucket, Supplier<LiquidBlock> block) {
		return new BaseFlowingFluid.Properties(type, still, flowing)
				.bucket(bucket).block(block);
	}

	public LiquidBlockReg(String name, Supplier<Block> blockSupplier, MapColor mapColor, int color, boolean coldLiquid, int luminosity) {
		this.name = name;
		fluidType = LiquidRegistry.FLUID_TYPES.register(name, () -> new BlockFluidType(color, FluidHelper.createTypeProperties()
				.temperature(coldLiquid ? 300 : 1000).lightLevel(luminosity)));
		source = LiquidRegistry.FLUIDS.register(name, () -> new LiquidBlockFluid.Source(
				createProperties(fluidType, source, flowing, bucket, fluidblock))
		);
		flowing = LiquidRegistry.FLUIDS.register(name + "_flowing", () -> new LiquidBlockFluid.Flowing(
				createProperties(fluidType, source, flowing, bucket, fluidblock))
		);

		if (name.equals("ore")) {
			fluidblock = LiquidRegistry.BLOCKS.registerBlock(name, (properties) -> new LiquidOreBlock(
					properties.mapColor(mapColor).pushReaction(PushReaction.DESTROY).liquid().noCollision().strength(100.0F).randomTicks().noLootTable().lightLevel(state -> luminosity), source, blockSupplier));
		} else {
			fluidblock = LiquidRegistry.BLOCKS.registerBlock(name, (properties) -> new LiquidBlockBlock(
					properties.mapColor(mapColor).pushReaction(PushReaction.DESTROY).liquid().noCollision().strength(100.0F).randomTicks().noLootTable().lightLevel(state -> luminosity), source, blockSupplier));
		}
		bucket = LiquidRegistry.ITEMS.registerItem(name + "_bucket", (properties) -> new LiquidBucketItem(properties.craftRemainder(Items.BUCKET).stacksTo(1), source));
	}

	public static class Builder {
		private String name;
		private Supplier<Block> blockSupplier;
		private MapColor mapColor = MapColor.WATER;
		private int color;
		private boolean hot = false;
		private int luminosity = 0;

		public Builder(String name, Supplier<Block> blockSupplier, int color) {
			this.name = name;
			this.blockSupplier = blockSupplier;
			this.color = color;
		}

		public Builder mapColor(MapColor mapColor) {
			this.mapColor = mapColor;
			return this;
		}

		public Builder hot() {
			this.hot = true;
			return this;
		}

		public Builder luminosity(int luminosity) {
			this.luminosity = luminosity;
			return this;
		}

		public LiquidBlockReg build() {
			return new LiquidBlockReg(name, blockSupplier, mapColor, color, hot, luminosity);
		}
	}
}
