package com.mrbysco.liquidblocks.blockentity;

import com.mrbysco.liquidblocks.blocks.LiquidBlockBlock;
import com.mrbysco.liquidblocks.config.LiquidConfig;
import com.mrbysco.liquidblocks.init.LiquidRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LiquidBlockEntity extends BlockEntity {
	private int solidifyTimer;

	public LiquidBlockEntity(BlockPos pos, BlockState state) {
		super(LiquidRegistry.LIQUID_BLOCK_ENTITY.get(), pos, state);
		this.solidifyTimer = -1;
	}

	@Override
	protected void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
		compound.putInt("TimeLeft", this.solidifyTimer);
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		this.solidifyTimer = compound.getShort("TimeLeft");
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, LiquidBlockEntity blockEntity) {
		if (level.getGameTime() % 20 == 0) {
			if (blockEntity.solidifyTimer == -1) {
				if (state.getBlock() instanceof LiquidBlockBlock liquidBlock) {
					if (liquidBlock.getLiquifiedBlock().get() != null) {
						float hardness = liquidBlock.getLiquifiedBlock().get().defaultBlockState().getDestroySpeed(level, pos);
						if (hardness > 0.0F) {
							int configuredTimer = LiquidConfig.COMMON.solidifyTimer.get();
							blockEntity.solidifyTimer = configuredTimer == -1 ? (int) Math.ceil((double) hardness * 7) : configuredTimer;
						}
					}
				}
			}
			if (blockEntity.solidifyTimer > 0) {
				blockEntity.solidifyTimer--;
				if (!LiquidConfig.COMMON.completelyFill.get()) {
					if (state.getBlock() instanceof LiquidBlockBlock) {
						boolean flag = !(state.getValue(LiquidBlockBlock.LEVEL) == 0);
						if (flag) {
							blockEntity.decrementAgain();
						}

						if (state.getValue(LiquidBlockBlock.LEVEL) > 5) {
							blockEntity.decrementAgain();
						}
					}
				}
			} else if (blockEntity.solidifyTimer == 0) {
				if (state.getBlock() instanceof LiquidBlockBlock liquid) {
					liquid.convertBlock(level, pos);
				}
			}
		}
	}

	public void decrementAgain() {
		solidifyTimer--;
		if (solidifyTimer < 0) {
			solidifyTimer = 0;
		}
	}
}
