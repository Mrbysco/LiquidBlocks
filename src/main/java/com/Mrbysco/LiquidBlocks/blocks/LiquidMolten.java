package com.Mrbysco.LiquidBlocks.blocks;

import com.Mrbysco.LiquidBlocks.LiquidReference;

import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class LiquidMolten extends Fluid {

	public final int color;
	 
	public static ResourceLocation still_metal = new ResourceLocation(LiquidReference.MOD_ID, "blocks/molten_block_still");
	public static ResourceLocation flowing_metal = new ResourceLocation(LiquidReference.MOD_ID, "blocks/molten_block_flow");

	public LiquidMolten(String fluidName, int color) {
	    this(fluidName, color, still_metal, flowing_metal);
	}

	public LiquidMolten(String fluidName, int color, ResourceLocation still, ResourceLocation flow) {
		super(fluidName, still, flow);

		// make opaque if no alpha is set
		if(((color >> 24) & 0xFF) == 0) {
			color |= 0xFF << 24;
	    }
		this.color = color;
	
	    this.setDensity(2000); // thicker than a bowl of oatmeal
	    this.setViscosity(8500); // sloooow moving
	    this.setTemperature(1000); // not exactly lava, but still hot. Should depend on the material
	    this.setLuminosity(0);

	    // rare by default
	    setRarity(EnumRarity.UNCOMMON);
	}
	
	@Override
	public int getColor() {
	    return color;
	}

	@Override
	public String getLocalizedName(FluidStack stack) {
		String s = this.getUnlocalizedName();
	    return s == null ? "" : I18n.translateToLocal(s + ".name");
	}
}