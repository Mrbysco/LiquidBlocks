package com.mrbysco.liquidblocks.client;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrbysco.liquidblocks.fluid.BlockFluidType;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.fluid.FluidUtil;
import org.jetbrains.annotations.Nullable;

public record FluidItemTint(int defaultColor) implements ItemTintSource {
	public static final MapCodec<FluidItemTint> MAP_CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(ExtraCodecs.ARGB_COLOR_CODEC.fieldOf("default").forGetter(FluidItemTint::defaultColor))
					.apply(instance, FluidItemTint::new)
	);

	public FluidItemTint() {
		this(0xFFFFFFFF);
	}

	@Override
	public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity) {
		FluidStack fluidStack = FluidUtil.getFirstStackContained(stack);
		if (!fluidStack.isEmpty() && fluidStack.getFluidType() instanceof BlockFluidType blockFluidType) {
			return blockFluidType.getColor();
		}
		return defaultColor();
	}

	@Override
	public MapCodec<? extends ItemTintSource> type() {
		return MAP_CODEC;
	}
}