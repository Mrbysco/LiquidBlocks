package com.mrbysco.liquidblocks.init.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;

public class ShapedNoRemainderRecipe extends ShapedRecipe {
	static int MAX_WIDTH = 3;
	static int MAX_HEIGHT = 3;

	final ShapedRecipePattern recipePattern;
	final ItemStack result;
	final String group;
	final CraftingBookCategory category;
	final boolean showNotification;

	public ShapedNoRemainderRecipe(String groupIn, CraftingBookCategory bookCategory, ShapedRecipePattern recipePattern, ItemStack recipeOutputIn, boolean showNotif) {
		super(groupIn, bookCategory, recipePattern, recipeOutputIn, showNotif);
		this.group = groupIn;
		this.category = bookCategory;
		this.recipePattern = recipePattern;
		this.result = recipeOutputIn;
		this.showNotification = showNotif;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return LiquidRecipes.SHAPED_NO_REMAINDER_SERIALIZER.get();
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
		NonNullList<ItemStack> nonnulllist = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);

		return nonnulllist;
	}

	@Override
	public boolean isIncomplete() {
		NonNullList<Ingredient> nonnulllist = this.getIngredients();
		return nonnulllist.isEmpty() || nonnulllist.stream().filter(ingredient -> !ingredient.isEmpty())
				.anyMatch(net.neoforged.neoforge.common.CommonHooks::hasNoElements);
	}

	public static class Serializer implements RecipeSerializer<ShapedNoRemainderRecipe> {
		public static final Codec<ShapedNoRemainderRecipe> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(recipe -> recipe.group),
								CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(recipe -> recipe.category),
								ShapedRecipePattern.MAP_CODEC.forGetter(recipe -> recipe.recipePattern),
								ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
								ExtraCodecs.strictOptionalField(Codec.BOOL, "show_notification", true).forGetter(recipe -> recipe.showNotification)
						)
						.apply(instance, ShapedNoRemainderRecipe::new)
		);

		@Override
		public Codec<ShapedNoRemainderRecipe> codec() {
			return CODEC;
		}

		public ShapedNoRemainderRecipe fromNetwork(FriendlyByteBuf byteBuf) {
			String s = byteBuf.readUtf();
			CraftingBookCategory craftingbookcategory = byteBuf.readEnum(CraftingBookCategory.class);
			ShapedRecipePattern shapedrecipepattern = ShapedRecipePattern.fromNetwork(byteBuf);

			ItemStack itemstack = byteBuf.readItem();
			boolean flag = byteBuf.readBoolean();
			return new ShapedNoRemainderRecipe(s, craftingbookcategory, shapedrecipepattern, itemstack, flag);
		}

		public void toNetwork(FriendlyByteBuf byteBuf, ShapedNoRemainderRecipe shapedNoRemainderRecipe) {
			byteBuf.writeUtf(shapedNoRemainderRecipe.group);
			byteBuf.writeEnum(shapedNoRemainderRecipe.category);
			shapedNoRemainderRecipe.recipePattern.toNetwork(byteBuf);

			byteBuf.writeItem(shapedNoRemainderRecipe.result);
			byteBuf.writeBoolean(shapedNoRemainderRecipe.showNotification);
		}
	}
}