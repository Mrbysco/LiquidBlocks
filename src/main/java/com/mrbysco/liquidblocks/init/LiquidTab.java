package com.mrbysco.liquidblocks.init;

import com.mrbysco.liquidblocks.LiquidBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LiquidTab{
	public static final ItemGroup MAIN_TAB = new ItemGroup(LiquidBlocks.MOD_ID) {
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(Items.BUCKET);
		}
	};
}
