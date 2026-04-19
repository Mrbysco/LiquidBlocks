package com.mrbysco.liquidblocks.data.builder;

import com.mrbysco.liquidblocks.init.recipes.ShapelessNoRemainderRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ShapelessRecipeNoRemainderBuilder implements RecipeBuilder {
	private final HolderGetter<Item> items;
	private final RecipeCategory category;
	private final ItemStackTemplate result;
	private final List<Ingredient> ingredients = new ArrayList<>();
	private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();
	private @Nullable String group;

	private ShapelessRecipeNoRemainderBuilder(HolderGetter<Item> items, RecipeCategory category, ItemStackTemplate result) {
		this.items = items;
		this.category = category;
		this.result = result;
	}

	public static ShapelessRecipeNoRemainderBuilder shapeless(HolderGetter<Item> items, RecipeCategory category, ItemStackTemplate result) {
		return new ShapelessRecipeNoRemainderBuilder(items, category, result);
	}

	public static ShapelessRecipeNoRemainderBuilder shapeless(HolderGetter<Item> items, RecipeCategory category, ItemLike item) {
		return shapeless(items, category, item, 1);
	}

	public static ShapelessRecipeNoRemainderBuilder shapeless(HolderGetter<Item> items, RecipeCategory category, ItemLike item, int count) {
		return new ShapelessRecipeNoRemainderBuilder(items, category, new ItemStackTemplate(item.asItem(), count));
	}

	public ShapelessRecipeNoRemainderBuilder requires(TagKey<Item> tag) {
		return this.requires(Ingredient.of(this.items.getOrThrow(tag)));
	}

	public ShapelessRecipeNoRemainderBuilder requires(ItemLike item) {
		return this.requires(item, 1);
	}

	public ShapelessRecipeNoRemainderBuilder requires(ItemLike item, int count) {
		for (int i = 0; i < count; i++) {
			this.requires(Ingredient.of(item));
		}

		return this;
	}

	public ShapelessRecipeNoRemainderBuilder requires(Ingredient ingredient) {
		return this.requires(ingredient, 1);
	}

	public ShapelessRecipeNoRemainderBuilder requires(Ingredient ingredient, int count) {
		for (int i = 0; i < count; i++) {
			this.ingredients.add(ingredient);
		}

		return this;
	}

	public ShapelessRecipeNoRemainderBuilder unlockedBy(String name, Criterion<?> criterion) {
		this.advancementBuilder.unlockedBy(name, criterion);
		return this;
	}

	public ShapelessRecipeNoRemainderBuilder group(@Nullable String group) {
		this.group = group;
		return this;
	}

	@Override
	public ResourceKey<Recipe<?>> defaultId() {
		return RecipeBuilder.getDefaultRecipeId(this.result);
	}

	@Override
	public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
		ShapelessNoRemainderRecipe recipe = new ShapelessNoRemainderRecipe(
				RecipeBuilder.createCraftingCommonInfo(true),
				RecipeBuilder.createCraftingBookInfo(this.category, this.group), this.result, this.ingredients
		);
		output.accept(id, recipe, this.advancementBuilder.build(output, id, this.category));
	}
}