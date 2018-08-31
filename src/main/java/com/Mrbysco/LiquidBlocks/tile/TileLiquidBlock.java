package com.Mrbysco.LiquidBlocks.tile;

import com.Mrbysco.LiquidBlocks.blocks.BlockLiquidBlock;
import com.Mrbysco.LiquidBlocks.config.LiquidConfigGen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.BlockFluidBase;

public class TileLiquidBlock extends TileEntity implements ITickable{

	private int solidifyTimer;
	
	public TileLiquidBlock(int solidifyTime) {
		this.solidifyTimer = solidifyTime;
	}
	
	public TileLiquidBlock() {
		this.solidifyTimer = 200;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
    	compound.setInteger("TimeLeft", this.solidifyTimer);
    	return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
				
        this.solidifyTimer = compound.getShort("TimeLeft");
	}
	
	@Override
	public void update() {
    	if(!world.isRemote)
    	{
    		if (solidifyTimer > 0)
            {
    			solidifyTimer--;
            	if(!LiquidConfigGen.liquid.completelyFill)
            	{
            		IBlockState state = this.world.getBlockState(this.getPos());
        			if(state.getBlock() instanceof BlockLiquidBlock)
        			{
                		BlockLiquidBlock liquid = (BlockLiquidBlock) state.getBlock();
                		boolean flag = !liquid.isSourceBlock(this.world, this.pos);
                		if(flag)
        				{
                			decrementAgain();
        				}

                		if(state.getValue(BlockFluidBase.LEVEL) > 5)
                		{
                			decrementAgain();
                		}
        			}
            	}
            }
            else if (solidifyTimer == 0)
            {
            	IBlockState state = this.world.getBlockState(this.getPos());
            	if(state.getBlock() instanceof BlockLiquidBlock)
            	{
            		BlockLiquidBlock liquid = (BlockLiquidBlock) state.getBlock();
                	liquid.convertBlock(world, pos);
            	}
            }
    	}
	}
	
	public void decrementAgain() {
		solidifyTimer--;
		if(solidifyTimer < 0)
		{
			solidifyTimer = 0;
		}
	}

}
