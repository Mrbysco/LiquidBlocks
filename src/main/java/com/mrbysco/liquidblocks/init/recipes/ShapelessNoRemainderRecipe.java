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
import net.minecraft.world.item.crafting.CraftingRecipeCodecs;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class ShapelessNoRemainderRecipe extends ShapelessRecipe {
	static int MAX_WIDTH = 3;
	static int MAX_HEIGHT = 3;

	final String group;
	final CraftingBookCategory category;
	final ItemStack result;
	final NonNullList<Ingredient> ingredients;
	private final boolean isSimple;

	public ShapelessNoRemainderRecipe(String groupIn, CraftingBookCategory bookCategory, ItemStack recipeOutputIn, NonNullList<Ingredient> recipeItemsIn) {
		super(groupIn, bookCategory, recipeOutputIn, recipeItemsIn);
		this.group = groupIn;
		this.category = bookCategory;
		this.result = recipeOutputIn;
		this.ingredients = recipeItemsIn;
		this.isSimple = recipeItemsIn.stream().allMatch(Ingredient::isSimple);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return LiquidRecipes.SHAPELESS_NO_REMAINDER_SERIALIZER.get();
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
		NonNullList<ItemStack> nonnulllist = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);

		return nonnulllist;
	}

	public static class Serializer implements RecipeSerializer<ShapelessNoRemainderRecipe> {
		private static final net.minecraft.resources.ResourceLocation NAME = new net.minecraft.resources.ResourceLocation("minecraft", "crafting_shapeless");
		private static final Codec<ShapelessNoRemainderRecipe> CODEC = RecordCodecBuilder.create(
				p_300958_ -> p_300958_.group(
								ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(p_301127_ -> p_301127_.group),
								CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(p_301133_ -> p_301133_.category),
								CraftingRecipeCodecs.ITEMSTACK_OBJECT_CODEC.fieldOf("result").forGetter(p_301142_ -> p_301142_.result),
								Ingredient.CODEC_NONEMPTY
										.listOf()
										.fieldOf("ingredients")
										.flatXmap(
												p_301021_ -> {
													Ingredient[] aingredient = p_301021_
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
										.forGetter(p_300975_ -> p_300975_.ingredients)
						)
						.apply(p_300958_, ShapelessNoRemainderRecipe::new)
		);

		@Override
		public Codec<ShapelessNoRemainderRecipe> codec() {
			return CODEC;
		}

		public ShapelessNoRemainderRecipe fromNetwork(FriendlyByteBuf p_44294_) {
			String s = p_44294_.readUtf();
			CraftingBookCategory craftingbookcategory = p_44294_.readEnum(CraftingBookCategory.class);
			int i = p_44294_.readVarInt();
			NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

			for(int j = 0; j < nonnulllist.size(); ++j) {
				nonnulllist.set(j, Ingredient.fromNetwork(p_44294_));
			}

			ItemStack itemstack = p_44294_.readItem();
			return new ShapelessNoRemainderRecipe(s, craftingbookcategory, itemstack, nonnulllist);
		}

		public void toNetwork(FriendlyByteBuf p_44281_, ShapelessNoRemainderRecipe p_44282_) {
			p_44281_.writeUtf(p_44282_.group);
			p_44281_.writeEnum(p_44282_.category);
			p_44281_.writeVarInt(p_44282_.ingredients.size());

			for(Ingredient ingredient : p_44282_.ingredients) {
				ingredient.toNetwork(p_44281_);
			}

			p_44281_.writeItem(p_44282_.result);
		}
	}
}
