package com.mrbysco.liquidblocks.init.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.NormalCraftingRecipe;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class ShapelessNoRemainderRecipe extends NormalCraftingRecipe {
	public static final MapCodec<ShapelessNoRemainderRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
			i -> i.group(
							Recipe.CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
							CraftingRecipe.CraftingBookInfo.MAP_CODEC.forGetter(o -> o.bookInfo),
							ItemStackTemplate.CODEC.fieldOf("result").forGetter(o -> o.result),
							com.mojang.serialization.Codec.lazyInitialized(() ->
											Ingredient.CODEC.listOf(1,
													ShapedRecipePattern.getMaxHeight() * ShapedRecipePattern.getMaxWidth()))
									.fieldOf("ingredients").forGetter(o -> o.ingredients)
					)
					.apply(i, ShapelessNoRemainderRecipe::new)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, ShapelessNoRemainderRecipe> STREAM_CODEC = StreamCodec.composite(
			Recipe.CommonInfo.STREAM_CODEC,
			o -> o.commonInfo,
			CraftingRecipe.CraftingBookInfo.STREAM_CODEC,
			o -> o.bookInfo,
			ItemStackTemplate.STREAM_CODEC,
			o -> o.result,
			Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
			o -> o.ingredients,
			ShapelessNoRemainderRecipe::new
	);
	public static final RecipeSerializer<ShapelessNoRemainderRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);
	public final ItemStackTemplate result;
	public final List<Ingredient> ingredients;
	private final boolean isSimple;

	public ShapelessNoRemainderRecipe(Recipe.CommonInfo commonInfo, CraftingRecipe.CraftingBookInfo bookInfo, ItemStackTemplate result, List<Ingredient> ingredients) {
		super(commonInfo, bookInfo);
		this.result = result;
		this.ingredients = ingredients;
		this.isSimple = ingredients.stream().allMatch(Ingredient::isSimple);
	}

	@Override
	public RecipeSerializer<ShapelessNoRemainderRecipe> getSerializer() {
		return LiquidRecipes.SHAPELESS_NO_REMAINDER_SERIALIZER.get();
	}

	@Override
	protected PlacementInfo createPlacementInfo() {
		return PlacementInfo.create(this.ingredients);
	}

	@Override
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

	/**
	 * {@return the result of this shapeless recipe or null if it is not static and needs ot be obtained by assembling it}
	 */
	public @Nullable ItemStackTemplate result() {
		return result;
	}

	@Override
	public ItemStack assemble(CraftingInput input) {
		return this.result.create();
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
}
