package com.mrbysco.liquidblocks.tile;

import com.mrbysco.liquidblocks.blocks.LiquidBlockBlock;
import com.mrbysco.liquidblocks.config.LiquidConfig;
import com.mrbysco.liquidblocks.init.LiquidRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileLiquidBlock extends TileEntity implements ITickableTileEntity {

	private int solidifyTimer;

	public TileLiquidBlock() {
		super(LiquidRegistry.LIQUID_TILE.get());
		this.solidifyTimer = -1;
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		compound.putInt("TimeLeft", this.solidifyTimer);

		return compound;
	}

	@Override
	public void read(BlockState state, CompoundNBT compound) {
		super.read(state, compound);
		this.solidifyTimer = compound.getShort("TimeLeft");
	}

	@Override
	public void tick() {
    	if(!world.isRemote && world.getGameTime() % 20 == 0) {
			BlockState state = getBlockState();
    		if(solidifyTimer == -1) {
				if(state.getBlock() instanceof LiquidBlockBlock) {
					LiquidBlockBlock liquidBlock = (LiquidBlockBlock)state.getBlock();
					if(liquidBlock.getLiquifiedBlock().get() != null) {
						float hardness = liquidBlock.getLiquifiedBlock().get().getDefaultState().getBlockHardness(world, pos);
						if(hardness > 0.0F) {
							this.solidifyTimer = (int)Math.ceil((double)hardness * 7);
						}
					}
				}
			}
    		if (solidifyTimer > 0) {
    			solidifyTimer--;
            	if(!LiquidConfig.COMMON.completelyFill.get()) {
					if(state.getBlock() instanceof LiquidBlockBlock) {
						boolean flag = !(state.get(LiquidBlockBlock.LEVEL) == 0);
						if(flag) {
							decrementAgain();
						}

						if(state.get(LiquidBlockBlock.LEVEL) > 5) {
							decrementAgain();
						}
					}
            	}
            } else if (solidifyTimer == 0) {
            	if(state.getBlock() instanceof LiquidBlockBlock) {
            		LiquidBlockBlock liquid = (LiquidBlockBlock) state.getBlock();
                	liquid.convertBlock(world, pos);
            	}
            }
    	}
	}

	public void decrementAgain() {
		solidifyTimer--;
		if(solidifyTimer < 0) {
			solidifyTimer = 0;
		}
	}
}
