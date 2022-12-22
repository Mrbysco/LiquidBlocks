package com.mrbysco.liquidblocks.init;

import com.mrbysco.liquidblocks.LiquidBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class LiquidTab {

	private static CreativeModeTab MAIN_TAB;

	@SubscribeEvent
	public void registerCreativeTabs(final CreativeModeTabEvent.Register event) {
		MAIN_TAB = event.registerCreativeModeTab(new ResourceLocation(LiquidBlocks.MOD_ID, "tab"), builder ->
				builder.icon(() -> new ItemStack(Items.BUCKET))
						.title(Component.translatable("itemGroup.liquidblocks"))
						.displayItems((features, output, hasPermissions) -> {
							List<ItemStack> stacks = LiquidRegistry.ITEMS.getEntries().stream().map(reg -> new ItemStack(reg.get())).toList();
							output.acceptAll(stacks);
						}));
	}
}
