package com.mrbysco.liquidblocks.data.builder;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mrbysco.liquidblocks.init.recipes.LiquidRecipes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.CraftingRecipeBuilder;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShapelessRecipeNoRemainderBuilder extends CraftingRecipeBuilder implements RecipeBuilder {
	private final RecipeCategory category;
	private final Item result;
	private final int count;
	private final List<Ingredient> ingredients = Lists.newArrayList();
	private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
	@Nullable
	private String group;

	public ShapelessRecipeNoRemainderBuilder(ItemLike p_126180_, int p_126181_) {
		this.category = RecipeCategory.MISC;
		this.result = p_126180_.asItem();
		this.count = p_126181_;
	}

	public static ShapelessRecipeNoRemainderBuilder shapeless(ItemLike p_126190_) {
		return new ShapelessRecipeNoRemainderBuilder(p_126190_, 1);
	}

	public static ShapelessRecipeNoRemainderBuilder shapeless(ItemLike p_126192_, int p_126193_) {
		return new ShapelessRecipeNoRemainderBuilder(p_126192_, p_126193_);
	}

	public ShapelessRecipeNoRemainderBuilder requires(TagKey<Item> p_206420_) {
		return this.requires(Ingredient.of(p_206420_));
	}

	public ShapelessRecipeNoRemainderBuilder requires(ItemLike p_126210_) {
		return this.requires(p_126210_, 1);
	}

	public ShapelessRecipeNoRemainderBuilder requires(ItemLike p_126212_, int p_126213_) {
		for (int i = 0; i < p_126213_; ++i) {
			this.requires(Ingredient.of(p_126212_));
		}

		return this;
	}

	public ShapelessRecipeNoRemainderBuilder requires(Ingredient p_126185_) {
		return this.requires(p_126185_, 1);
	}

	public ShapelessRecipeNoRemainderBuilder requires(Ingredient p_126187_, int p_126188_) {
		for (int i = 0; i < p_126188_; ++i) {
			this.ingredients.add(p_126187_);
		}

		return this;
	}

	public ShapelessRecipeNoRemainderBuilder unlockedBy(String p_176781_, Criterion<?> p_300897_) {
		this.criteria.put(p_176781_, p_300897_);
		return this;
	}

	public ShapelessRecipeNoRemainderBuilder group(@Nullable String group) {
		this.group = group;
		return this;
	}

	public Item getResult() {
		return this.result;
	}

	public void save(RecipeOutput recipeOutput, ResourceLocation id) {
		this.ensureValid(id);
		Advancement.Builder advancement$builder = recipeOutput.advancement()
				.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
				.rewards(AdvancementRewards.Builder.recipe(id))
				.requirements(AdvancementRequirements.Strategy.OR);
		this.criteria.forEach(advancement$builder::addCriterion);
		recipeOutput.accept(
				new ShapelessRecipeBuilder.Result(
						id,
						this.result,
						this.count,
						this.group == null ? "" : this.group,
						determineBookCategory(this.category),
						this.ingredients,
						advancement$builder.build(id.withPrefix("recipes/" + this.category.getFolderName() + "/"))
				)
		);
	}

	private void ensureValid(ResourceLocation p_126208_) {
		if (this.criteria.isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + p_126208_);
		}
	}

	public static class Result extends CraftingRecipeBuilder.CraftingResult {
		private final ResourceLocation id;
		private final Item result;
		private final int count;
		private final String group;
		private final List<Ingredient> ingredients;
		private final AdvancementHolder advancement;

		public Result(
				ResourceLocation p_249007_,
				Item p_248667_,
				int p_249014_,
				String p_248592_,
				CraftingBookCategory p_249485_,
				List<Ingredient> p_252312_,
				AdvancementHolder p_301041_
		) {
			super(p_249485_);
			this.id = p_249007_;
			this.result = p_248667_;
			this.count = p_249014_;
			this.group = p_248592_;
			this.ingredients = p_252312_;
			this.advancement = p_301041_;
		}

		@Override
		public void serializeRecipeData(JsonObject p_126230_) {
			super.serializeRecipeData(p_126230_);
			if (!this.group.isEmpty()) {
				p_126230_.addProperty("group", this.group);
			}

			JsonArray jsonarray = new JsonArray();

			for (Ingredient ingredient : this.ingredients) {
				jsonarray.add(ingredient.toJson(false));
			}

			p_126230_.add("ingredients", jsonarray);
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("item", BuiltInRegistries.ITEM.getKey(this.result).toString());
			if (this.count > 1) {
				jsonobject.addProperty("count", this.count);
			}

			p_126230_.add("result", jsonobject);
		}

		@Override
		public RecipeSerializer<?> type() {
			return LiquidRecipes.SHAPELESS_NO_REMAINDER_SERIALIZER.get();
		}

		@Override
		public ResourceLocation id() {
			return this.id;
		}

		@Override
		public AdvancementHolder advancement() {
			return this.advancement;
		}
	}
}