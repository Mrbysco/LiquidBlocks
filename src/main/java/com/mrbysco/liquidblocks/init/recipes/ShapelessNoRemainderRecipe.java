package com.mrbysco.liquidblocks.init.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class ShapelessNoRemainderRecipe extends ShapelessRecipe {
	static int MAX_WIDTH = 3;
	static int MAX_HEIGHT = 3;

	private final ResourceLocation id;
	private final String group;
	private final ItemStack recipeOutput;
	private final NonNullList<Ingredient> recipeItems;
	private final boolean isSimple;

	public ShapelessNoRemainderRecipe(ResourceLocation idIn, String groupIn, ItemStack recipeOutputIn, NonNullList<Ingredient> recipeItemsIn) {
		super(idIn, groupIn, recipeOutputIn, recipeItemsIn);
		this.id = idIn;
		this.group = groupIn;
		this.recipeOutput = recipeOutputIn;
		this.recipeItems = recipeItemsIn;
		this.isSimple = recipeItemsIn.stream().allMatch(Ingredient::isSimple);
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return LiquidRecipes.SHAPELESS_NO_REMAINDER_SERIALIZER.get();
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
		NonNullList<ItemStack> nonnulllist = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);

		return NonNullList.create();
	}

	public static class SerializerShapelessNoRemainderRecipe extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>>  implements IRecipeSerializer<ShapelessNoRemainderRecipe> {
		public ShapelessNoRemainderRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			String s = JSONUtils.getAsString(json, "group", "");
			NonNullList<Ingredient> nonnulllist = readIngredients(JSONUtils.getAsJsonArray(json, "ingredients"));
			if (nonnulllist.isEmpty()) {
				throw new JsonParseException("No ingredients for shapeless recipe");
			} else if (nonnulllist.size() > ShapelessNoRemainderRecipe.MAX_WIDTH * ShapelessNoRemainderRecipe.MAX_HEIGHT) {
				throw new JsonParseException("Too many ingredients for shapeless recipe the max is " + (ShapelessNoRemainderRecipe.MAX_WIDTH * ShapelessNoRemainderRecipe.MAX_HEIGHT));
			} else {
				ItemStack itemstack = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"));
				return new ShapelessNoRemainderRecipe(recipeId, s, itemstack, nonnulllist);
			}
		}

		private static NonNullList<Ingredient> readIngredients(JsonArray ingredientArray) {
			NonNullList<Ingredient> nonnulllist = NonNullList.create();

			for(int i = 0; i < ingredientArray.size(); ++i) {
				Ingredient ingredient = Ingredient.fromJson(ingredientArray.get(i));
				if (!ingredient.isEmpty()) {
					nonnulllist.add(ingredient);
				}
			}

			return nonnulllist;
		}

		public ShapelessNoRemainderRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
			String s = buffer.readUtf(32767);
			int i = buffer.readVarInt();
			NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

			for(int j = 0; j < nonnulllist.size(); ++j) {
				nonnulllist.set(j, Ingredient.fromNetwork(buffer));
			}

			ItemStack itemstack = buffer.readItem();
			return new ShapelessNoRemainderRecipe(recipeId, s, itemstack, nonnulllist);
		}

		public void toNetwork(PacketBuffer buffer, ShapelessNoRemainderRecipe recipe) {
			buffer.writeUtf(recipe.group);
			buffer.writeVarInt(recipe.recipeItems.size());

			for(Ingredient ingredient : recipe.recipeItems) {
				ingredient.toNetwork(buffer);
			}

			buffer.writeItem(recipe.recipeOutput);
		}
	}
}
