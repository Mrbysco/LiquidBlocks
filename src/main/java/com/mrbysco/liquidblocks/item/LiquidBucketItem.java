package com.mrbysco.liquidblocks.item;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

public class LiquidBucketItem extends BucketItem {
	public LiquidBucketItem(Properties builder, Supplier<? extends Fluid> supplier) {
		super(supplier, builder);
	}
}
