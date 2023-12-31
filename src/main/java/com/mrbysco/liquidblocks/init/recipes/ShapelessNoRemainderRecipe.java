package com.mrbysco.liquidblocks.init.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class ShapelessNoRemainderRecipe extends ShapelessRecipe {
	final String group;
	final CraftingBookCategory category;
	final ItemStack result;
	final NonNullList<Ingredient> ingredients;

	public ShapelessNoRemainderRecipe(String groupIn, CraftingBookCategory bookCategory, ItemStack recipeOutputIn, NonNullList<Ingredient> recipeItemsIn) {
		super(groupIn, bookCategory, recipeOutputIn, recipeItemsIn);
		this.group = groupIn;
		this.category = bookCategory;
		this.result = recipeOutputIn;
		this.ingredients = recipeItemsIn;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return LiquidRecipes.SHAPELESS_NO_REMAINDER_SERIALIZER.get();
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingContainer craftingContainer) {
		NonNullList<ItemStack> nonnulllist = NonNullList.withSize(craftingContainer.getContainerSize(), ItemStack.EMPTY);

		return nonnulllist;
	}

	public static class Serializer implements RecipeSerializer<ShapelessNoRemainderRecipe> {
		private static final Codec<ShapelessNoRemainderRecipe> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(recipe -> recipe.group),
								CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(recipe -> recipe.category),
								ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
								Ingredient.CODEC_NONEMPTY
										.listOf()
										.fieldOf("ingredients")
										.flatXmap(
												list -> {
													Ingredient[] aingredient = list
															.toArray(Ingredient[]::new); //Forge skip the empty check and immediatly create the array.
													if (aingredient.length == 0) {
														return DataResult.error(() -> "No ingredients for shapeless recipe");
													} else {
														return aingredient.length > ShapedNoRemainderRecipe.MAX_HEIGHT * ShapedNoRemainderRecipe.MAX_WIDTH
																? DataResult.error(() -> "Too many ingredients for shapeless recipe. The maximum is: %s".formatted(ShapedNoRemainderRecipe.MAX_HEIGHT * ShapedNoRemainderRecipe.MAX_WIDTH))
																: DataResult.success(NonNullList.of(Ingredient.EMPTY, aingredient));
													}
												},
												DataResult::success
										)
										.forGetter(recipe -> recipe.ingredients)
						)
						.apply(instance, ShapelessNoRemainderRecipe::new)
		);

		@Override
		public Codec<ShapelessNoRemainderRecipe> codec() {
			return CODEC;
		}

		public ShapelessNoRemainderRecipe fromNetwork(FriendlyByteBuf byteBuf) {
			String s = byteBuf.readUtf();
			CraftingBookCategory craftingbookcategory = byteBuf.readEnum(CraftingBookCategory.class);
			int i = byteBuf.readVarInt();
			NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

			for (int j = 0; j < nonnulllist.size(); ++j) {
				nonnulllist.set(j, Ingredient.fromNetwork(byteBuf));
			}

			ItemStack itemstack = byteBuf.readItem();
			return new ShapelessNoRemainderRecipe(s, craftingbookcategory, itemstack, nonnulllist);
		}

		public void toNetwork(FriendlyByteBuf byteBuf, ShapelessNoRemainderRecipe shapelessNoRemainderRecipe) {
			byteBuf.writeUtf(shapelessNoRemainderRecipe.group);
			byteBuf.writeEnum(shapelessNoRemainderRecipe.category);
			byteBuf.writeVarInt(shapelessNoRemainderRecipe.ingredients.size());

			for (Ingredient ingredient : shapelessNoRemainderRecipe.ingredients) {
				ingredient.toNetwork(byteBuf);
			}

			byteBuf.writeItem(shapelessNoRemainderRecipe.result);
		}
	}
}
