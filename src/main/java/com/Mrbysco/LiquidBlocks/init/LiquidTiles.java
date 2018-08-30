package com.Mrbysco.LiquidBlocks.init;

import com.Mrbysco.LiquidBlocks.LiquidReference;
import com.Mrbysco.LiquidBlocks.tile.TileLiquidBlock;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class LiquidTiles {
	
	public static void register() { 
		registerTileEntity(TileLiquidBlock.class, "liquid_tile");
	}
	
	public static void registerTileEntity(Class<? extends TileEntity> tileentityClass, String tilename) {
		GameRegistry.registerTileEntity(tileentityClass, new ResourceLocation(LiquidReference.MOD_ID, tilename));
	}
}
