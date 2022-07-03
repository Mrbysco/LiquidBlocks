package com.mrbysco.liquidblocks.init;

import com.mojang.math.Vector3f;
import com.mrbysco.liquidblocks.LiquidBlocks;
import com.mrbysco.liquidblocks.blocks.LiquidBlockBlock;
import com.mrbysco.liquidblocks.blocks.LiquidOreBlock;
import com.mrbysco.liquidblocks.fluid.LiquidBlockFluid;
import com.mrbysco.liquidblocks.item.LiquidBucketItem;
import com.mrbysco.liquidblocks.util.FluidHelper;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.IFluidTypeRenderProperties;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LiquidBlockReg<B extends LiquidBlock> {
	private static final ResourceLocation STILL_METAL = new ResourceLocation(LiquidBlocks.MOD_ID, "block/molten_block_still");
	private static final ResourceLocation FLOWING_METAL = new ResourceLocation(LiquidBlocks.MOD_ID, "block/molten_block_flow");

	private final String name;
	private RegistryObject<FluidType> fluidType;
	private RegistryObject<ForgeFlowingFluid> source;
	private RegistryObject<ForgeFlowingFluid> flowing;
	private RegistryObject<LiquidBlock> fluidblock;
	private RegistryObject<Item> bucket;

	@Nonnull
	public String getName() {
		return name;
	}

	@Nonnull
	public RegistryObject<FluidType> getFluidType() {
		return fluidType;
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

	public static ForgeFlowingFluid.Properties createProperties(Supplier<FluidType> type, Supplier<ForgeFlowingFluid> still, Supplier<ForgeFlowingFluid> flowing,
																Supplier<Item> bucket, Supplier<LiquidBlock> block) {
		return new ForgeFlowingFluid.Properties(type, still, flowing)
				.bucket(bucket).block(block);
	}

	public LiquidBlockReg(String name, Supplier<Block> blockSupplier, Material material, int color) {
		this.name = name;
		fluidType = LiquidRegistry.FLUID_TYPES.register(name, () -> new FluidType(FluidHelper.createTypeProperties().temperature(material == Material.WATER ? 300 : 1000)) {
			@Override
			public double motionScale(Entity entity) {
				return entity.level.dimensionType().ultraWarm() ? 0.007D : 0.0023333333333333335D;
			}

			@Override
			public void setItemMovement(ItemEntity entity) {
				Vec3 vec3 = entity.getDeltaMovement();
				entity.setDeltaMovement(vec3.x * (double) 0.95F, vec3.y + (double) (vec3.y < (double) 0.06F ? 5.0E-4F : 0.0F), vec3.z * (double) 0.95F);
			}

			@Override
			public void initializeClient(Consumer<IFluidTypeRenderProperties> consumer) {
				consumer.accept(new IFluidTypeRenderProperties() {

					@Override
					public ResourceLocation getStillTexture() {
						return STILL_METAL;
					}

					@Override
					public ResourceLocation getFlowingTexture() {
						return FLOWING_METAL;
					}

					@Override
					public int getColorTint() {
						return color;
					}

					@Override
					public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
						int color = this.getColorTint();
						return new Vector3f((color >> 16 & 0xFF) / 255F, (color >> 8 & 0xFF) / 255F, (color & 0xFF) / 255F);
					}
				});
			}
		});
		source = LiquidRegistry.FLUIDS.register(name, () -> new LiquidBlockFluid.Source(
				createProperties(fluidType, source, flowing, bucket, fluidblock))
		);
		flowing = LiquidRegistry.FLUIDS.register(name + "_flowing", () -> new LiquidBlockFluid.Flowing(
				createProperties(fluidType, source, flowing, bucket, fluidblock))
		);
		if (name.equals("liquid_ore")) {
			fluidblock = LiquidRegistry.BLOCKS.register(name, () -> new LiquidOreBlock(
					Block.Properties.of(material).noCollission().strength(100.0F).noLootTable(), source, blockSupplier));
		} else {
			fluidblock = LiquidRegistry.BLOCKS.register(name, () -> new LiquidBlockBlock(
					Block.Properties.of(material).noCollission().strength(100.0F).noLootTable(), source, blockSupplier));
		}
		bucket = LiquidRegistry.ITEMS.register(name + "_bucket", () -> new LiquidBucketItem(new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(LiquidTab.MAIN_TAB), source));
	}

	public LiquidBlockReg(String name, Supplier<Block> blockSupplier, Material material, int color, int luminosity) {
		this.name = name;
		fluidType = LiquidRegistry.FLUID_TYPES.register(name, () -> new FluidType(FluidHelper.createTypeProperties().temperature(material == Material.WATER ? 300 : 1000).lightLevel(luminosity)) {
			@Override
			public double motionScale(Entity entity) {
				return entity.level.dimensionType().ultraWarm() ? 0.007D : 0.0023333333333333335D;
			}

			@Override
			public void setItemMovement(ItemEntity entity) {
				Vec3 vec3 = entity.getDeltaMovement();
				entity.setDeltaMovement(vec3.x * (double) 0.95F, vec3.y + (double) (vec3.y < (double) 0.06F ? 5.0E-4F : 0.0F), vec3.z * (double) 0.95F);
			}

			@Override
			public void initializeClient(Consumer<IFluidTypeRenderProperties> consumer) {
				consumer.accept(new IFluidTypeRenderProperties() {

					@Override
					public ResourceLocation getStillTexture() {
						return STILL_METAL;
					}

					@Override
					public ResourceLocation getFlowingTexture() {
						return FLOWING_METAL;
					}

					@Override
					public int getColorTint(FluidStack stack) {
						return color;
					}

					@Override
					public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
						int color = this.getColorTint();
						return new Vector3f((color >> 16 & 0xFF) / 255F, (color >> 8 & 0xFF) / 255F, (color & 0xFF) / 255F);
					}
				});
			}
		});
		source = LiquidRegistry.FLUIDS.register(name, () -> new LiquidBlockFluid.Source(
				createProperties(fluidType, source, flowing, bucket, fluidblock))
		);
		flowing = LiquidRegistry.FLUIDS.register(name + "_flowing", () -> new LiquidBlockFluid.Flowing(
				createProperties(fluidType, source, flowing, bucket, fluidblock))
		);

		fluidblock = LiquidRegistry.BLOCKS.register(name, () -> new LiquidBlockBlock(
				Block.Properties.of(material).noCollission().strength(100.0F).randomTicks().noLootTable().lightLevel(state -> luminosity), source, blockSupplier));
		bucket = LiquidRegistry.ITEMS.register(name + "_bucket", () -> new LiquidBucketItem(new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(LiquidTab.MAIN_TAB), source));
	}
}
