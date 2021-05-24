package com.mrbysco.liquidblocks.init;

import com.mrbysco.liquidblocks.init.conditions.CraftWithIceCondition;
import com.mrbysco.liquidblocks.init.conditions.CraftWithWaterBottleCondition;
import com.mrbysco.liquidblocks.init.conditions.CraftWithWaterBucketCondition;
import com.mrbysco.liquidblocks.init.conditions.EnableLiquidOreCondition;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LiquidConditions {
	@SubscribeEvent
	public void onRegisterSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
		CraftingHelper.register(CraftWithIceCondition.Serializer.INSTANCE);
		CraftingHelper.register(CraftWithWaterBottleCondition.Serializer.INSTANCE);
		CraftingHelper.register(CraftWithWaterBucketCondition.Serializer.INSTANCE);
		CraftingHelper.register(EnableLiquidOreCondition.Serializer.INSTANCE);
	}
}
