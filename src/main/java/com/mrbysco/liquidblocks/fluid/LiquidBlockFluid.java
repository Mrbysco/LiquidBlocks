package com.mrbysco.liquidblocks.fluid;

import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.StateContainer;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class LiquidBlockFluid {
	public static class Flowing extends ForgeFlowingFluid {
		public Flowing(Properties properties) {
			super(properties);
			setDefaultState(getStateContainer().getBaseState().with(LEVEL_1_8, 7));
		}

		protected void fillStateContainer(StateContainer.Builder<Fluid, FluidState> builder) {
			super.fillStateContainer(builder);
			builder.add(LEVEL_1_8);
		}

		@Override
		public boolean isSource(FluidState state) {
			return false;
		}

		@Override
		public int getLevel(FluidState state) {
			return state.get(LEVEL_1_8);
		}
	}

	public static class Source extends ForgeFlowingFluid {
		public Source(Properties properties)
		{
			super(properties);
		}

		public int getLevel(FluidState state) {
			return 8;
		}

		public boolean isSource(FluidState state) {
			return true;
		}

		@Override
		protected boolean canSourcesMultiply() {
			return false;
		}
	}
}
