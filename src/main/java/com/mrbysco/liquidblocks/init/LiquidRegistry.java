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
	public static final LiquidBlockReg LIQUID_DIRT = new LiquidBlockReg("liquid_dirt", () -> Blocks.DIRT, Material.WATER, 0xFF392C20);
	public static final LiquidBlockReg LIQUID_COARSE_DIRT = new LiquidBlockReg("liquid_coarse_dirt", () -> Blocks.COARSE_DIRT, Material.WATER, 0xFF392C20);
	public static final LiquidBlockReg LIQUID_PODZOL = new LiquidBlockReg("liquid_podzol", () -> Blocks.PODZOL, Material.WATER, 0xFF442A14);

	//Stones
	public static final LiquidBlockReg LIQUID_STONE = new LiquidBlockReg("liquid_stone", () -> Blocks.STONE, Material.LAVA, 0xFF7f7f7f);
	public static final LiquidBlockReg LIQUID_GRANITE = new LiquidBlockReg("liquid_granite", () -> Blocks.GRANITE, Material.LAVA, 0xFFb48877);
	public static final LiquidBlockReg LIQUID_DIORITE = new LiquidBlockReg("liquid_diorite", () -> Blocks.DIORITE, Material.LAVA, 0xFFe0e0e3);
	public static final LiquidBlockReg LIQUID_ANDESITE = new LiquidBlockReg("liquid_andesite", () -> Blocks.ANDESITE, Material.LAVA, 0xFFababac);
	public static final LiquidBlockReg LIQUID_SANDSTONE = new LiquidBlockReg("liquid_sandstone", () -> Blocks.SANDSTONE, Material.LAVA, 0xFFe6d9ae);
	public static final LiquidBlockReg LIQUID_RED_SANDSTONE = new LiquidBlockReg("liquid_red_sandstone", () -> Blocks.RED_SANDSTONE, Material.LAVA, 0xFFb45e26);

	//Nether stuff
	public static final LiquidBlockReg LIQUID_NETHERRACK = new LiquidBlockReg("liquid_netherrack", () -> Blocks.NETHERRACK, Material.LAVA, 0xFF9e5d5d);
	public static final LiquidBlockReg LIQUID_SOUL_SAND = new LiquidBlockReg("liquid_soul_sand", () -> Blocks.SOUL_SAND, Material.LAVA, 0xFF695243);
	public static final LiquidBlockReg LIQUID_MAGMA = new LiquidBlockReg("liquid_magma", () -> Blocks.MAGMA_BLOCK, Material.LAVA, 0xFFca4e06, 12);
	public static final LiquidBlockReg LIQUID_GLOWSTONE = new LiquidBlockReg("liquid_glowstone", () -> Blocks.GLOWSTONE, Material.LAVA, 0xFFf9d49c, 12);

	//Sand
	public static final LiquidBlockReg LIQUID_SAND = new LiquidBlockReg("liquid_sand", () -> Blocks.SAND, Material.WATER, 0xFFe6d9ae);
	public static final LiquidBlockReg LIQUID_RED_SAND = new LiquidBlockReg("liquid_red_sand", () -> Blocks.RED_SAND, Material.WATER, 0xFFb45e26);
	public static final LiquidBlockReg LIQUID_GRAVEL = new LiquidBlockReg("liquid_gravel", () -> Blocks.GRAVEL, Material.WATER, 0xFF817f7f);

	public static final LiquidBlockReg LIQUID_ORE = new LiquidBlockReg("liquid_ore", () -> Blocks.STONE, Material.LAVA, 0xFF7f7f7f);

	public static final LiquidBlockReg LIQUID_CLAY = new LiquidBlockReg("liquid_clay", () -> Blocks.CLAY, Material.WATER, 0xa9afbb);
	public static final LiquidBlockReg LIQUID_TERRACOTTA = new LiquidBlockReg("liquid_terracotta", () -> Blocks.TERRACOTTA, Material.LAVA, 0x935940);

	public static final LiquidBlockReg LIQUID_WHITE_GLAZED_TERRACOTTA = new LiquidBlockReg("liquid_white_glazed_terracotta", () -> Blocks.WHITE_GLAZED_TERRACOTTA, Material.LAVA, 16777215);
	public static final LiquidBlockReg LIQUID_ORANGE_GLAZED_TERRACOTTA = new LiquidBlockReg("liquid_orange_glazed_terracotta", () -> Blocks.ORANGE_GLAZED_TERRACOTTA, Material.LAVA, 14188339);
	public static final LiquidBlockReg LIQUID_MAGENTA_GLAZED_TERRACOTTA = new LiquidBlockReg("liquid_magenta_glazed_terracotta", () -> Blocks.MAGENTA_GLAZED_TERRACOTTA, Material.LAVA, 11685080);
	public static final LiquidBlockReg LIQUID_LIGHT_BLUE_GLAZED_TERRACOTTA = new LiquidBlockReg("liquid_light_blue_glazed_terracotta", () -> Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, Material.LAVA, 6724056);
	public static final LiquidBlockReg LIQUID_YELLOW_GLAZED_TERRACOTTA = new LiquidBlockReg("liquid_yellow_glazed_terracotta", () -> Blocks.YELLOW_GLAZED_TERRACOTTA, Material.LAVA, 15066419);
	public static final LiquidBlockReg LIQUID_LIME_GLAZED_TERRACOTTA = new LiquidBlockReg("liquid_lime_glazed_terracotta", () -> Blocks.LIME_GLAZED_TERRACOTTA, Material.LAVA, 8375321);
	public static final LiquidBlockReg LIQUID_PINK_GLAZED_TERRACOTTA = new LiquidBlockReg("liquid_pink_glazed_terracotta", () -> Blocks.PINK_GLAZED_TERRACOTTA, Material.LAVA, 15892389);
	public static final LiquidBlockReg LIQUID_GRAY_GLAZED_TERRACOTTA = new LiquidBlockReg("liquid_gray_glazed_terracotta", () -> Blocks.GRAY_GLAZED_TERRACOTTA, Material.LAVA, 5000268);
	public static final LiquidBlockReg LIQUID_LIGHT_GRAY_GLAZED_TERRACOTTA = new LiquidBlockReg("liquid_light_gray_glazed_terracotta", () -> Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA, Material.LAVA, 10066329);
	public static final LiquidBlockReg LIQUID_CYAN_GLAZED_TERRACOTTA = new LiquidBlockReg("liquid_cyan_glazed_terracotta", () -> Blocks.CYAN_GLAZED_TERRACOTTA, Material.LAVA, 5013401);
	public static final LiquidBlockReg LIQUID_PURPLE_GLAZED_TERRACOTTA = new LiquidBlockReg("liquid_purple_glazed_terracotta", () -> Blocks.PURPLE_GLAZED_TERRACOTTA, Material.LAVA, 8339378);
	public static final LiquidBlockReg LIQUID_BLUE_GLAZED_TERRACOTTA = new LiquidBlockReg("liquid_blue_glazed_terracotta", () -> Blocks.BLUE_GLAZED_TERRACOTTA, Material.LAVA, 3361970);
	public static final LiquidBlockReg LIQUID_BROWN_GLAZED_TERRACOTTA = new LiquidBlockReg("liquid_brown_glazed_terracotta", () -> Blocks.BROWN_GLAZED_TERRACOTTA, Material.LAVA, 6704179);
	public static final LiquidBlockReg LIQUID_GREEN_GLAZED_TERRACOTTA = new LiquidBlockReg("liquid_green_glazed_terracotta", () -> Blocks.GREEN_GLAZED_TERRACOTTA, Material.LAVA, 6717235);
	public static final LiquidBlockReg LIQUID_RED_GLAZED_TERRACOTTA = new LiquidBlockReg("liquid_red_glazed_terracotta", () -> Blocks.RED_GLAZED_TERRACOTTA, Material.LAVA, 10040115);
	public static final LiquidBlockReg LIQUID_BLACK_GLAZED_TERRACOTTA = new LiquidBlockReg("liquid_black_glazed_terracotta", () -> Blocks.BLACK_GLAZED_TERRACOTTA, Material.LAVA, 1644825);

	public static final LiquidBlockReg LIQUID_WHITE_CONCRETE = new LiquidBlockReg("liquid_white_concrete", () -> Blocks.WHITE_CONCRETE, Material.WATER, 16777215);
	public static final LiquidBlockReg LIQUID_ORANGE_CONCRETE = new LiquidBlockReg("liquid_orange_concrete", () -> Blocks.ORANGE_CONCRETE, Material.WATER, 14188339);
	public static final LiquidBlockReg LIQUID_MAGENTA_CONCRETE = new LiquidBlockReg("liquid_magenta_concrete", () -> Blocks.MAGENTA_CONCRETE, Material.WATER, 11685080);
	public static final LiquidBlockReg LIQUID_LIGHT_BLUE_CONCRETE = new LiquidBlockReg("liquid_light_blue_concrete", () -> Blocks.LIGHT_BLUE_CONCRETE, Material.WATER, 6724056);
	public static final LiquidBlockReg LIQUID_YELLOW_CONCRETE = new LiquidBlockReg("liquid_yellow_concrete", () -> Blocks.YELLOW_CONCRETE, Material.WATER, 15066419);
	public static final LiquidBlockReg LIQUID_LIME_CONCRETE = new LiquidBlockReg("liquid_lime_concrete", () -> Blocks.LIME_CONCRETE, Material.WATER, 8375321);
	public static final LiquidBlockReg LIQUID_PINK_CONCRETE = new LiquidBlockReg("liquid_pink_concrete", () -> Blocks.PINK_CONCRETE, Material.WATER, 15892389);
	public static final LiquidBlockReg LIQUID_GRAY_CONCRETE = new LiquidBlockReg("liquid_gray_concrete", () -> Blocks.GRAY_CONCRETE, Material.WATER, 5000268);
	public static final LiquidBlockReg LIQUID_LIGHT_GRAY_CONCRETE = new LiquidBlockReg("liquid_light_gray_concrete", () -> Blocks.LIGHT_GRAY_CONCRETE, Material.WATER, 10066329);
	public static final LiquidBlockReg LIQUID_CYAN_CONCRETE = new LiquidBlockReg("liquid_cyan_concrete", () -> Blocks.CYAN_CONCRETE, Material.WATER, 5013401);
	public static final LiquidBlockReg LIQUID_PURPLE_CONCRETE = new LiquidBlockReg("liquid_purple_concrete", () -> Blocks.PURPLE_CONCRETE, Material.WATER, 8339378);
	public static final LiquidBlockReg LIQUID_BLUE_CONCRETE = new LiquidBlockReg("liquid_blue_concrete", () -> Blocks.BLUE_CONCRETE, Material.WATER, 3361970);
	public static final LiquidBlockReg LIQUID_BROWN_CONCRETE = new LiquidBlockReg("liquid_brown_concrete", () -> Blocks.BROWN_CONCRETE, Material.WATER, 6704179);
	public static final LiquidBlockReg LIQUID_GREEN_CONCRETE = new LiquidBlockReg("liquid_green_concrete", () -> Blocks.GREEN_CONCRETE, Material.WATER, 6717235);
	public static final LiquidBlockReg LIQUID_RED_CONCRETE = new LiquidBlockReg("liquid_red_concrete", () -> Blocks.RED_CONCRETE, Material.WATER, 10040115);
	public static final LiquidBlockReg LIQUID_BLACK_CONCRETE = new LiquidBlockReg("liquid_black_concrete", () -> Blocks.BLACK_CONCRETE, Material.WATER, 1644825);

	public static final RegistryObject<BlockEntityType<LiquidBlockEntity>> LIQUID_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("liquid_tile",
			() -> BlockEntityType.Builder.of(LiquidBlockEntity::new,
					LIQUID_DIRT.getFluidblock(), LIQUID_COARSE_DIRT.getFluidblock(), LIQUID_PODZOL.getFluidblock(),
					LIQUID_STONE.getFluidblock(), LIQUID_GRANITE.getFluidblock(), LIQUID_DIORITE.getFluidblock(),
					LIQUID_ANDESITE.getFluidblock(), LIQUID_SANDSTONE.getFluidblock(), LIQUID_RED_SANDSTONE.getFluidblock(),
					LIQUID_NETHERRACK.getFluidblock(), LIQUID_SOUL_SAND.getFluidblock(), LIQUID_MAGMA.getFluidblock(),
					LIQUID_GLOWSTONE.getFluidblock(), LIQUID_SAND.getFluidblock(), LIQUID_RED_SAND.getFluidblock(),
					LIQUID_GRAVEL.getFluidblock(), LIQUID_ORE.getFluidblock(), LIQUID_CLAY.getFluidblock(),
					LIQUID_TERRACOTTA.getFluidblock(), LIQUID_WHITE_GLAZED_TERRACOTTA.getFluidblock(),
					LIQUID_ORANGE_GLAZED_TERRACOTTA.getFluidblock(), LIQUID_MAGENTA_GLAZED_TERRACOTTA.getFluidblock(),
					LIQUID_LIGHT_BLUE_GLAZED_TERRACOTTA.getFluidblock(), LIQUID_YELLOW_GLAZED_TERRACOTTA.getFluidblock(),
					LIQUID_LIME_GLAZED_TERRACOTTA.getFluidblock(), LIQUID_PINK_GLAZED_TERRACOTTA.getFluidblock(),
					LIQUID_GRAY_GLAZED_TERRACOTTA.getFluidblock(), LIQUID_LIGHT_GRAY_GLAZED_TERRACOTTA.getFluidblock(),
					LIQUID_CYAN_GLAZED_TERRACOTTA.getFluidblock(), LIQUID_PURPLE_GLAZED_TERRACOTTA.getFluidblock(),
					LIQUID_BLUE_GLAZED_TERRACOTTA.getFluidblock(), LIQUID_BROWN_GLAZED_TERRACOTTA.getFluidblock(),
					LIQUID_GREEN_GLAZED_TERRACOTTA.getFluidblock(), LIQUID_RED_GLAZED_TERRACOTTA.getFluidblock(),
					LIQUID_BLACK_GLAZED_TERRACOTTA.getFluidblock(), LIQUID_WHITE_CONCRETE.getFluidblock(),
					LIQUID_ORANGE_CONCRETE.getFluidblock(), LIQUID_MAGENTA_CONCRETE.getFluidblock(),
					LIQUID_LIGHT_BLUE_CONCRETE.getFluidblock(), LIQUID_YELLOW_CONCRETE.getFluidblock(),
					LIQUID_LIME_CONCRETE.getFluidblock(), LIQUID_PINK_CONCRETE.getFluidblock(),
					LIQUID_GRAY_CONCRETE.getFluidblock(), LIQUID_LIGHT_GRAY_CONCRETE.getFluidblock(),
					LIQUID_CYAN_CONCRETE.getFluidblock(), LIQUID_PURPLE_CONCRETE.getFluidblock(),
					LIQUID_BLUE_CONCRETE.getFluidblock(), LIQUID_BROWN_CONCRETE.getFluidblock(),
					LIQUID_GREEN_CONCRETE.getFluidblock(), LIQUID_RED_CONCRETE.getFluidblock(),
					LIQUID_BLACK_CONCRETE.getFluidblock()).build(null));
}
