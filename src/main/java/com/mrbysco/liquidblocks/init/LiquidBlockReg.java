package com.mrbysco.liquidblocks.init;

import com.mrbysco.liquidblocks.blocks.LiquidBlockBlock;
import com.mrbysco.liquidblocks.blocks.LiquidOreBlock;
import com.mrbysco.liquidblocks.fluid.LiquidBlockFluid;
import com.mrbysco.liquidblocks.item.LiquidBucketItem;
import com.mrbysco.liquidblocks.util.FluidHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class LiquidBlockReg<B extends LiquidBlock> {
	private final String name;
	private RegistryObject<ForgeFlowingFluid> source;
	private RegistryObject<ForgeFlowingFluid> flowing;
	private RegistryObject<LiquidBlock> fluidblock;
	private RegistryObject<Item> bucket;

	@Nonnull
	public String getName() {
		return name;
	}

	@Nonnull
	public RegistryObject<ForgeFlowingFluid> getSource() {
		return source;
	}

	@Nonnull
	public RegistryObject<ForgeFlowingFluid> getFlowing() {
		return flowing;
	}

	@Nonnull
	public LiquidBlock getFluidblock() {
		return fluidblock.get();
	}

	public RegistryObject<Item> getBucket() {
		return bucket;
	}

	public static ForgeFlowingFluid.Properties createProperties(FluidAttributes.Builder attributeBuilder, Supplier<ForgeFlowingFluid> still,
																Supplier<ForgeFlowingFluid> flowing, Supplier<Item> bucket, Supplier<LiquidBlock> block) {
		return new ForgeFlowingFluid.Properties(still, flowing,
				attributeBuilder)
				.bucket(bucket).block(block);
	}

	public LiquidBlockReg(String name, Supplier<Block> blockSupplier, Material material, int color) {
		this.name = name;
		source = LiquidRegistry.FLUIDS.register(name, () -> new LiquidBlockFluid.Source(
				createProperties(FluidHelper.createAttributes(color).temperature(material == Material.WATER ? 300 : 1000), source, flowing, bucket, fluidblock))
		);
		flowing = LiquidRegistry.FLUIDS.register(name + "_flowing", () -> new LiquidBlockFluid.Flowing(
				createProperties(FluidHelper.createAttributes(color).temperature(material == Material.WATER ? 300 : 1000), source, flowing, bucket, fluidblock))
		);
		if (name.equals("liquid_ore")) {
			fluidblock = LiquidRegistry.BLOCKS.register(name, () -> new LiquidOreBlock(
					Block.Properties.of(material).noCollission().strength(100.0F).noDrops(), source, blockSupplier));
		} else {
			fluidblock = LiquidRegistry.BLOCKS.register(name, () -> new LiquidBlockBlock(
					Block.Properties.of(material).noCollission().strength(100.0F).noDrops(), source, blockSupplier));
		}
		bucket = LiquidRegistry.ITEMS.register(name + "_bucket", () -> new LiquidBucketItem(new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(LiquidTab.MAIN_TAB), source));
	}

	public LiquidBlockReg(String name, Supplier<Block> blockSupplier, Material material, int color, int luminosity) {
		this.name = name;
		source = LiquidRegistry.FLUIDS.register(name, () -> new LiquidBlockFluid.Source(
				createProperties(FluidHelper.createAttributes(color).temperature(material == Material.WATER ? 300 : 1000).luminosity(luminosity), source, flowing, bucket, fluidblock))
		);
		flowing = LiquidRegistry.FLUIDS.register(name + "_flowing", () -> new LiquidBlockFluid.Flowing(
				createProperties(FluidHelper.createAttributes(color).temperature(material == Material.WATER ? 300 : 1000).luminosity(luminosity), source, flowing, bucket, fluidblock))
		);
		fluidblock = LiquidRegistry.BLOCKS.register(name, () -> new LiquidBlockBlock(
				Block.Properties.of(material).noCollission().strength(100.0F).randomTicks().noDrops().lightLevel(state -> luminosity), source, blockSupplier));
		bucket = LiquidRegistry.ITEMS.register(name + "_bucket", () -> new LiquidBucketItem(new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(LiquidTab.MAIN_TAB), source));
	}
}
