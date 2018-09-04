package com.Mrbysco.LiquidBlocks.init.recipes;

import javax.annotation.Nonnull;

import com.Mrbysco.LiquidBlocks.LiquidReference;
import com.google.gson.JsonObject;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class LiquidShapedRecipeFactory implements IRecipeFactory{
	
    @Override
    public IRecipe parse( JsonContext context, JsonObject json )
    {
    	ShapelessOreRecipe recipe = ShapelessOreRecipe.factory(context, json);
    	return new BucketlessShapedRecipe(new ResourceLocation(LiquidReference.MOD_ID, "liquid_ore_bucket_shaped"), recipe.getRecipeOutput(), recipe.getIngredients().toArray());    
    }
    
    public static class BucketlessShapedRecipe extends ShapedOreRecipe{
    	
    	public BucketlessShapedRecipe(ResourceLocation group, ItemStack result, Object[] recipe) {
			super(group, result, recipe);
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
