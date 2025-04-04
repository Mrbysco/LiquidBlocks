package com.mrbysco.liquidblocks.fluid;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidType;

public class BlockFluidType extends FluidType {
	private int color;

	public BlockFluidType(int color, Properties properties) {
		super(properties);
		this.color = color;
	}

	public int getColor() {
		return color;
	}

	@Override
	public double motionScale(Entity entity) {
		return entity.level().dimensionType().ultraWarm() ? 0.007D : 0.0023333333333333335D;
	}

	@Override
	public void setItemMovement(ItemEntity entity) {
		Vec3 vec3 = entity.getDeltaMovement();
		entity.setDeltaMovement(vec3.x * (double) 0.95F, vec3.y + (double) (vec3.y < (double) 0.06F ? 5.0E-4F : 0.0F), vec3.z * (double) 0.95F);
	}
}
