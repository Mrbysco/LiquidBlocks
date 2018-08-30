package com.Mrbysco.LiquidBlocks.init;

import java.util.ArrayList;
import java.util.Locale;

import com.Mrbysco.LiquidBlocks.LiquidBlocks;
import com.Mrbysco.LiquidBlocks.LiquidReference;
import com.Mrbysco.LiquidBlocks.blocks.BlockLiquidBlock;
import com.Mrbysco.LiquidBlocks.blocks.LiquidMolten;
import com.Mrbysco.LiquidBlocks.config.LiquidConfigGen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

@EventBusSubscriber
public class LiquidRegistry {
	public static LiquidMolten liquidDirt, liquidCoarseDirt, liquidPodzol;
	public static LiquidMolten liquidStone, liquidGranite, liquidDiorite, liquidAndesite;
	
	public static ArrayList<Fluid> LIQUIDS = new ArrayList<>();
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
	    IForgeRegistry<Block> registry = event.getRegistry();
	    
	    liquidDirt = createLiquid("liquiddirt", 0x392C20);
	    registerFluidBlock(registry, liquidDirt, Material.WATER, Blocks.DIRT.getDefaultState(), LiquidConfigGen.liquid.dirtSolidifyTime);
	    
	    liquidCoarseDirt = createLiquid("liquidcoarsedirt", 0x392C20);
	    registerFluidBlock(registry, liquidCoarseDirt, Material.WATER, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT), LiquidConfigGen.liquid.dirtSolidifyTime);
	    
	    liquidPodzol = createLiquid("liquidpodzol", 0x442A14);
	    registerFluidBlock(registry, liquidPodzol, Material.WATER, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL), LiquidConfigGen.liquid.dirtSolidifyTime);
	
	    liquidStone = createLiquid("liquidstone", 0x7f7f7f, 1000);
	    registerFluidBlock(registry, liquidStone, Material.LAVA, Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.STONE), LiquidConfigGen.liquid.stoneSolidifyTime);

	    liquidGranite = createLiquid("liquidgranite", 0xb48877, 1000);
	    registerFluidBlock(registry, liquidGranite, Material.LAVA, Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE), LiquidConfigGen.liquid.stoneSolidifyTime);

	    liquidDiorite = createLiquid("liquiddiorite", 0xe0e0e3, 1000);
	    registerFluidBlock(registry, liquidDiorite, Material.LAVA, Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), LiquidConfigGen.liquid.stoneSolidifyTime);

	    liquidAndesite = createLiquid("liquidandesite", 0xababac, 1000);
	    registerFluidBlock(registry, liquidAndesite, Material.LAVA, Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE), LiquidConfigGen.liquid.stoneSolidifyTime);

	}
	
	@SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
		IForgeRegistry<Item> registry = event.getRegistry();

		FluidRegistry.addBucketForFluid(liquidDirt);
		FluidRegistry.addBucketForFluid(liquidCoarseDirt);
		FluidRegistry.addBucketForFluid(liquidPodzol);
		
		FluidRegistry.addBucketForFluid(liquidStone);
		FluidRegistry.addBucketForFluid(liquidGranite);
		FluidRegistry.addBucketForFluid(liquidDiorite);
		FluidRegistry.addBucketForFluid(liquidAndesite);
    }

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
	    LiquidBlocks.proxy.registerModels();
	}
	
	public static LiquidMolten createLiquid(String liquidName, int color, int temperature, int density, int viscosity, int luminosity)
	{
		final String texturePrefix = LiquidReference.MOD_PREFIX + "blocks/";
		
		LiquidMolten theFluid = new LiquidMolten(liquidName, color);
		theFluid.setTemperature(temperature);
		theFluid.setDensity(density); 
		theFluid.setViscosity(viscosity); //Higher equals slower
		theFluid.setLuminosity(luminosity);
		
		theFluid.setUnlocalizedName(LiquidReference.MOD_PREFIX + theFluid.getName());
		FluidRegistry.registerFluid(theFluid);

		return theFluid;
	}
	
	public static LiquidMolten createLiquid(String liquidName, int color, int temperature, int density, int viscosity)
	{
		final String texturePrefix = LiquidReference.MOD_PREFIX + "blocks/";
		
		LiquidMolten theFluid = new LiquidMolten(liquidName, color);
		theFluid.setTemperature(temperature);
	    theFluid.setDensity(density); 
	    theFluid.setViscosity(viscosity); //Higher equals slower
	    
		theFluid.setUnlocalizedName(LiquidReference.MOD_PREFIX + theFluid.getName());
		FluidRegistry.registerFluid(theFluid);

	    
		return theFluid;
	}
	
	public static LiquidMolten createLiquid(String liquidName, int color, int temperature, int density)
	{
		final String texturePrefix = LiquidReference.MOD_PREFIX + "blocks/";
		
		LiquidMolten theFluid = new LiquidMolten(liquidName, color);
		theFluid.setTemperature(temperature);
	    theFluid.setDensity(density); 

		theFluid.setUnlocalizedName(LiquidReference.MOD_PREFIX + theFluid.getName());
		FluidRegistry.registerFluid(theFluid);
	    
		return theFluid;
	}
	
	public static LiquidMolten createLiquid(String liquidName, int color, int temperature)
	{
		final String texturePrefix = LiquidReference.MOD_PREFIX + "blocks/";
		
		LiquidMolten theFluid = new LiquidMolten(liquidName, color);
		theFluid.setTemperature(temperature);
		
		theFluid.setUnlocalizedName(LiquidReference.MOD_PREFIX + theFluid.getName());
	    FluidRegistry.registerFluid(theFluid);
	    
		return theFluid;
	}
	
	public static LiquidMolten createLiquid(String liquidName, int color)
	{
		final String texturePrefix = LiquidReference.MOD_PREFIX + "blocks/";
		
		LiquidMolten theFluid = new LiquidMolten(liquidName, color);
		
		theFluid.setUnlocalizedName(LiquidReference.MOD_PREFIX + theFluid.getName());
	    FluidRegistry.registerFluid(theFluid);
	    
		return theFluid;
	}
	
	public static BlockFluidBase registerFluidBlock(IForgeRegistry<Block> registry, Fluid fluid, Material material, IBlockState state, int solidifyTime) {
	    return registerBlock(registry, new BlockLiquidBlock(fluid, material, state, solidifyTime), fluid.getName());
	}
	
	protected static <T extends Block> T registerBlock(IForgeRegistry<Block> registry, T block, String name) {
		if(!name.equals(name.toLowerCase(Locale.US))) {
			throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! Block: %s", name));
		}

		String prefixedName = LiquidReference.MOD_PREFIX + name;
		block.setUnlocalizedName(prefixedName);

	    register(registry, block, name);
	    return block;
	}
	
	 protected static <T extends IForgeRegistryEntry<T>> T register(IForgeRegistry<T> registry, T thing, String name) {
		 thing.setRegistryName(new ResourceLocation(LiquidReference.MOD_ID, name));
		 registry.register(thing);
		 return thing;
	 }
}
