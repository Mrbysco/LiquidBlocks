package com.mrbysco.liquidblocks.data.builder;

import com.mrbysco.liquidblocks.init.recipes.ShapelessNoRemainderRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ShapelessRecipeNoRemainderBuilder implements RecipeBuilder {
	private final HolderGetter<Item> items;
	private final RecipeCategory category;
	private final ItemStack result;
	private final List<Ingredient> ingredients = new ArrayList<>();
	private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
	@Nullable
	private String group;

	public ShapelessRecipeNoRemainderBuilder(HolderGetter<Item> items, ItemStack result) {
		this.items = items;
		this.category = RecipeCategory.MISC;
		this.result = result;
	}

	public static ShapelessRecipeNoRemainderBuilder shapeless(HolderGetter<Item> items, ItemStack result) {
		return new ShapelessRecipeNoRemainderBuilder(items, result);
	}

	public static ShapelessRecipeNoRemainderBuilder shapeless(HolderGetter<Item> items, ItemLike result) {
		return shapeless(items, result, 1);
	}

	public static ShapelessRecipeNoRemainderBuilder shapeless(HolderGetter<Item> items, ItemLike result, int count) {
		return new ShapelessRecipeNoRemainderBuilder(items, result.asItem().getDefaultInstance().copyWithCount(count));
	}

	/**
	 * Adds an ingredient that can be any item in the given tag.
	 */
	public ShapelessRecipeNoRemainderBuilder requires(TagKey<Item> tag) {
		return this.requires(Ingredient.of(this.items.getOrThrow(tag)));
	}

	/**
	 * Adds an ingredient of the given item.
	 */
	public ShapelessRecipeNoRemainderBuilder requires(ItemLike item) {
		return this.requires(item, 1);
	}

	/**
	 * Adds the given ingredient multiple times.
	 */
	public ShapelessRecipeNoRemainderBuilder requires(ItemLike item, int quantity) {
		for (int i = 0; i < quantity; i++) {
			this.requires(Ingredient.of(item));
		}

		return this;
	}

	/**
	 * Adds an ingredient.
	 */
	public ShapelessRecipeNoRemainderBuilder requires(Ingredient ingredient) {
		return this.requires(ingredient, 1);
	}

	/**
	 * Adds an ingredient multiple times.
	 */
	public ShapelessRecipeNoRemainderBuilder requires(Ingredient ingredient, int quantity) {
		for (int i = 0; i < quantity; i++) {
			this.ingredients.add(ingredient);
		}

		return this;
	}

	@Override
	public ShapelessRecipeNoRemainderBuilder unlockedBy(String p_176781_, Criterion<?> p_300897_) {
		this.criteria.put(p_176781_, p_300897_);
		return this;
	}

	@Override
	public ShapelessRecipeNoRemainderBuilder group(@Nullable String group) {
		this.group = group;
		return this;
	}

	@Override
	public Item getResult() {
		return this.result.getItem();
	}

	public void save(RecipeOutput recipeOutput, Identifier id) {
		this.save(recipeOutput, ResourceKey.create(Registries.RECIPE, id));
	}

	@Override
	public void save(RecipeOutput recipeOutput, ResourceKey<Recipe<?>> id) {
		this.ensureValid(id);
		Advancement.Builder advancement$builder = recipeOutput.advancement()
				.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
				.rewards(AdvancementRewards.Builder.recipe(id))
				.requirements(AdvancementRequirements.Strategy.OR);
		this.criteria.forEach(advancement$builder::addCriterion);
		ShapelessNoRemainderRecipe shapelessrecipe = new ShapelessNoRemainderRecipe(Objects.requireNonNullElse(this.group, ""),
				RecipeBuilder.determineBookCategory(this.category), this.result, this.ingredients);
		recipeOutput.accept(
				id, shapelessrecipe, advancement$builder.build(id.identifier().withPrefix("recipes/" + this.category.getFolderName() + "/"))
		);
	}

	private void ensureValid(ResourceKey<Recipe<?>> recipe) {
		if (this.criteria.isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + recipe.identifier());
		}
	}
}