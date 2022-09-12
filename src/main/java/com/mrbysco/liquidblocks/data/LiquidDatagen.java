package com.mrbysco.liquidblocks.data;

import com.mrbysco.liquidblocks.LiquidBlocks;
import com.mrbysco.liquidblocks.data.builder.ShapelessRecipeNoRemainderBuilder;
import com.mrbysco.liquidblocks.init.LiquidBlockReg;
import com.mrbysco.liquidblocks.init.LiquidRegistry;
import com.mrbysco.liquidblocks.init.conditions.CraftWithIceCondition;
import com.mrbysco.liquidblocks.init.conditions.CraftWithWaterBottleCondition;
import com.mrbysco.liquidblocks.init.conditions.CraftWithWaterBucketCondition;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.loaders.DynamicFluidContainerModelBuilder;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.StrictNBTIngredient;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class LiquidDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper helper = event.getExistingFileHelper();

		generator.addProvider(event.includeServer(), new Recipes(generator));
		generator.addProvider(event.includeClient(), new Language(generator));
		generator.addProvider(event.includeClient(), new ItemModels(generator, helper));
		generator.addProvider(event.includeClient(), new BlockStates(generator, helper));
	}

	public static class Recipes extends RecipeProvider {

		public Recipes(DataGenerator generator) {
			super(generator);
		}

		@Override
		protected void buildCraftingRecipes(Consumer<FinishedRecipe> recipeConsumer) {
			buildWaterRecipes(LiquidRegistry.LIQUID_DIRT, Blocks.DIRT, recipeConsumer);
			buildWaterRecipes(LiquidRegistry.LIQUID_COARSE_DIRT, Blocks.COARSE_DIRT, recipeConsumer);
			buildWaterRecipes(LiquidRegistry.LIQUID_PODZOL, Blocks.PODZOL, recipeConsumer);

			buildLavaRecipe(LiquidRegistry.LIQUID_STONE, Blocks.STONE, recipeConsumer);
			buildLavaRecipe(LiquidRegistry.LIQUID_GRANITE, Blocks.GRANITE, recipeConsumer);
			buildLavaRecipe(LiquidRegistry.LIQUID_DIORITE, Blocks.DIORITE, recipeConsumer);
			buildLavaRecipe(LiquidRegistry.LIQUID_ANDESITE, Blocks.ANDESITE, recipeConsumer);
			buildLavaRecipe(LiquidRegistry.LIQUID_SANDSTONE, Blocks.SANDSTONE, recipeConsumer);
			buildLavaRecipe(LiquidRegistry.LIQUID_RED_SANDSTONE, Blocks.RED_SANDSTONE, recipeConsumer);

			buildLavaRecipe(LiquidRegistry.LIQUID_NETHERRACK, Blocks.NETHERRACK, recipeConsumer);
			buildLavaRecipe(LiquidRegistry.LIQUID_SOUL_SAND, Blocks.SOUL_SAND, recipeConsumer);
			buildLavaRecipe(LiquidRegistry.LIQUID_MAGMA, Blocks.MAGMA_BLOCK, recipeConsumer);
			buildLavaRecipe(LiquidRegistry.LIQUID_GLOWSTONE, Blocks.GLOWSTONE, recipeConsumer);

			buildWaterRecipes(LiquidRegistry.LIQUID_SAND, Blocks.SAND, recipeConsumer);
			buildWaterRecipes(LiquidRegistry.LIQUID_RED_SAND, Blocks.RED_SAND, recipeConsumer);
			buildWaterRecipes(LiquidRegistry.LIQUID_GRAVEL, Blocks.GRAVEL, recipeConsumer);

			ShapelessRecipeNoRemainderBuilder.shapeless(LiquidRegistry.LIQUID_ORE.getBucket())
					.requires(Items.LAVA_BUCKET)
					.requires(Tags.Items.ORES_DIAMOND).requires(Tags.Items.ORES_REDSTONE)
					.requires(Tags.Items.ORES_LAPIS).requires(Tags.Items.ORES_COAL)
					.unlockedBy("has_lava_bucket", has(Items.LAVA_BUCKET))
					.save(recipeConsumer, "liquidblocks:ore_bucket_with_bucket");


			buildWaterRecipes(LiquidRegistry.LIQUID_CLAY, Blocks.CLAY, recipeConsumer);
			buildLavaRecipe(LiquidRegistry.LIQUID_TERRACOTTA, Blocks.TERRACOTTA, recipeConsumer);

			buildLavaRecipe(LiquidRegistry.LIQUID_WHITE_GLAZED_TERRACOTTA, Blocks.WHITE_GLAZED_TERRACOTTA, recipeConsumer);
			buildLavaRecipe(LiquidRegistry.LIQUID_ORANGE_GLAZED_TERRACOTTA, Blocks.ORANGE_GLAZED_TERRACOTTA, recipeConsumer);
			buildLavaRecipe(LiquidRegistry.LIQUID_MAGENTA_GLAZED_TERRACOTTA, Blocks.MAGENTA_GLAZED_TERRACOTTA, recipeConsumer);
			buildLavaRecipe(LiquidRegistry.LIQUID_LIGHT_BLUE_GLAZED_TERRACOTTA, Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, recipeConsumer);
			buildLavaRecipe(LiquidRegistry.LIQUID_YELLOW_GLAZED_TERRACOTTA, Blocks.YELLOW_GLAZED_TERRACOTTA, recipeConsumer);
			buildLavaRecipe(LiquidRegistry.LIQUID_LIME_GLAZED_TERRACOTTA, Blocks.LIME_GLAZED_TERRACOTTA, recipeConsumer);
			buildLavaRecipe(LiquidRegistry.LIQUID_PINK_GLAZED_TERRACOTTA, Blocks.PINK_GLAZED_TERRACOTTA, recipeConsumer);
			buildLavaRecipe(LiquidRegistry.LIQUID_GRAY_GLAZED_TERRACOTTA, Blocks.GRAY_GLAZED_TERRACOTTA, recipeConsumer);
			buildLavaRecipe(LiquidRegistry.LIQUID_LIGHT_GRAY_GLAZED_TERRACOTTA, Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA, recipeConsumer);
			buildLavaRecipe(LiquidRegistry.LIQUID_CYAN_GLAZED_TERRACOTTA, Blocks.CYAN_GLAZED_TERRACOTTA, recipeConsumer);
			buildLavaRecipe(LiquidRegistry.LIQUID_PURPLE_GLAZED_TERRACOTTA, Blocks.PURPLE_GLAZED_TERRACOTTA, recipeConsumer);
			buildLavaRecipe(LiquidRegistry.LIQUID_BLUE_GLAZED_TERRACOTTA, Blocks.BLUE_GLAZED_TERRACOTTA, recipeConsumer);
			buildLavaRecipe(LiquidRegistry.LIQUID_BROWN_GLAZED_TERRACOTTA, Blocks.BROWN_GLAZED_TERRACOTTA, recipeConsumer);
			buildLavaRecipe(LiquidRegistry.LIQUID_GREEN_GLAZED_TERRACOTTA, Blocks.GREEN_GLAZED_TERRACOTTA, recipeConsumer);
			buildLavaRecipe(LiquidRegistry.LIQUID_RED_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA, recipeConsumer);
			buildLavaRecipe(LiquidRegistry.LIQUID_BLACK_GLAZED_TERRACOTTA, Blocks.BLACK_GLAZED_TERRACOTTA, recipeConsumer);

			buildWaterRecipes(LiquidRegistry.LIQUID_WHITE_CONCRETE, Blocks.WHITE_CONCRETE, recipeConsumer);
			buildWaterRecipes(LiquidRegistry.LIQUID_ORANGE_CONCRETE, Blocks.ORANGE_CONCRETE, recipeConsumer);
			buildWaterRecipes(LiquidRegistry.LIQUID_MAGENTA_CONCRETE, Blocks.MAGENTA_CONCRETE, recipeConsumer);
			buildWaterRecipes(LiquidRegistry.LIQUID_LIGHT_BLUE_CONCRETE, Blocks.LIGHT_BLUE_CONCRETE, recipeConsumer);
			buildWaterRecipes(LiquidRegistry.LIQUID_YELLOW_CONCRETE, Blocks.YELLOW_CONCRETE, recipeConsumer);
			buildWaterRecipes(LiquidRegistry.LIQUID_LIME_CONCRETE, Blocks.LIME_CONCRETE, recipeConsumer);
			buildWaterRecipes(LiquidRegistry.LIQUID_PINK_CONCRETE, Blocks.PINK_CONCRETE, recipeConsumer);
			buildWaterRecipes(LiquidRegistry.LIQUID_GRAY_CONCRETE, Blocks.GRAY_CONCRETE, recipeConsumer);
			buildWaterRecipes(LiquidRegistry.LIQUID_LIGHT_GRAY_CONCRETE, Blocks.LIGHT_GRAY_CONCRETE, recipeConsumer);
			buildWaterRecipes(LiquidRegistry.LIQUID_CYAN_CONCRETE, Blocks.CYAN_CONCRETE, recipeConsumer);
			buildWaterRecipes(LiquidRegistry.LIQUID_PURPLE_CONCRETE, Blocks.PURPLE_CONCRETE, recipeConsumer);
			buildWaterRecipes(LiquidRegistry.LIQUID_BLUE_CONCRETE, Blocks.BLUE_CONCRETE, recipeConsumer);
			buildWaterRecipes(LiquidRegistry.LIQUID_BROWN_CONCRETE, Blocks.BROWN_CONCRETE, recipeConsumer);
			buildWaterRecipes(LiquidRegistry.LIQUID_GREEN_CONCRETE, Blocks.GREEN_CONCRETE, recipeConsumer);
			buildWaterRecipes(LiquidRegistry.LIQUID_RED_CONCRETE, Blocks.RED_CONCRETE, recipeConsumer);
			buildWaterRecipes(LiquidRegistry.LIQUID_BLACK_CONCRETE, Blocks.BLACK_CONCRETE, recipeConsumer);
		}

		private void buildWaterRecipes(LiquidBlockReg reg, Block block, Consumer<FinishedRecipe> recipeConsumer) {
			ResourceLocation location = ForgeRegistries.BLOCKS.getKey(block);
			ItemStack waterBottle = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER);
			new ConditionalRecipe.Builder()
					.addCondition(
							new CraftWithWaterBottleCondition()
					)
					.addRecipe(
							ShapelessRecipeNoRemainderBuilder.shapeless(reg.getBucket())
									.requires(block).requires(Items.BUCKET).requires(StrictNBTIngredient.of(waterBottle))
									.group("liquidblocks").unlockedBy("has_" + location.getPath(), has(block))
									::save
					)
					.build(recipeConsumer,
							new ResourceLocation(LiquidBlocks.MOD_ID, location.getPath() + "_with_bottle"));

			new ConditionalRecipe.Builder()
					.addCondition(
							new CraftWithWaterBucketCondition()
					)
					.addRecipe(
							ShapelessRecipeNoRemainderBuilder.shapeless(reg.getBucket())
									.requires(block).requires(Items.WATER_BUCKET)
									.group("liquidblocks").unlockedBy("has_" + location.getPath(), has(block))
									::save
					)
					.build(recipeConsumer,
							new ResourceLocation(LiquidBlocks.MOD_ID, location.getPath() + "_with_bucket"));

			new ConditionalRecipe.Builder()
					.addCondition(
							new CraftWithIceCondition()
					)
					.addRecipe(
							ShapelessRecipeNoRemainderBuilder.shapeless(reg.getBucket())
									.requires(block).requires(Items.ICE)
									.group("liquidblocks").unlockedBy("has_" + location.getPath(), has(block))
									::save
					)
					.build(recipeConsumer,
							new ResourceLocation(LiquidBlocks.MOD_ID, location.getPath() + "_with_ice"));
		}

		private void buildLavaRecipe(LiquidBlockReg reg, Block block, Consumer<FinishedRecipe> recipeConsumer) {
			ResourceLocation location = ForgeRegistries.BLOCKS.getKey(block);
			ShapelessRecipeNoRemainderBuilder.shapeless(reg.getBucket())
					.requires(block).requires(Items.LAVA_BUCKET)
					.unlockedBy("has_lava_bucket", has(Items.LAVA_BUCKET))
					.save(recipeConsumer, "liquidblocks:" + location.getPath() + "_with_lava_bucket");
		}
	}

	private static class Language extends LanguageProvider {
		public Language(DataGenerator gen) {
			super(gen, LiquidBlocks.MOD_ID, "en_us");
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

		private void addFluid(String name, RegistryObject<ForgeFlowingFluid> registryObject) {
			add("fluid." + registryObject.getId().getNamespace() + "." + registryObject.getId().getPath(), name);
		}
	}

	private static class BlockStates extends BlockStateProvider {
		public BlockStates(DataGenerator gen, ExistingFileHelper helper) {
			super(gen, LiquidBlocks.MOD_ID, helper);
		}

		@Override
		protected void registerStatesAndModels() {
			ModelFile liquidModel = models().getExistingFile(modLoc("block/liquid_block"));
			simpleBlock(LiquidRegistry.LIQUID_DIRT.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_COARSE_DIRT.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_PODZOL.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_STONE.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_GRANITE.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_DIORITE.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_ANDESITE.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_SANDSTONE.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_RED_SANDSTONE.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_NETHERRACK.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_SOUL_SAND.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_MAGMA.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_GLOWSTONE.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_SAND.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_RED_SAND.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_GRAVEL.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_ORE.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_CLAY.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_TERRACOTTA.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_WHITE_GLAZED_TERRACOTTA.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_ORANGE_GLAZED_TERRACOTTA.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_MAGENTA_GLAZED_TERRACOTTA.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_LIGHT_BLUE_GLAZED_TERRACOTTA.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_YELLOW_GLAZED_TERRACOTTA.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_LIME_GLAZED_TERRACOTTA.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_PINK_GLAZED_TERRACOTTA.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_GRAY_GLAZED_TERRACOTTA.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_LIGHT_GRAY_GLAZED_TERRACOTTA.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_CYAN_GLAZED_TERRACOTTA.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_PURPLE_GLAZED_TERRACOTTA.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_BLUE_GLAZED_TERRACOTTA.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_BROWN_GLAZED_TERRACOTTA.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_GREEN_GLAZED_TERRACOTTA.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_RED_GLAZED_TERRACOTTA.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_BLACK_GLAZED_TERRACOTTA.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_WHITE_CONCRETE.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_ORANGE_CONCRETE.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_MAGENTA_CONCRETE.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_LIGHT_BLUE_CONCRETE.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_YELLOW_CONCRETE.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_LIME_CONCRETE.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_PINK_CONCRETE.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_GRAY_CONCRETE.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_LIGHT_GRAY_CONCRETE.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_CYAN_CONCRETE.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_PURPLE_CONCRETE.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_BLUE_CONCRETE.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_BROWN_CONCRETE.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_GREEN_CONCRETE.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_RED_CONCRETE.getFluidblock(), liquidModel);
			simpleBlock(LiquidRegistry.LIQUID_BLACK_CONCRETE.getFluidblock(), liquidModel);
		}
	}

	private static class ItemModels extends ItemModelProvider {
		public ItemModels(DataGenerator gen, ExistingFileHelper helper) {
			super(gen, LiquidBlocks.MOD_ID, helper);
		}

		@Override
		protected void registerModels() {
			generateBucket(LiquidRegistry.LIQUID_DIRT);
			generateBucket(LiquidRegistry.LIQUID_COARSE_DIRT);
			generateBucket(LiquidRegistry.LIQUID_PODZOL);
			generateBucket(LiquidRegistry.LIQUID_STONE);
			generateBucket(LiquidRegistry.LIQUID_GRANITE);
			generateBucket(LiquidRegistry.LIQUID_DIORITE);
			generateBucket(LiquidRegistry.LIQUID_ANDESITE);
			generateBucket(LiquidRegistry.LIQUID_SANDSTONE);
			generateBucket(LiquidRegistry.LIQUID_RED_SANDSTONE);
			generateBucket(LiquidRegistry.LIQUID_NETHERRACK);
			generateBucket(LiquidRegistry.LIQUID_SOUL_SAND);
			generateBucket(LiquidRegistry.LIQUID_MAGMA);
			generateBucket(LiquidRegistry.LIQUID_GLOWSTONE);
			generateBucket(LiquidRegistry.LIQUID_SAND);
			generateBucket(LiquidRegistry.LIQUID_RED_SAND);
			generateBucket(LiquidRegistry.LIQUID_GRAVEL);
			generateBucket(LiquidRegistry.LIQUID_ORE);
			generateBucket(LiquidRegistry.LIQUID_CLAY);
			generateBucket(LiquidRegistry.LIQUID_TERRACOTTA);
			generateBucket(LiquidRegistry.LIQUID_WHITE_GLAZED_TERRACOTTA);
			generateBucket(LiquidRegistry.LIQUID_ORANGE_GLAZED_TERRACOTTA);
			generateBucket(LiquidRegistry.LIQUID_MAGENTA_GLAZED_TERRACOTTA);
			generateBucket(LiquidRegistry.LIQUID_LIGHT_BLUE_GLAZED_TERRACOTTA);
			generateBucket(LiquidRegistry.LIQUID_YELLOW_GLAZED_TERRACOTTA);
			generateBucket(LiquidRegistry.LIQUID_LIME_GLAZED_TERRACOTTA);
			generateBucket(LiquidRegistry.LIQUID_PINK_GLAZED_TERRACOTTA);
			generateBucket(LiquidRegistry.LIQUID_GRAY_GLAZED_TERRACOTTA);
			generateBucket(LiquidRegistry.LIQUID_LIGHT_GRAY_GLAZED_TERRACOTTA);
			generateBucket(LiquidRegistry.LIQUID_CYAN_GLAZED_TERRACOTTA);
			generateBucket(LiquidRegistry.LIQUID_PURPLE_GLAZED_TERRACOTTA);
			generateBucket(LiquidRegistry.LIQUID_BLUE_GLAZED_TERRACOTTA);
			generateBucket(LiquidRegistry.LIQUID_BROWN_GLAZED_TERRACOTTA);
			generateBucket(LiquidRegistry.LIQUID_GREEN_GLAZED_TERRACOTTA);
			generateBucket(LiquidRegistry.LIQUID_RED_GLAZED_TERRACOTTA);
			generateBucket(LiquidRegistry.LIQUID_BLACK_GLAZED_TERRACOTTA);
			generateBucket(LiquidRegistry.LIQUID_WHITE_CONCRETE);
			generateBucket(LiquidRegistry.LIQUID_ORANGE_CONCRETE);
			generateBucket(LiquidRegistry.LIQUID_MAGENTA_CONCRETE);
			generateBucket(LiquidRegistry.LIQUID_LIGHT_BLUE_CONCRETE);
			generateBucket(LiquidRegistry.LIQUID_YELLOW_CONCRETE);
			generateBucket(LiquidRegistry.LIQUID_LIME_CONCRETE);
			generateBucket(LiquidRegistry.LIQUID_PINK_CONCRETE);
			generateBucket(LiquidRegistry.LIQUID_GRAY_CONCRETE);
			generateBucket(LiquidRegistry.LIQUID_LIGHT_GRAY_CONCRETE);
			generateBucket(LiquidRegistry.LIQUID_CYAN_CONCRETE);
			generateBucket(LiquidRegistry.LIQUID_PURPLE_CONCRETE);
			generateBucket(LiquidRegistry.LIQUID_BLUE_CONCRETE);
			generateBucket(LiquidRegistry.LIQUID_BROWN_CONCRETE);
			generateBucket(LiquidRegistry.LIQUID_GREEN_CONCRETE);
			generateBucket(LiquidRegistry.LIQUID_RED_CONCRETE);
			generateBucket(LiquidRegistry.LIQUID_BLACK_CONCRETE);
		}

		private void generateBucket(LiquidBlockReg blockReg) {
			withExistingParent(blockReg.getBucketRegistry().getId().getPath(), new ResourceLocation("forge", "item/bucket"))
					.customLoader(DynamicFluidContainerModelBuilder::begin)
					.fluid(blockReg.getSource());
		}
	}
}
