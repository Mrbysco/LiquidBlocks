package com.mrbysco.liquidblocks.util;

import net.minecraft.world.item.DyeColor;

public class LiquidUtil {

	public static int getColorForTerracotta(DyeColor color) {
		switch (color) {
			case BLACK:
				return 0x251710;
			case BROWN:
				return 0x251710;
			case BLUE:
				return 0x4d3b5c;
			case CYAN:
				return 0x55595a;
			case GRAY:
				return 0x392921;
			case GREEN:
				return 0x4b5229;
			case LIGHT_BLUE:
				return 0x726e8b;
			case LIME:
				return 0x6c7a39;
			case MAGENTA:
				return 0x9c5c70;
			case ORANGE:
				return 0x9f5224;
			case PINK:
				return 0xa04b4a;
			case PURPLE:
				return 0x764656;
			case RED:
				return 0x943d32;
			case LIGHT_GRAY:
				return 0x866a61;
			case WHITE:
				return 0xd2b1a1;
			case YELLOW:
				return 0xb88322;
			default:
				return 0x935940;
			}
	}
	
	public static int getColorForConcrete(DyeColor color)
	{
		switch (color) {
		case BLACK:
			return 0x07090e;
		case BROWN:
			return 0x603b1f;
		case BLUE:
			return 0x2d2f90;
		case CYAN:
			return 0x157687;
		case GRAY:
			return 0x373a3e;
		case GREEN:
			return 0x4a5c25;
		case LIGHT_BLUE:
			return 0x2387c5;
		case LIME:
			return 0x5da818;
		case MAGENTA:
			return 0xa9309f;
		case ORANGE:
			return 0xe16202;
		case PINK:
			return 0xd66690;
		case PURPLE:
			return 0x641f9c;
		case RED:
			return 0x8e2020;
		case LIGHT_GRAY:
			return 0x7d7d73;
		case WHITE:
			return 0xcfd5d6;
		case YELLOW:
			return 0xf1af15;
		default:
			return 0xe2e0ff;
		}
	}
}
