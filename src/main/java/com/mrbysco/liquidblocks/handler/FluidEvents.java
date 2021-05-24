package com.mrbysco.liquidblocks.handler;

import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class FluidEvents {

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void worldTick(WorldTickEvent event) {
		if(event.phase == TickEvent.Phase.START && event.phase == Phase.START) return;

		World world = event.world;
		if (world.getGameTime() % 20 == 0) {

		}
//		System.out.println(event.getState());
	}
}
