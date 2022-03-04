package com.mrbysco.liquidblocks.util;

import net.minecraft.world.item.DyeColor;

public class LiquidUtil {

	public static int getColorForTerracotta(DyeColor color) {
		return switch (color) {
			case BLACK -> 0x251710;
			case BROWN -> 0x251710;
			case BLUE -> 0x4d3b5c;
			case CYAN -> 0x55595a;
			case GRAY -> 0x392921;
			case GREEN -> 0x4b5229;
			case LIGHT_BLUE -> 0x726e8b;
			case LIME -> 0x6c7a39;
			case MAGENTA -> 0x9c5c70;
			case ORANGE -> 0x9f5224;
			case PINK -> 0xa04b4a;
			case PURPLE -> 0x764656;
			case RED -> 0x943d32;
			case LIGHT_GRAY -> 0x866a61;
			case WHITE -> 0xd2b1a1;
			case YELLOW -> 0xb88322;
			default -> 0x935940;
		};
	}

	public static int getColorForConcrete(DyeColor color) {
		return switch (color) {
			case BLACK -> 0x07090e;
			case BROWN -> 0x603b1f;
			case BLUE -> 0x2d2f90;
			case CYAN -> 0x157687;
			case GRAY -> 0x373a3e;
			case GREEN -> 0x4a5c25;
			case LIGHT_BLUE -> 0x2387c5;
			case LIME -> 0x5da818;
			case MAGENTA -> 0xa9309f;
			case ORANGE -> 0xe16202;
			case PINK -> 0xd66690;
			case PURPLE -> 0x641f9c;
			case RED -> 0x8e2020;
			case LIGHT_GRAY -> 0x7d7d73;
			case WHITE -> 0xcfd5d6;
			case YELLOW -> 0xf1af15;
			default -> 0xe2e0ff;
		};
	}
}
