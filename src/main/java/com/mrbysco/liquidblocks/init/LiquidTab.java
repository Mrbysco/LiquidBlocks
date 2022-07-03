package com.mrbysco.liquidblocks.init;

import com.mrbysco.liquidblocks.LiquidBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LiquidTab {
	public static final CreativeModeTab MAIN_TAB = new CreativeModeTab(LiquidBlocks.MOD_ID) {
		public ItemStack makeIcon() {
			return new ItemStack(Items.BUCKET);
		}
	};
}
