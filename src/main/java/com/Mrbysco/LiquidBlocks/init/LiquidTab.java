package com.Mrbysco.LiquidBlocks.init;

import com.Mrbysco.LiquidBlocks.LiquidReference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class LiquidTab extends CreativeTabs{

	public LiquidTab() {
		super(LiquidReference.MOD_ID);
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Items.BUCKET);
	}
	
	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> list) {		
		list.add(FluidUtil.getFilledBucket(new FluidStack(LiquidRegistry.liquidDirt, 1000)));
		list.add(FluidUtil.getFilledBucket(new FluidStack(LiquidRegistry.liquidCoarseDirt, 1000)));
		list.add(FluidUtil.getFilledBucket(new FluidStack(LiquidRegistry.liquidPodzol, 1000)));
		
		list.add(FluidUtil.getFilledBucket(new FluidStack(LiquidRegistry.liquidStone, 1000)));
		list.add(FluidUtil.getFilledBucket(new FluidStack(LiquidRegistry.liquidGranite, 1000)));
		list.add(FluidUtil.getFilledBucket(new FluidStack(LiquidRegistry.liquidDiorite, 1000)));
		list.add(FluidUtil.getFilledBucket(new FluidStack(LiquidRegistry.liquidAndesite, 1000)));
		
		list.add(FluidUtil.getFilledBucket(new FluidStack(LiquidRegistry.liquidSandstone, 1000)));
		list.add(FluidUtil.getFilledBucket(new FluidStack(LiquidRegistry.liquidRedSandstone, 1000)));

		list.add(FluidUtil.getFilledBucket(new FluidStack(LiquidRegistry.liquidNetherrack, 1000)));
		list.add(FluidUtil.getFilledBucket(new FluidStack(LiquidRegistry.liquidSoulsand, 1000)));
		list.add(FluidUtil.getFilledBucket(new FluidStack(LiquidRegistry.liquidMagmablock, 1000)));
		list.add(FluidUtil.getFilledBucket(new FluidStack(LiquidRegistry.liquidGlowstone, 1000)));

		list.add(FluidUtil.getFilledBucket(new FluidStack(LiquidRegistry.liquidSand, 1000)));
		list.add(FluidUtil.getFilledBucket(new FluidStack(LiquidRegistry.liquidRedsand, 1000)));
		list.add(FluidUtil.getFilledBucket(new FluidStack(LiquidRegistry.liquidGravel, 1000)));
		
		list.add(FluidUtil.getFilledBucket(new FluidStack(LiquidRegistry.liquidOre, 1000)));
		
		list.add(FluidUtil.getFilledBucket(new FluidStack(LiquidRegistry.liquidClay, 1000)));
		list.add(FluidUtil.getFilledBucket(new FluidStack(LiquidRegistry.liquidTerracotta, 1000)));
		
		for(int i = 0; i < EnumDyeColor.values().length; i++)
	    {
			list.add(FluidUtil.getFilledBucket(new FluidStack(LiquidRegistry.liquidStainedTerracotta[i], 1000)));
			list.add(FluidUtil.getFilledBucket(new FluidStack(LiquidRegistry.liquidConcrete[i], 1000)));
	    }
		
		super.displayAllRelevantItems(list);
	}
	
}
