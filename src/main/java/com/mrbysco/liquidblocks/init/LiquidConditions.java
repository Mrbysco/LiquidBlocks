package com.mrbysco.liquidblocks.init;

import com.mrbysco.liquidblocks.init.conditions.CraftWithIceCondition;
import com.mrbysco.liquidblocks.init.conditions.CraftWithWaterBottleCondition;
import com.mrbysco.liquidblocks.init.conditions.CraftWithWaterBucketCondition;
import com.mrbysco.liquidblocks.init.conditions.EnableLiquidOreCondition;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

public class LiquidConditions {
	@SubscribeEvent
	public void onRegisterSerializers(RegisterEvent event) {
		if (event.getRegistryKey().equals(ForgeRegistries.Keys.RECIPE_SERIALIZERS)) {
			CraftingHelper.register(CraftWithIceCondition.Serializer.INSTANCE);
			CraftingHelper.register(CraftWithWaterBottleCondition.Serializer.INSTANCE);
			CraftingHelper.register(CraftWithWaterBucketCondition.Serializer.INSTANCE);
			CraftingHelper.register(EnableLiquidOreCondition.Serializer.INSTANCE);
		}
	}
}
