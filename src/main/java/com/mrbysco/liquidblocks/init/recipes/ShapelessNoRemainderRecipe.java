package com.mrbysco.liquidblocks.init.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ShapelessNoRemainderRecipe implements CraftingRecipe {
	final String group;
	final CraftingBookCategory category;
	final ItemStack result;
	final List<Ingredient> ingredients;
	@Nullable
	private PlacementInfo placementInfo;
	private final boolean isSimple;

	public ShapelessNoRemainderRecipe(String group, CraftingBookCategory category, ItemStack result, List<Ingredient> ingredients) {
		this.group = group;
		this.category = category;
		this.result = result;
		this.ingredients = ingredients;
		this.isSimple = ingredients.stream().allMatch(Ingredient::isSimple);
	}

	@Override
	public RecipeSerializer<ShapelessNoRemainderRecipe> getSerializer() {
		return LiquidRecipes.SHAPELESS_NO_REMAINDER_SERIALIZER.get();
	}

	@Override
	public String group() {
		return this.group;
	}

	@Override
	public CraftingBookCategory category() {
		return this.category;
	}

	@Override
	public PlacementInfo placementInfo() {
		if (this.placementInfo == null) {
			this.placementInfo = PlacementInfo.create(this.ingredients);
		}

		return this.placementInfo;
	}

	public boolean matches(CraftingInput input, Level level) {
		if (input.ingredientCount() != this.ingredients.size()) {
			return false;
		} else if (!isSimple) {
			var nonEmptyItems = new java.util.ArrayList<ItemStack>(input.ingredientCount());
			for (var item : input.items())
				if (!item.isEmpty())
					nonEmptyItems.add(item);
			return net.neoforged.neoforge.common.util.RecipeMatcher.findMatches(nonEmptyItems, this.ingredients) != null;
		} else {
			return input.size() == 1 && this.ingredients.size() == 1
					? this.ingredients.getFirst().test(input.getItem(0))
					: input.stackedContents().canCraft(this, null);
		}
	}

	public ItemStack assemble(CraftingInput input, HolderLookup.Provider provider) {
		return this.result.copy();
	}

	@Override
	public List<RecipeDisplay> display() {
		return List.of(
				new ShapelessCraftingRecipeDisplay(
						this.ingredients.stream().map(Ingredient::display).toList(),
						new SlotDisplay.ItemStackSlotDisplay(this.result),
						new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)
				)
		);
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingInput craftingContainer) {
		return NonNullList.withSize(craftingContainer.size(), ItemStack.EMPTY);
	}

	public static class Serializer implements RecipeSerializer<ShapelessNoRemainderRecipe> {
		private static final MapCodec<ShapelessNoRemainderRecipe> CODEC = RecordCodecBuilder.mapCodec(
				p_360072_ -> p_360072_.group(
								Codec.STRING.optionalFieldOf("group", "").forGetter(p_301127_ -> p_301127_.group),
								CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(p_301133_ -> p_301133_.category),
								ItemStack.STRICT_CODEC.fieldOf("result").forGetter(p_301142_ -> p_301142_.result),
								Codec.lazyInitialized(() -> Ingredient.CODEC.listOf(1, 3 * 3)).fieldOf("ingredients").forGetter(p_360071_ -> p_360071_.ingredients)
						)
						.apply(p_360072_, ShapelessNoRemainderRecipe::new)
		);
		public static final StreamCodec<RegistryFriendlyByteBuf, ShapelessNoRemainderRecipe> STREAM_CODEC = StreamCodec.composite(
				ByteBufCodecs.STRING_UTF8,
				p_360074_ -> p_360074_.group,
				CraftingBookCategory.STREAM_CODEC,
				p_360073_ -> p_360073_.category,
				ItemStack.STREAM_CODEC,
				p_360070_ -> p_360070_.result,
				Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
				p_360069_ -> p_360069_.ingredients,
				ShapelessNoRemainderRecipe::new
		);

		@Override
		public MapCodec<ShapelessNoRemainderRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, ShapelessNoRemainderRecipe> streamCodec() {
			return STREAM_CODEC;
		}
	}
}
