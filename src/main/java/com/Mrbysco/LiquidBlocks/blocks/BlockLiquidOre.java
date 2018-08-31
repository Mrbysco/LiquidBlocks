package com.Mrbysco.LiquidBlocks.blocks;

import com.Mrbysco.LiquidBlocks.LiquidBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class BlockLiquidOre extends BlockLiquidBlock{

	public BlockLiquidOre(Fluid fluid, Material material, int solidTime) {
		super(fluid, material, Blocks.STONE.getDefaultState(), solidTime);
		
		this.setCreativeTab(LiquidBlocks.liquidTab);
	}

	public void convertBlock(World world, BlockPos pos)
    {
        world.removeTileEntity(pos);
        world.setBlockState(pos, getRandomOre(world));
    }
	
	public IBlockState getRandomOre(World worldIn)
	{
		int randNumber = worldIn.rand.nextInt(48);
		
		if(randNumber > 6)
		{
			return Blocks.STONE.getDefaultState();
		}
		else if(randNumber == 6)
		{
			return Blocks.EMERALD_ORE.getDefaultState();
		}
		else if(randNumber == 5)
		{
			return Blocks.REDSTONE_ORE.getDefaultState();
		}
		else if(randNumber == 4)
		{
			return Blocks.LAPIS_ORE.getDefaultState();
		}
		else if(randNumber == 3)
		{
			return Blocks.COAL_ORE.getDefaultState();
		}
		else if(randNumber == 2)
		{
			return Blocks.DIAMOND_ORE.getDefaultState();
		}
		else if(randNumber == 1)
		{
			return Blocks.GOLD_ORE.getDefaultState();
		}
		else
		{
			return Blocks.IRON_ORE.getDefaultState();
		}
	}
}
