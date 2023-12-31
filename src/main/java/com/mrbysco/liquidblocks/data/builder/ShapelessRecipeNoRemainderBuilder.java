package com.mrbysco.liquidblocks.data.builder;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class ShapelessRecipeNoRemainderBuilder implements RecipeBuilder {
	private final RecipeCategory category;
	private final Item result;
	private final int count;
	private final NonNullList<Ingredient> ingredients = NonNullList.create();
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
		ShapelessRecipe shapelessrecipe = new ShapelessRecipe((String) Objects.requireNonNullElse(this.group, ""),
				RecipeBuilder.determineBookCategory(this.category), new ItemStack(this.result, this.count), this.ingredients);
		recipeOutput.accept(id, shapelessrecipe, advancement$builder.build(id.withPrefix("recipes/" + this.category.getFolderName() + "/")));
	}

	private void ensureValid(ResourceLocation p_126208_) {
		if (this.criteria.isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + p_126208_);
		}
	}
}