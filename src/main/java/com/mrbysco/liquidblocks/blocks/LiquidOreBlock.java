package com.mrbysco.liquidblocks.blocks;

import com.mrbysco.liquidblocks.config.LiquidConfig;
import com.mrbysco.liquidblocks.init.LiquidTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

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

		if(randNumber == 0) {
			if(!LiquidTags.ORES.getValues().isEmpty()) {
				Block oreBlock = LiquidTags.ORES.getRandomElement(level.random);
				return oreBlock.defaultBlockState();
			} else {
				return Blocks.STONE.defaultBlockState();
			}
		} else {
			return Blocks.STONE.defaultBlockState();
		}
	}
}
