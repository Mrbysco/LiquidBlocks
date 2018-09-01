package com.Mrbysco.LiquidBlocks.init;

import java.util.ArrayList;
import java.util.Locale;

import com.Mrbysco.LiquidBlocks.LiquidBlocks;
import com.Mrbysco.LiquidBlocks.LiquidReference;
import com.Mrbysco.LiquidBlocks.blocks.BlockLiquidBlock;
import com.Mrbysco.LiquidBlocks.blocks.BlockLiquidOre;
import com.Mrbysco.LiquidBlocks.blocks.LiquidMolten;
import com.Mrbysco.LiquidBlocks.config.LiquidConfigGen;
import com.Mrbysco.LiquidBlocks.util.LiquidUtil;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockStainedHardenedClay;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
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
	public static LiquidMolten liquidStone, liquidGranite, liquidDiorite, liquidAndesite, liquidSandstone, liquidRedSandstone, liquidNetherrack;
	
	public static LiquidMolten liquidSand, liquidRedsand, liquidSoulsand;
	
	public static LiquidMolten liquidOre;
	
	public static LiquidMolten liquidClay, liquidTerracotta;
	
	public static LiquidMolten[] liquidStainedTerracotta, liquidConcrete;
	
	public static ArrayList<Fluid> LIQUIDS = new ArrayList<>();
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
	    IForgeRegistry<Block> registry = event.getRegistry();
	    
	    //Dirt
	    liquidDirt = createLiquid("liquiddirt", 0x392C20);
	    registerFluidBlock(registry, liquidDirt, Material.WATER, Blocks.DIRT.getDefaultState(), LiquidConfigGen.liquid.dirtSolidifyTime);
	    
	    liquidCoarseDirt = createLiquid("liquidcoarsedirt", 0x392C20);
	    registerFluidBlock(registry, liquidCoarseDirt, Material.WATER, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT), LiquidConfigGen.liquid.dirtSolidifyTime);
	    
	    liquidPodzol = createLiquid("liquidpodzol", 0x442A14);
	    registerFluidBlock(registry, liquidPodzol, Material.WATER, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL), LiquidConfigGen.liquid.dirtSolidifyTime);
	
	    //Stone
	    liquidStone = createLiquid("liquidstone", 0x7f7f7f, 1000);
	    registerFluidBlock(registry, liquidStone, Material.LAVA, Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.STONE), LiquidConfigGen.liquid.stoneSolidifyTime);

	    liquidGranite = createLiquid("liquidgranite", 0xb48877, 1000);
	    registerFluidBlock(registry, liquidGranite, Material.LAVA, Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE), LiquidConfigGen.liquid.stoneSolidifyTime);

	    liquidDiorite = createLiquid("liquiddiorite", 0xe0e0e3, 1000);
	    registerFluidBlock(registry, liquidDiorite, Material.LAVA, Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), LiquidConfigGen.liquid.stoneSolidifyTime);

	    liquidAndesite = createLiquid("liquidandesite", 0xababac, 1000);
	    registerFluidBlock(registry, liquidAndesite, Material.LAVA, Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE), LiquidConfigGen.liquid.stoneSolidifyTime);
	    
	    liquidSandstone = createLiquid("liquidsandstone", 0xe6d9ae, 1000);
	    registerFluidBlock(registry, liquidSandstone, Material.LAVA, Blocks.SANDSTONE.getDefaultState(), LiquidConfigGen.liquid.sandstoneSolidifyTime);
	    
	    liquidRedSandstone = createLiquid("liquidredsandstone", 0xb45e26, 1000);
	    registerFluidBlock(registry, liquidRedSandstone, Material.LAVA, Blocks.RED_SANDSTONE.getDefaultState(), LiquidConfigGen.liquid.sandstoneSolidifyTime);
	    
	    liquidNetherrack = createLiquid("liquidnetherrack", 0x9e5d5d, 1000);
	    registerFluidBlock(registry, liquidNetherrack, Material.LAVA, Blocks.NETHERRACK.getDefaultState(), LiquidConfigGen.liquid.netherSolidifyTime);

	    //Sand
	    liquidSand = createLiquid("liquidsand", 0xe6d9ae);
	    registerFluidBlock(registry, liquidSand, Material.WATER, Blocks.SAND.getDefaultState(), LiquidConfigGen.liquid.sandSolidifyTime);
	    
	    liquidRedsand = createLiquid("liquidredsand", 0xb45e26);
	    registerFluidBlock(registry, liquidRedsand, Material.WATER, Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND), LiquidConfigGen.liquid.sandSolidifyTime);

	    liquidSoulsand = createLiquid("liquidsoulsand", 0x695243, 2800);
	    registerFluidBlock(registry, liquidSoulsand, Material.LAVA, Blocks.SOUL_SAND.getDefaultState(), LiquidConfigGen.liquid.netherSolidifyTime);

	    //Ore?????
	    liquidOre = createLiquid("liquidore", 0x7f7f7f, 2000);
	    registerFluidOreBlock(registry, liquidOre, Material.LAVA, LiquidConfigGen.liquid.stoneSolidifyTime);
	    
	    //Clay
	    liquidClay = createLiquid("liquidclay", 0xa9afbb);
	    registerFluidBlock(registry, liquidClay, Material.WATER, Blocks.CLAY.getDefaultState(), LiquidConfigGen.liquid.claySolidifyTime);
	    
	    liquidTerracotta = createLiquid("liquidterracotta", 0x935940, 1000);
	    registerFluidBlock(registry, liquidTerracotta, Material.LAVA, Blocks.HARDENED_CLAY.getDefaultState(), LiquidConfigGen.liquid.terracottaSolidifyTime);
	    		
	    liquidStainedTerracotta = registerTerracottaLiquid("liquidstainedterracotta", registry); //Block generation is done within this method
	    
	    //Concrete
	    liquidConcrete = registerConcreteLiquid("liquidconcrete", registry); //Block generation is done within this method
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
		
		FluidRegistry.addBucketForFluid(liquidSandstone);
		FluidRegistry.addBucketForFluid(liquidRedSandstone);
		FluidRegistry.addBucketForFluid(liquidNetherrack);
		
		FluidRegistry.addBucketForFluid(liquidSand);
		FluidRegistry.addBucketForFluid(liquidRedsand);
		FluidRegistry.addBucketForFluid(liquidSoulsand);
		
		FluidRegistry.addBucketForFluid(liquidOre);
		
		FluidRegistry.addBucketForFluid(liquidClay);
		FluidRegistry.addBucketForFluid(liquidTerracotta);
		
		for(int i = 0; i < EnumDyeColor.values().length; i++)
	    {
			FluidRegistry.addBucketForFluid(liquidStainedTerracotta[i]);
			FluidRegistry.addBucketForFluid(liquidConcrete[i]);
	    }
    }

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
	    LiquidBlocks.proxy.registerModels();
	}
	
	public static LiquidMolten[] registerTerracottaLiquid(String liquidBaseName, IForgeRegistry<Block> registry)
	{
		LiquidMolten[] liquidTerracottas = new LiquidMolten[EnumDyeColor.values().length];
		
		for(int i = 0; i < EnumDyeColor.values().length; i++)
	    {
			EnumDyeColor color = EnumDyeColor.byMetadata(i);
			liquidTerracottas[i] = createLiquid(liquidBaseName + color.getUnlocalizedName().toLowerCase(), LiquidUtil.getColorForTerracotta(color), 1000);
		    registerFluidBlock(registry, liquidTerracottas[i], Material.LAVA, Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(BlockStainedHardenedClay.COLOR, color), LiquidConfigGen.liquid.terracottaSolidifyTime);
	    }
		
		return liquidTerracottas;
	}
	
	public static LiquidMolten[] registerConcreteLiquid(String liquidBaseName, IForgeRegistry<Block> registry)
	{
		LiquidMolten[] liquidConcretes = new LiquidMolten[EnumDyeColor.values().length];
		
		for(int i = 0; i < EnumDyeColor.values().length; i++)
		{
			EnumDyeColor color = EnumDyeColor.byMetadata(i);
			liquidConcretes[i] = createLiquid(liquidBaseName + color.getUnlocalizedName().toLowerCase(), LiquidUtil.getColorForConcrete(color), 1000);
			registerFluidBlock(registry, liquidConcretes[i], Material.WATER, Blocks.CONCRETE.getDefaultState().withProperty(BlockColored.COLOR, color), LiquidConfigGen.liquid.concreteSolidifyTime);
		}
		
		return liquidConcretes;
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
	
	public static BlockFluidBase registerFluidOreBlock(IForgeRegistry<Block> registry, Fluid fluid, Material material, int solidifyTime) {
	    return registerBlock(registry, new BlockLiquidOre(fluid, material, solidifyTime), fluid.getName());
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
