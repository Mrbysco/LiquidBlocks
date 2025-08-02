package com.mrbysco.liquidblocks.data;

import com.mrbysco.liquidblocks.LiquidBlocks;
import com.mrbysco.liquidblocks.data.builder.ShapelessRecipeNoRemainderBuilder;
import com.mrbysco.liquidblocks.init.LiquidBlockReg;
import com.mrbysco.liquidblocks.init.LiquidRegistry;
import com.mrbysco.liquidblocks.init.conditions.CraftWithIceCondition;
import com.mrbysco.liquidblocks.init.conditions.CraftWithWaterBottleCondition;
import com.mrbysco.liquidblocks.init.conditions.CraftWithWaterBucketCondition;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.model.item.DynamicFluidContainerModel;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class LiquidDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();

		generator.addProvider(true, new LiquidRecipes.Runner(packOutput, event.getLookupProvider()));
		generator.addProvider(true, new LiquidLanguage(packOutput));
		generator.addProvider(true, new LiquidModels(packOutput));
	}

	public static class LiquidRecipes extends RecipeProvider {
		private final HolderGetter<Item> items;

		public LiquidRecipes(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
			super(provider, recipeOutput);
			this.items = registries.lookupOrThrow(Registries.ITEM);
		}

		@Override
		protected void buildRecipes() {
			buildWaterRecipes(LiquidRegistry.LIQUID_DIRT, Blocks.DIRT);
			buildWaterRecipes(LiquidRegistry.LIQUID_COARSE_DIRT, Blocks.COARSE_DIRT);
			buildWaterRecipes(LiquidRegistry.LIQUID_PODZOL, Blocks.PODZOL);

			buildLavaRecipe(LiquidRegistry.LIQUID_STONE, Blocks.STONE);
			buildLavaRecipe(LiquidRegistry.LIQUID_GRANITE, Blocks.GRANITE);
			buildLavaRecipe(LiquidRegistry.LIQUID_DIORITE, Blocks.DIORITE);
			buildLavaRecipe(LiquidRegistry.LIQUID_ANDESITE, Blocks.ANDESITE);
			buildLavaRecipe(LiquidRegistry.LIQUID_SANDSTONE, Blocks.SANDSTONE);
			buildLavaRecipe(LiquidRegistry.LIQUID_RED_SANDSTONE, Blocks.RED_SANDSTONE);

			buildLavaRecipe(LiquidRegistry.LIQUID_NETHERRACK, Blocks.NETHERRACK);
			buildLavaRecipe(LiquidRegistry.LIQUID_SOUL_SAND, Blocks.SOUL_SAND);
			buildLavaRecipe(LiquidRegistry.LIQUID_MAGMA, Blocks.MAGMA_BLOCK);
			buildLavaRecipe(LiquidRegistry.LIQUID_GLOWSTONE, Blocks.GLOWSTONE);

			buildWaterRecipes(LiquidRegistry.LIQUID_SAND, Blocks.SAND);
			buildWaterRecipes(LiquidRegistry.LIQUID_RED_SAND, Blocks.RED_SAND);
			buildWaterRecipes(LiquidRegistry.LIQUID_GRAVEL, Blocks.GRAVEL);

			ShapelessRecipeNoRemainderBuilder.shapeless(this.items, LiquidRegistry.LIQUID_ORE.getBucket())
					.requires(Items.LAVA_BUCKET)
					.requires(Tags.Items.ORES_DIAMOND).requires(Tags.Items.ORES_REDSTONE)
					.requires(Tags.Items.ORES_LAPIS).requires(Tags.Items.ORES_COAL)
					.unlockedBy("has_lava_bucket", has(Items.LAVA_BUCKET))
					.save(this.output, "liquidblocks:ore_bucket_with_bucket");


			buildWaterRecipes(LiquidRegistry.LIQUID_CLAY, Blocks.CLAY);
			buildLavaRecipe(LiquidRegistry.LIQUID_TERRACOTTA, Blocks.TERRACOTTA);

			buildLavaRecipe(LiquidRegistry.LIQUID_WHITE_GLAZED_TERRACOTTA, Blocks.WHITE_GLAZED_TERRACOTTA);
			buildLavaRecipe(LiquidRegistry.LIQUID_ORANGE_GLAZED_TERRACOTTA, Blocks.ORANGE_GLAZED_TERRACOTTA);
			buildLavaRecipe(LiquidRegistry.LIQUID_MAGENTA_GLAZED_TERRACOTTA, Blocks.MAGENTA_GLAZED_TERRACOTTA);
			buildLavaRecipe(LiquidRegistry.LIQUID_LIGHT_BLUE_GLAZED_TERRACOTTA, Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA);
			buildLavaRecipe(LiquidRegistry.LIQUID_YELLOW_GLAZED_TERRACOTTA, Blocks.YELLOW_GLAZED_TERRACOTTA);
			buildLavaRecipe(LiquidRegistry.LIQUID_LIME_GLAZED_TERRACOTTA, Blocks.LIME_GLAZED_TERRACOTTA);
			buildLavaRecipe(LiquidRegistry.LIQUID_PINK_GLAZED_TERRACOTTA, Blocks.PINK_GLAZED_TERRACOTTA);
			buildLavaRecipe(LiquidRegistry.LIQUID_GRAY_GLAZED_TERRACOTTA, Blocks.GRAY_GLAZED_TERRACOTTA);
			buildLavaRecipe(LiquidRegistry.LIQUID_LIGHT_GRAY_GLAZED_TERRACOTTA, Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA);
			buildLavaRecipe(LiquidRegistry.LIQUID_CYAN_GLAZED_TERRACOTTA, Blocks.CYAN_GLAZED_TERRACOTTA);
			buildLavaRecipe(LiquidRegistry.LIQUID_PURPLE_GLAZED_TERRACOTTA, Blocks.PURPLE_GLAZED_TERRACOTTA);
			buildLavaRecipe(LiquidRegistry.LIQUID_BLUE_GLAZED_TERRACOTTA, Blocks.BLUE_GLAZED_TERRACOTTA);
			buildLavaRecipe(LiquidRegistry.LIQUID_BROWN_GLAZED_TERRACOTTA, Blocks.BROWN_GLAZED_TERRACOTTA);
			buildLavaRecipe(LiquidRegistry.LIQUID_GREEN_GLAZED_TERRACOTTA, Blocks.GREEN_GLAZED_TERRACOTTA);
			buildLavaRecipe(LiquidRegistry.LIQUID_RED_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA);
			buildLavaRecipe(LiquidRegistry.LIQUID_BLACK_GLAZED_TERRACOTTA, Blocks.BLACK_GLAZED_TERRACOTTA);

			buildWaterRecipes(LiquidRegistry.LIQUID_WHITE_CONCRETE, Blocks.WHITE_CONCRETE_POWDER);
			buildWaterRecipes(LiquidRegistry.LIQUID_ORANGE_CONCRETE, Blocks.ORANGE_CONCRETE_POWDER);
			buildWaterRecipes(LiquidRegistry.LIQUID_MAGENTA_CONCRETE, Blocks.MAGENTA_CONCRETE_POWDER);
			buildWaterRecipes(LiquidRegistry.LIQUID_LIGHT_BLUE_CONCRETE, Blocks.LIGHT_BLUE_CONCRETE_POWDER);
			buildWaterRecipes(LiquidRegistry.LIQUID_YELLOW_CONCRETE, Blocks.YELLOW_CONCRETE_POWDER);
			buildWaterRecipes(LiquidRegistry.LIQUID_LIME_CONCRETE, Blocks.LIME_CONCRETE_POWDER);
			buildWaterRecipes(LiquidRegistry.LIQUID_PINK_CONCRETE, Blocks.PINK_CONCRETE_POWDER);
			buildWaterRecipes(LiquidRegistry.LIQUID_GRAY_CONCRETE, Blocks.GRAY_CONCRETE_POWDER);
			buildWaterRecipes(LiquidRegistry.LIQUID_LIGHT_GRAY_CONCRETE, Blocks.LIGHT_GRAY_CONCRETE_POWDER);
			buildWaterRecipes(LiquidRegistry.LIQUID_CYAN_CONCRETE, Blocks.CYAN_CONCRETE_POWDER);
			buildWaterRecipes(LiquidRegistry.LIQUID_PURPLE_CONCRETE, Blocks.PURPLE_CONCRETE_POWDER);
			buildWaterRecipes(LiquidRegistry.LIQUID_BLUE_CONCRETE, Blocks.BLUE_CONCRETE_POWDER);
			buildWaterRecipes(LiquidRegistry.LIQUID_BROWN_CONCRETE, Blocks.BROWN_CONCRETE_POWDER);
			buildWaterRecipes(LiquidRegistry.LIQUID_GREEN_CONCRETE, Blocks.GREEN_CONCRETE_POWDER);
			buildWaterRecipes(LiquidRegistry.LIQUID_RED_CONCRETE, Blocks.RED_CONCRETE_POWDER);
			buildWaterRecipes(LiquidRegistry.LIQUID_BLACK_CONCRETE, Blocks.BLACK_CONCRETE_POWDER);
		}

		private void buildWaterRecipes(LiquidBlockReg reg, Block block) {
			ResourceLocation location = BuiltInRegistries.BLOCK.getKey(block);
			ItemStack waterBottle = Items.POTION.getDefaultInstance();

			ShapelessRecipeNoRemainderBuilder.shapeless(this.items, reg.getBucket())
					.requires(block).requires(Items.BUCKET).requires(DataComponentIngredient.of(true, waterBottle))
					.group("liquidblocks").unlockedBy("has_" + location.getPath(), has(block))
					.save(this.output.withConditions(new CraftWithWaterBottleCondition()), LiquidBlocks.modLoc(location.getPath() + "_with_bottle"));

			ShapelessRecipeNoRemainderBuilder.shapeless(this.items, reg.getBucket())
					.requires(block).requires(Items.WATER_BUCKET)
					.group("liquidblocks").unlockedBy("has_" + location.getPath(), has(block))
					.save(this.output.withConditions(new CraftWithWaterBucketCondition()),
							LiquidBlocks.modLoc(location.getPath() + "_with_bucket"));

			ShapelessRecipeNoRemainderBuilder.shapeless(this.items, reg.getBucket())
					.requires(block).requires(Items.ICE)
					.group("liquidblocks").unlockedBy("has_" + location.getPath(), has(block))
					.save(this.output.withConditions(new CraftWithIceCondition()),
							LiquidBlocks.modLoc(location.getPath() + "_with_ice"));
		}

		private void buildLavaRecipe(LiquidBlockReg reg, Block block) {
			ResourceLocation location = BuiltInRegistries.BLOCK.getKey(block);
			ShapelessRecipeNoRemainderBuilder.shapeless(this.items, reg.getBucket())
					.requires(block).requires(Items.LAVA_BUCKET)
					.unlockedBy("has_lava_bucket", has(Items.LAVA_BUCKET))
					.save(this.output, "liquidblocks:" + location.getPath() + "_with_lava_bucket");
		}

		public static class Runner extends RecipeProvider.Runner {
			public Runner(PackOutput output, CompletableFuture<Provider> completableFuture) {
				super(output, completableFuture);
			}

			@Override
			protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
				return new LiquidRecipes(provider, recipeOutput);
			}

			@Override
			public String getName() {
				return "LiquidBlocks Recipes";
			}
		}
	}

	private static class LiquidLanguage extends LanguageProvider {
		public LiquidLanguage(PackOutput packOutput) {
			super(packOutput, LiquidBlocks.MOD_ID, "en_us");
		}

		@Override
		protected void addTranslations() {
			add("itemGroup.liquidblocks", "Liquid Blocks");

			generateLang(LiquidRegistry.LIQUID_DIRT, "Liquid Dirt");
			generateLang(LiquidRegistry.LIQUID_COARSE_DIRT, "Liquid Course Dirt");
			generateLang(LiquidRegistry.LIQUID_PODZOL, "Liquid Podzol");
			generateLang(LiquidRegistry.LIQUID_STONE, "Liquid Stone");
			generateLang(LiquidRegistry.LIQUID_GRANITE, "Liquid Granite");
			generateLang(LiquidRegistry.LIQUID_DIORITE, "Liquid Diorite");
			generateLang(LiquidRegistry.LIQUID_ANDESITE, "Liquid Andesite");
			generateLang(LiquidRegistry.LIQUID_SANDSTONE, "Liquid Sandstone");
			generateLang(LiquidRegistry.LIQUID_RED_SANDSTONE, "Liquid Red Sandstone");
			generateLang(LiquidRegistry.LIQUID_NETHERRACK, "Liquid Netherrack");
			generateLang(LiquidRegistry.LIQUID_SOUL_SAND, "Liquid Soul Sand");
			generateLang(LiquidRegistry.LIQUID_MAGMA, "Liquid Magma");
			generateLang(LiquidRegistry.LIQUID_GLOWSTONE, "Liquid Glowstone");
			generateLang(LiquidRegistry.LIQUID_SAND, "Liquid Sand");
			generateLang(LiquidRegistry.LIQUID_RED_SAND, "Liquid Red Sand");
			generateLang(LiquidRegistry.LIQUID_GRAVEL, "Liquid Gravel");
			generateLang(LiquidRegistry.LIQUID_ORE, "Liquid Ore");
			generateLang(LiquidRegistry.LIQUID_CLAY, "Liquid Clay");
			generateLang(LiquidRegistry.LIQUID_TERRACOTTA, "Liquid Terracotta");
			generateLang(LiquidRegistry.LIQUID_WHITE_GLAZED_TERRACOTTA, "Liquid White Glazed Terracotta");
			generateLang(LiquidRegistry.LIQUID_ORANGE_GLAZED_TERRACOTTA, "Liquid Orange Glazed Terracotta");
			generateLang(LiquidRegistry.LIQUID_MAGENTA_GLAZED_TERRACOTTA, "Liquid Magenta Glazed Terracotta");
			generateLang(LiquidRegistry.LIQUID_LIGHT_BLUE_GLAZED_TERRACOTTA, "Liquid Light Blue Glazed Terracotta");
			generateLang(LiquidRegistry.LIQUID_YELLOW_GLAZED_TERRACOTTA, "Liquid Yellow Glazed Terracotta");
			generateLang(LiquidRegistry.LIQUID_LIME_GLAZED_TERRACOTTA, "Liquid Lime Glazed Terracotta");
			generateLang(LiquidRegistry.LIQUID_PINK_GLAZED_TERRACOTTA, "Liquid Pink Glazed Terracotta");
			generateLang(LiquidRegistry.LIQUID_GRAY_GLAZED_TERRACOTTA, "Liquid Gray Glazed Terracotta");
			generateLang(LiquidRegistry.LIQUID_LIGHT_GRAY_GLAZED_TERRACOTTA, "Liquid Light Gray Glazed Terracotta");
			generateLang(LiquidRegistry.LIQUID_CYAN_GLAZED_TERRACOTTA, "Liquid Cyan Glazed Terracotta");
			generateLang(LiquidRegistry.LIQUID_PURPLE_GLAZED_TERRACOTTA, "Liquid Purple Glazed Terracotta");
			generateLang(LiquidRegistry.LIQUID_BLUE_GLAZED_TERRACOTTA, "Liquid Blue Glazed Terracotta");
			generateLang(LiquidRegistry.LIQUID_BROWN_GLAZED_TERRACOTTA, "Liquid Brown Glazed Terracotta");
			generateLang(LiquidRegistry.LIQUID_GREEN_GLAZED_TERRACOTTA, "Liquid Green Glazed Terracotta");
			generateLang(LiquidRegistry.LIQUID_RED_GLAZED_TERRACOTTA, "Liquid Red Glazed Terracotta");
			generateLang(LiquidRegistry.LIQUID_BLACK_GLAZED_TERRACOTTA, "Liquid Black Glazed Terracotta");
			generateLang(LiquidRegistry.LIQUID_WHITE_CONCRETE, "Liquid White Concrete");
			generateLang(LiquidRegistry.LIQUID_ORANGE_CONCRETE, "Liquid Orange Concrete");
			generateLang(LiquidRegistry.LIQUID_MAGENTA_CONCRETE, "Liquid Magenta Concrete");
			generateLang(LiquidRegistry.LIQUID_LIGHT_BLUE_CONCRETE, "Liquid Light Blue Concrete");
			generateLang(LiquidRegistry.LIQUID_YELLOW_CONCRETE, "Liquid Yellow Concrete");
			generateLang(LiquidRegistry.LIQUID_LIME_CONCRETE, "Liquid Lime Concrete");
			generateLang(LiquidRegistry.LIQUID_PINK_CONCRETE, "Liquid Pink Concrete");
			generateLang(LiquidRegistry.LIQUID_GRAY_CONCRETE, "Liquid Gray Concrete");
			generateLang(LiquidRegistry.LIQUID_LIGHT_GRAY_CONCRETE, "Liquid Light Gray Concrete");
			generateLang(LiquidRegistry.LIQUID_CYAN_CONCRETE, "Liquid Cyan Concrete");
			generateLang(LiquidRegistry.LIQUID_PURPLE_CONCRETE, "Liquid Purple Concrete");
			generateLang(LiquidRegistry.LIQUID_BLUE_CONCRETE, "Liquid Blue Concrete");
			generateLang(LiquidRegistry.LIQUID_BROWN_CONCRETE, "Liquid Brown Concrete");
			generateLang(LiquidRegistry.LIQUID_GREEN_CONCRETE, "Liquid Green Concrete");
			generateLang(LiquidRegistry.LIQUID_RED_CONCRETE, "Liquid Red Concrete");
			generateLang(LiquidRegistry.LIQUID_BLACK_CONCRETE, "Liquid Black Concrete");
		}

		private void generateLang(LiquidBlockReg blockReg, String name) {
			addFluid(name, blockReg.getSourceRegistry());
			addFluid("Flowing " + name, blockReg.getFlowing());
			add("fluid_type.liquidblocks." + blockReg.getFluidType().getId().getPath(), name);

			addItem(blockReg.getBucketRegistry(), name + " Bucket");
		}

		private void addFluid(String name, DeferredHolder<Fluid, BaseFlowingFluid> registryObject) {
			add("fluid." + registryObject.getId().getNamespace() + "." + registryObject.getId().getPath(), name);
		}
	}

	private static class LiquidModels extends ModelProvider {
		public LiquidModels(PackOutput packOutput) {
			super(packOutput, LiquidBlocks.MOD_ID);
		}

		@Override
		protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
			generateLiquid(blockModels, LiquidRegistry.LIQUID_DIRT);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_COARSE_DIRT);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_PODZOL);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_STONE);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_GRANITE);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_DIORITE);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_ANDESITE);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_SANDSTONE);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_RED_SANDSTONE);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_NETHERRACK);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_SOUL_SAND);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_MAGMA);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_GLOWSTONE);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_SAND);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_RED_SAND);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_GRAVEL);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_ORE);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_CLAY);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_TERRACOTTA);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_WHITE_GLAZED_TERRACOTTA);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_ORANGE_GLAZED_TERRACOTTA);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_MAGENTA_GLAZED_TERRACOTTA);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_LIGHT_BLUE_GLAZED_TERRACOTTA);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_YELLOW_GLAZED_TERRACOTTA);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_LIME_GLAZED_TERRACOTTA);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_PINK_GLAZED_TERRACOTTA);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_GRAY_GLAZED_TERRACOTTA);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_LIGHT_GRAY_GLAZED_TERRACOTTA);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_CYAN_GLAZED_TERRACOTTA);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_PURPLE_GLAZED_TERRACOTTA);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_BLUE_GLAZED_TERRACOTTA);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_BROWN_GLAZED_TERRACOTTA);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_GREEN_GLAZED_TERRACOTTA);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_RED_GLAZED_TERRACOTTA);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_BLACK_GLAZED_TERRACOTTA);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_WHITE_CONCRETE);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_ORANGE_CONCRETE);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_MAGENTA_CONCRETE);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_LIGHT_BLUE_CONCRETE);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_YELLOW_CONCRETE);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_LIME_CONCRETE);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_PINK_CONCRETE);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_GRAY_CONCRETE);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_LIGHT_GRAY_CONCRETE);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_CYAN_CONCRETE);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_PURPLE_CONCRETE);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_BLUE_CONCRETE);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_BROWN_CONCRETE);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_GREEN_CONCRETE);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_RED_CONCRETE);
			generateLiquid(blockModels, LiquidRegistry.LIQUID_BLACK_CONCRETE);
		}

		private void generateLiquid(BlockModelGenerators blockModels, LiquidBlockReg blockReg) {
			blockModels.createNonTemplateModelBlock(blockReg.getFluidblock());

			blockModels.itemModelOutput.accept(blockReg.getBucket(), new DynamicFluidContainerModel.Unbaked(
					new DynamicFluidContainerModel.Textures(
							Optional.of(ResourceLocation.withDefaultNamespace("item/bucket")),
							Optional.of(ResourceLocation.withDefaultNamespace("item/bucket")),
							Optional.of(ResourceLocation.fromNamespaceAndPath("neoforge", "item/mask/bucket_fluid")),
							Optional.of(ResourceLocation.fromNamespaceAndPath("neoforge", "item/mask/bucket_fluid_cover"))
					), blockReg.getSource(), false, true, false
			));
		}
	}
}
