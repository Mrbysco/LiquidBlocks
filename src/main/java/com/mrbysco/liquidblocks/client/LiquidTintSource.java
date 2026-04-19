package com.mrbysco.liquidblocks.client;

import com.mrbysco.liquidblocks.fluid.BlockFluidType;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.client.fluid.FluidTintSource;

public class LiquidTintSource implements FluidTintSource {
	@Override
	public int color(FluidState state) {
		if (state.getFluidType() instanceof BlockFluidType blockFluidType) {
			return blockFluidType.getColor();
		}
		return 0;
	}
}
