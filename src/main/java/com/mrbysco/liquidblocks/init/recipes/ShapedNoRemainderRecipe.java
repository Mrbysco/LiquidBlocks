package com.mrbysco.liquidblocks.init.recipes;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
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
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShapedNoRemainderRecipe extends ShapedRecipe {
	static int MAX_WIDTH = 3;
	static int MAX_HEIGHT = 3;

	final int width;
	final int height;
	final NonNullList<Ingredient> recipeItems;
	final ItemStack result;
	final String group;
	final CraftingBookCategory category;
	final boolean showNotification;

	public ShapedNoRemainderRecipe(String groupIn, CraftingBookCategory bookCategory, int recipeWidthIn, int recipeHeightIn, NonNullList<Ingredient> recipeItemsIn, ItemStack recipeOutputIn, boolean showNotif) {
		super(groupIn, bookCategory, recipeWidthIn, recipeHeightIn, recipeItemsIn, recipeOutputIn, showNotif);
		this.group = groupIn;
		this.category = bookCategory;
		this.width = recipeWidthIn;
		this.height = recipeHeightIn;
		this.recipeItems = recipeItemsIn;
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

	@VisibleForTesting
	static String[] shrink(List<String> p_301102_) {
		int i = Integer.MAX_VALUE;
		int j = 0;
		int k = 0;
		int l = 0;

		for (int i1 = 0; i1 < p_301102_.size(); ++i1) {
			String s = p_301102_.get(i1);
			i = Math.min(i, firstNonSpace(s));
			int j1 = lastNonSpace(s);
			j = Math.max(j, j1);
			if (j1 < 0) {
				if (k == i1) {
					++k;
				}

				++l;
			} else {
				l = 0;
			}
		}

		if (p_301102_.size() == l) {
			return new String[0];
		} else {
			String[] astring = new String[p_301102_.size() - l - k];

			for (int k1 = 0; k1 < astring.length; ++k1) {
				astring[k1] = p_301102_.get(k1 + k).substring(i, j + 1);
			}

			return astring;
		}
	}

	@Override
	public boolean isIncomplete() {
		NonNullList<Ingredient> nonnulllist = this.getIngredients();
		return nonnulllist.isEmpty() || nonnulllist.stream().filter(p_151277_ -> !p_151277_.isEmpty()).anyMatch(net.neoforged.neoforge.common.CommonHooks::hasNoElements);
	}

	private static int firstNonSpace(String p_44185_) {
		int i = 0;

		while (i < p_44185_.length() && p_44185_.charAt(i) == ' ') {
			++i;
		}

		return i;
	}

	private static int lastNonSpace(String p_44201_) {
		int i = p_44201_.length() - 1;

		while (i >= 0 && p_44201_.charAt(i) == ' ') {
			--i;
		}

		return i;
	}

	public static class Serializer implements RecipeSerializer<ShapedNoRemainderRecipe> {
		private static final net.minecraft.resources.ResourceLocation NAME = new net.minecraft.resources.ResourceLocation("minecraft", "crafting_shaped");
		static final Codec<List<String>> PATTERN_CODEC = Codec.STRING.listOf().flatXmap(p_300940_ -> {
			if (p_300940_.size() > MAX_HEIGHT) {
				return DataResult.error(() -> "Invalid pattern: too many rows, %s is maximum".formatted(MAX_HEIGHT));
			} else if (p_300940_.isEmpty()) {
				return DataResult.error(() -> "Invalid pattern: empty pattern not allowed");
			} else {
				int i = p_300940_.get(0).length();

				for (String s : p_300940_) {
					if (s.length() > MAX_WIDTH) {
						return DataResult.error(() -> "Invalid pattern: too many columns, %s is maximum".formatted(MAX_WIDTH));
					}

					if (i != s.length()) {
						return DataResult.error(() -> "Invalid pattern: each row must be the same width");
					}
				}

				return DataResult.success(p_300940_);
			}
		}, DataResult::success);
		static final Codec<String> SINGLE_CHARACTER_STRING_CODEC = Codec.STRING.flatXmap(p_300861_ -> {
			if (p_300861_.length() != 1) {
				return DataResult.error(() -> "Invalid key entry: '" + p_300861_ + "' is an invalid symbol (must be 1 character only).");
			} else {
				return " ".equals(p_300861_) ? DataResult.error(() -> "Invalid key entry: ' ' is a reserved symbol.") : DataResult.success(p_300861_);
			}
		}, DataResult::success);
		private static final Codec<ShapedNoRemainderRecipe> CODEC = ShapedNoRemainderRecipe.Serializer.RawShapedRecipe.CODEC.flatXmap(p_301248_ -> {
			String[] astring = ShapedNoRemainderRecipe.shrink(p_301248_.pattern);
			int i = astring[0].length();
			int j = astring.length;
			NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i * j, Ingredient.EMPTY);
			Set<String> set = Sets.newHashSet(p_301248_.key.keySet());

			for (int k = 0; k < astring.length; ++k) {
				String s = astring[k];

				for (int l = 0; l < s.length(); ++l) {
					String s1 = s.substring(l, l + 1);
					Ingredient ingredient = s1.equals(" ") ? Ingredient.EMPTY : p_301248_.key.get(s1);
					if (ingredient == null) {
						return DataResult.error(() -> "Pattern references symbol '" + s1 + "' but it's not defined in the key");
					}

					set.remove(s1);
					nonnulllist.set(l + i * k, ingredient);
				}
			}

			if (!set.isEmpty()) {
				return DataResult.error(() -> "Key defines symbols that aren't used in pattern: " + set);
			} else {
				ShapedNoRemainderRecipe shapedrecipe = new ShapedNoRemainderRecipe(p_301248_.group, p_301248_.category, i, j, nonnulllist, p_301248_.result, p_301248_.showNotification);
				return DataResult.success(shapedrecipe);
			}
		}, p_300934_ -> {
			throw new NotImplementedException("Serializing ShapedNoRemainderRecipe is not implemented yet.");
		});

		@Override
		public Codec<ShapedNoRemainderRecipe> codec() {
			return CODEC;
		}

		public ShapedNoRemainderRecipe fromNetwork(FriendlyByteBuf p_44240_) {
			int i = p_44240_.readVarInt();
			int j = p_44240_.readVarInt();
			String s = p_44240_.readUtf();
			CraftingBookCategory craftingbookcategory = p_44240_.readEnum(CraftingBookCategory.class);
			NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i * j, Ingredient.EMPTY);

			for (int k = 0; k < nonnulllist.size(); ++k) {
				nonnulllist.set(k, Ingredient.fromNetwork(p_44240_));
			}

			ItemStack itemstack = p_44240_.readItem();
			boolean flag = p_44240_.readBoolean();
			return new ShapedNoRemainderRecipe(s, craftingbookcategory, i, j, nonnulllist, itemstack, flag);
		}

		public void toNetwork(FriendlyByteBuf p_44227_, ShapedNoRemainderRecipe p_44228_) {
			p_44227_.writeVarInt(p_44228_.width);
			p_44227_.writeVarInt(p_44228_.height);
			p_44227_.writeUtf(p_44228_.group);
			p_44227_.writeEnum(p_44228_.category);

			for (Ingredient ingredient : p_44228_.recipeItems) {
				ingredient.toNetwork(p_44227_);
			}

			p_44227_.writeItem(p_44228_.result);
			p_44227_.writeBoolean(p_44228_.showNotification);
		}

		static record RawShapedRecipe(
				String group, CraftingBookCategory category, Map<String, Ingredient> key, List<String> pattern,
				ItemStack result, boolean showNotification
		) {
			public static final Codec<ShapedNoRemainderRecipe.Serializer.RawShapedRecipe> CODEC = RecordCodecBuilder.create(
					p_300891_ -> p_300891_.group(
									ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(p_301109_ -> p_301109_.group),
									CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(p_301117_ -> p_301117_.category),
									ExtraCodecs.strictUnboundedMap(ShapedNoRemainderRecipe.Serializer.SINGLE_CHARACTER_STRING_CODEC, Ingredient.CODEC_NONEMPTY)
											.fieldOf("key")
											.forGetter(p_301234_ -> p_301234_.key),
									ShapedNoRemainderRecipe.Serializer.PATTERN_CODEC.fieldOf("pattern").forGetter(p_301164_ -> p_301164_.pattern),
									CraftingRecipeCodecs.ITEMSTACK_OBJECT_CODEC.fieldOf("result").forGetter(p_301076_ -> p_301076_.result),
									ExtraCodecs.strictOptionalField(Codec.BOOL, "show_notification", true).forGetter(p_301293_ -> p_301293_.showNotification)
							)
							.apply(p_300891_, ShapedNoRemainderRecipe.Serializer.RawShapedRecipe::new)
			);
		}
	}
}