package com.Mrbysco.LiquidBlocks.proxy;

import javax.annotation.Nonnull;

import com.Mrbysco.LiquidBlocks.LiquidReference;
import com.Mrbysco.LiquidBlocks.init.LiquidRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;

public class ClientProxy extends CommonProxy{

	@Override
	public void registerModels()
	{
		registerFluidModels(LiquidRegistry.liquidDirt);
		registerFluidModels(LiquidRegistry.liquidCoarseDirt);
		registerFluidModels(LiquidRegistry.liquidPodzol);
		
		registerFluidModels(LiquidRegistry.liquidStone);
		registerFluidModels(LiquidRegistry.liquidGranite);
		registerFluidModels(LiquidRegistry.liquidDiorite);
		registerFluidModels(LiquidRegistry.liquidAndesite);
		
		registerFluidModels(LiquidRegistry.liquidSandstone);
		registerFluidModels(LiquidRegistry.liquidRedSandstone);
		registerFluidModels(LiquidRegistry.liquidNetherrack);
		
		registerFluidModels(LiquidRegistry.liquidSand);
		registerFluidModels(LiquidRegistry.liquidRedsand);
		registerFluidModels(LiquidRegistry.liquidSoulsand);
		
		registerFluidModels(LiquidRegistry.liquidOre);
		
		registerFluidModels(LiquidRegistry.liquidClay);
		registerFluidModels(LiquidRegistry.liquidTerracotta);
		
		for(int i = 0; i < EnumDyeColor.values().length; i++)
	    {
			registerFluidModels(LiquidRegistry.liquidStainedTerracotta[i]);
	    }
	}
	
	public void registerFluidModels(Fluid fluid) {
	    if(fluid == null) {
	      return;
	    }

	    Block block = fluid.getBlock();
	    if(block != null) {
	      Item item = Item.getItemFromBlock(block);
	      FluidStateMapper mapper = new FluidStateMapper(fluid);

	      // item-model
	      if(item != Items.AIR) {
	        ModelLoader.registerItemVariants(item);
	        ModelLoader.setCustomMeshDefinition(item, mapper);
	      }
	      // block-model
	      ModelLoader.setCustomStateMapper(block, mapper);
	    }
	}
	
	public static class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition {

	    public final Fluid fluid;
	    public final ModelResourceLocation location;

	    public FluidStateMapper(Fluid fluid) {
	      this.fluid = fluid;

	      this.location = new ModelResourceLocation(new ResourceLocation(LiquidReference.MOD_ID, "fluid_block"), fluid.getName());
	    }

	    @Nonnull
	    @Override
	    protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
	      return location;
	    }

	    @Nonnull
	    @Override
	    public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack) {
	      return location;
	    }
	}
}
