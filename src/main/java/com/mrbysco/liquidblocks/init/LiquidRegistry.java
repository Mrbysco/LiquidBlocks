package com.mrbysco.liquidblocks.init;

import com.mrbysco.liquidblocks.LiquidBlocks;
import com.mrbysco.liquidblocks.blockentity.LiquidBlockEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LiquidRegistry {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LiquidBlocks.MOD_ID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LiquidBlocks.MOD_ID);
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, LiquidBlocks.MOD_ID);
	public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, LiquidBlocks.MOD_ID);
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, LiquidBlocks.MOD_ID);

	//Dirts
	public static LiquidBlockReg LIQUID_DIRT = new LiquidBlockReg("liquid_dirt", () -> Blocks.DIRT, Material.WATER, 0xFF392C20);
	public static LiquidBlockReg LIQUID_COARSE_DIRT = new LiquidBlockReg("liquid_coarse_dirt", () -> Blocks.COARSE_DIRT, Material.WATER, 0xFF392C20);
	public static LiquidBlockReg LIQUID_PODZOL = new LiquidBlockReg("liquid_podzol", () -> Blocks.PODZOL, Material.WATER, 0xFF442A14);

	//Stones
	public static LiquidBlockReg LIQUID_STONE = new LiquidBlockReg("liquid_stone", () -> Blocks.STONE, Material.LAVA, 0xFF7f7f7f);
	public static LiquidBlockReg LIQUID_GRANITE = new LiquidBlockReg("liquid_granite", () -> Blocks.GRANITE, Material.LAVA, 0xFFb48877);
	public static LiquidBlockReg LIQUID_DIORITE = new LiquidBlockReg("liquid_diorite", () -> Blocks.DIORITE, Material.LAVA, 0xFFe0e0e3);
	public static LiquidBlockReg LIQUID_ANDESITE = new LiquidBlockReg("liquid_andesite", () -> Blocks.ANDESITE, Material.LAVA, 0xFFababac);
	public static LiquidBlockReg LIQUID_SANDSTONE = new LiquidBlockReg("liquid_sandstone", () -> Blocks.SANDSTONE, Material.LAVA, 0xFFe6d9ae);
	public static LiquidBlockReg LIQUID_RED_SANDSTONE = new LiquidBlockReg("liquid_red_sandstone", () -> Blocks.RED_SANDSTONE, Material.LAVA, 0xFFb45e26);

	//Nether stuff
	public static LiquidBlockReg LIQUID_NETHERRACK = new LiquidBlockReg("liquid_netherrack", () -> Blocks.NETHERRACK, Material.LAVA, 0xFF9e5d5d);
	public static LiquidBlockReg LIQUID_SOUL_SAND = new LiquidBlockReg("liquid_soul_sand", () -> Blocks.SOUL_SAND, Material.LAVA, 0xFF695243);
	public static LiquidBlockReg LIQUID_MAGMA = new LiquidBlockReg("liquid_magma", () -> Blocks.MAGMA_BLOCK, Material.LAVA, 0xFFca4e06, 12);
	public static LiquidBlockReg LIQUID_GLOWSTONE = new LiquidBlockReg("liquid_glowstone", () -> Blocks.GLOWSTONE, Material.LAVA, 0xFFf9d49c, 12);

	//Sand
	public static LiquidBlockReg LIQUID_SAND = new LiquidBlockReg("liquid_sand", () -> Blocks.SAND, Material.WATER, 0xFFe6d9ae);
	public static LiquidBlockReg LIQUID_RED_SAND = new LiquidBlockReg("liquid_red_sand", () -> Blocks.RED_SAND, Material.WATER, 0xFFb45e26);
	public static LiquidBlockReg LIQUID_GRAVEL = new LiquidBlockReg("liquid_gravel", () -> Blocks.GRAVEL, Material.WATER, 0xFF817f7f);

	public static LiquidBlockReg LIQUID_ORE = new LiquidBlockReg("liquid_ore", () -> Blocks.STONE, Material.LAVA, 0xFF7f7f7f);

	public static final RegistryObject<BlockEntityType<LiquidBlockEntity>> LIQUID_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("liquid_tile", () -> BlockEntityType.Builder.of(LiquidBlockEntity::new,
			LIQUID_DIRT.getFluidblock(), LIQUID_COARSE_DIRT.getFluidblock(), LIQUID_PODZOL.getFluidblock(),
			LIQUID_STONE.getFluidblock(), LIQUID_GRANITE.getFluidblock(), LIQUID_DIORITE.getFluidblock(),
			LIQUID_ANDESITE.getFluidblock(), LIQUID_SANDSTONE.getFluidblock(), LIQUID_RED_SANDSTONE.getFluidblock(),
			LIQUID_NETHERRACK.getFluidblock(), LIQUID_SOUL_SAND.getFluidblock(), LIQUID_MAGMA.getFluidblock(),
			LIQUID_GLOWSTONE.getFluidblock(), LIQUID_SAND.getFluidblock(), LIQUID_RED_SAND.getFluidblock(),
			LIQUID_GRAVEL.getFluidblock(), LIQUID_ORE.getFluidblock()).build(null));

//	public static LiquidMolten liquidClay, liquidTerracotta;
//
//	public static LiquidMolten[] liquidStainedTerracotta, liquidConcrete;
//
//	@SubscribeEvent
//	public static void registerBlocks(RegistryEvent.Register<Block> event)
//	{
//	    //Clay
//	    liquidClay = createLiquid("liquidclay", 0xa9afbb);
//	    registerFluidBlock(registry, liquidClay, Material.WATER, Blocks.CLAY.getDefaultState(), LiquidConfigGen.liquid.claySolidifyTime);
//
//	    liquidTerracotta = createLiquid("liquidterracotta", 0x935940);
//	    registerFluidBlock(registry, liquidTerracotta, Material.LAVA, Blocks.HARDENED_CLAY.getDefaultState(), LiquidConfigGen.liquid.terracottaSolidifyTime);
//
//	    liquidStainedTerracotta = registerTerracottaLiquid("liquidstainedterracotta", registry); //Block generation is done within this method
//
//	    //Concrete
//	    liquidConcrete = registerConcreteLiquid("liquidconcrete", registry); //Block generation is done within this method
//	}
}
