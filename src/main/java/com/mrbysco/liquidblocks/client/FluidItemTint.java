package com.mrbysco.liquidblocks.client;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidUtil;
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
		return FluidUtil.getFluidContained(stack)
				.map(fluidStack -> IClientFluidTypeExtensions.of(fluidStack.getFluid()).getTintColor(fluidStack))
				.orElse(defaultColor());
	}

	@Override
	public MapCodec<? extends ItemTintSource> type() {
		return MAP_CODEC;
	}
}