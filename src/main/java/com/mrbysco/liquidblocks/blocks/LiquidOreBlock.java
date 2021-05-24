package com.mrbysco.liquidblocks.blocks;

import com.mrbysco.liquidblocks.config.LiquidConfig;
import com.mrbysco.liquidblocks.init.LiquidTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class LiquidOreBlock extends LiquidBlockBlock {

	public LiquidOreBlock(Block.Properties properties, Supplier<? extends FlowingFluid> supplier, Supplier<Block> blockSupplier) {
		super(properties, supplier, blockSupplier);
	}

	@Override
	public void convertBlock(World world, BlockPos pos) {
		world.removeTileEntity(pos);
		world.removeBlock(pos, false);
        world.setBlockState(pos, getRandomOre(world));
    }

	public BlockState getRandomOre(World worldIn) {
		int oreChance = LiquidConfig.COMMON.oreChance.get();
		int randNumber = worldIn.rand.nextInt(oreChance);

		if(randNumber == 0) {
			if(!LiquidTags.ORES.getAllElements().isEmpty()) {
				Block oreBlock = LiquidTags.ORES.getRandomElement(worldIn.rand);
				return oreBlock.getDefaultState();
			} else {
				return Blocks.STONE.getDefaultState();
			}
		} else {
			return Blocks.STONE.getDefaultState();
		}
	}
}
