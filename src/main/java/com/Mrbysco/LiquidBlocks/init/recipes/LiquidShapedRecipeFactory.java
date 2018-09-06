package com.Mrbysco.LiquidBlocks.init.recipes;

import javax.annotation.Nonnull;

import com.Mrbysco.LiquidBlocks.LiquidReference;
import com.google.gson.JsonObject;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class LiquidShapedRecipeFactory implements IRecipeFactory{
	
    @Override
    public IRecipe parse( JsonContext context, JsonObject json )
    {
    	ShapedOreRecipe recipe = ShapedOreRecipe.factory(context, json);
		
		ShapedPrimer primer = new ShapedPrimer();
		primer.width = recipe.getRecipeWidth();
		primer.height = recipe.getRecipeHeight();
		primer.mirrored = JsonUtils.getBoolean(json, "mirrored", true);
		primer.input = recipe.getIngredients();
		
		return new BucketlessShapedRecipe(new ResourceLocation(LiquidReference.MOD_ID, "liquid_ore_bucket_shaped"), recipe.getRecipeOutput(), primer);
    }
    
    public static class BucketlessShapedRecipe extends ShapedOreRecipe{

		public BucketlessShapedRecipe(ResourceLocation group, ItemStack result, ShapedPrimer primer) {
			super(group, result, primer);
		}

		@Override
    	@Nonnull
    	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
    		NonNullList<ItemStack> ret = super.getRemainingItems(inv);
    		for (int i = 0; i < ret.size(); i++) {
    			if (ret.get(i).getItem() == Items.BUCKET || ret.get(i).getItem() == Items.GLASS_BOTTLE) {
    				ret.set(i, ItemStack.EMPTY);
    				break;
    			}
    		}
    		return ret;
    	}
    }
}
