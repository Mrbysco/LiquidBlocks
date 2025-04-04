package com.mrbysco.liquidblocks.init.recipes;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapedCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class ShapedNoRemainderRecipe implements CraftingRecipe {
	public final ShapedRecipePattern pattern;
	final ItemStack result;
	final String group;
	final CraftingBookCategory category;
	final boolean showNotification;
	@Nullable
	private PlacementInfo placementInfo;

	public ShapedNoRemainderRecipe(String group, CraftingBookCategory category, ShapedRecipePattern pattern, ItemStack result, boolean showNotification) {
		this.group = group;
		this.category = category;
		this.pattern = pattern;
		this.result = result;
		this.showNotification = showNotification;
	}

	public ShapedNoRemainderRecipe(String group, CraftingBookCategory category, ShapedRecipePattern pattern, ItemStack result) {
		this(group, category, pattern, result, true);
	}

	@Override
	public RecipeSerializer<ShapedNoRemainderRecipe> getSerializer() {
		return LiquidRecipes.SHAPED_NO_REMAINDER_SERIALIZER.get();
	}

	@Override
	public String group() {
		return this.group;
	}

	@Override
	public CraftingBookCategory category() {
		return this.category;
	}

	@VisibleForTesting
	public List<Optional<Ingredient>> getIngredients() {
		return this.pattern.ingredients();
	}

	@Override
	public PlacementInfo placementInfo() {
		if (this.placementInfo == null) {
			this.placementInfo = PlacementInfo.createFromOptionals(this.pattern.ingredients());
		}

		return this.placementInfo;
	}

	@Override
	public boolean showNotification() {
		return this.showNotification;
	}

	public boolean matches(CraftingInput input, Level level) {
		return this.pattern.matches(input);
	}

	public ItemStack assemble(CraftingInput input, HolderLookup.Provider provider) {
		return this.result.copy();
	}

	public int getWidth() {
		return this.pattern.width();
	}

	public int getHeight() {
		return this.pattern.height();
	}

	@Override
	public List<RecipeDisplay> display() {
		return List.of(
				new ShapedCraftingRecipeDisplay(
						this.pattern.width(),
						this.pattern.height(),
						this.pattern.ingredients().stream().map(ingredient -> ingredient.map(Ingredient::display).orElse(SlotDisplay.Empty.INSTANCE)).toList(),
						new SlotDisplay.ItemStackSlotDisplay(this.result),
						new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)
				)
		);
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingInput inv) {
		return NonNullList.withSize(inv.size(), ItemStack.EMPTY);
	}

	public static class Serializer implements RecipeSerializer<ShapedNoRemainderRecipe> {
		public static final MapCodec<ShapedNoRemainderRecipe> CODEC = RecordCodecBuilder.mapCodec(
				p_340778_ -> p_340778_.group(
								Codec.STRING.optionalFieldOf("group", "").forGetter(p_311729_ -> p_311729_.group),
								CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(p_311732_ -> p_311732_.category),
								ShapedRecipePattern.MAP_CODEC.forGetter(p_311733_ -> p_311733_.pattern),
								ItemStack.STRICT_CODEC.fieldOf("result").forGetter(p_311730_ -> p_311730_.result),
								Codec.BOOL.optionalFieldOf("show_notification", Boolean.valueOf(true)).forGetter(p_311731_ -> p_311731_.showNotification)
						)
						.apply(p_340778_, ShapedNoRemainderRecipe::new)
		);
		public static final StreamCodec<RegistryFriendlyByteBuf, ShapedNoRemainderRecipe> STREAM_CODEC = StreamCodec.of(
				ShapedNoRemainderRecipe.Serializer::toNetwork, ShapedNoRemainderRecipe.Serializer::fromNetwork
		);

		@Override
		public MapCodec<ShapedNoRemainderRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, ShapedNoRemainderRecipe> streamCodec() {
			return STREAM_CODEC;
		}

		private static ShapedNoRemainderRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
			String s = buffer.readUtf();
			CraftingBookCategory craftingbookcategory = buffer.readEnum(CraftingBookCategory.class);
			ShapedRecipePattern shapedrecipepattern = ShapedRecipePattern.STREAM_CODEC.decode(buffer);
			ItemStack itemstack = ItemStack.STREAM_CODEC.decode(buffer);
			boolean flag = buffer.readBoolean();
			return new ShapedNoRemainderRecipe(s, craftingbookcategory, shapedrecipepattern, itemstack, flag);
		}

		private static void toNetwork(RegistryFriendlyByteBuf buffer, ShapedNoRemainderRecipe recipe) {
			buffer.writeUtf(recipe.group);
			buffer.writeEnum(recipe.category);
			ShapedRecipePattern.STREAM_CODEC.encode(buffer, recipe.pattern);
			ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
			buffer.writeBoolean(recipe.showNotification);
		}
	}
}