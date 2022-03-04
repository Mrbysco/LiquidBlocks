package com.mrbysco.liquidblocks.blocks;

import com.mrbysco.liquidblocks.config.LiquidConfig;
import com.mrbysco.liquidblocks.init.LiquidTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.Iterator;
import java.util.function.Supplier;

public class LiquidOreBlock extends LiquidBlockBlock {

	public LiquidOreBlock(Block.Properties properties, Supplier<? extends FlowingFluid> supplier, Supplier<Block> blockSupplier) {
		super(properties, supplier, blockSupplier);
	}

	@Override
	public void convertBlock(Level level, BlockPos pos) {
		level.removeBlockEntity(pos);
		level.removeBlock(pos, false);
		level.setBlockAndUpdate(pos, getRandomOre(level));
	}

	public BlockState getRandomOre(Level level) {
		int oreChance = LiquidConfig.COMMON.oreChance.get();
		int randNumber = level.random.nextInt(oreChance);

		if (randNumber == 0) {
			boolean isEmpty = !Registry.BLOCK.getTag(LiquidTags.ORES).map(HolderSet.Named::iterator).map(Iterator::hasNext).orElse(false);
			if (!isEmpty) {
				Block oreState = Blocks.STONE;
				oreState = Registry.BLOCK.getTag(LiquidTags.ORES).flatMap((blockNamed) -> {
					return blockNamed.getRandomElement(level.random);
				}).map((blockHolder) -> {
					return blockHolder.value();
				}).orElse(oreState);

				return oreState.defaultBlockState();
			} else {
				return Blocks.STONE.defaultBlockState();
			}
		} else {
			return Blocks.STONE.defaultBlockState();
		}
	}
}
