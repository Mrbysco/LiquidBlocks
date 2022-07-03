package com.mrbysco.liquidblocks.blocks;

import com.mrbysco.liquidblocks.config.LiquidConfig;
import com.mrbysco.liquidblocks.init.LiquidRegistry;
import com.mrbysco.liquidblocks.blockentity.LiquidBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class LiquidBlockBlock extends LiquidBlock implements EntityBlock {
	private final Supplier<Block> blockSupplier;

	public LiquidBlockBlock(Block.Properties properties, Supplier<? extends FlowingFluid> supplier, Supplier<Block> blockSupplier) {
		super(supplier, properties);
		this.blockSupplier = blockSupplier;
	}

	public void convertBlock(Level level, BlockPos pos) {
		level.removeBlockEntity(pos);
		level.setBlockAndUpdate(pos, blockSupplier.get().defaultBlockState());
		int fireChance = LiquidConfig.COMMON.netherrackFireChance.get();
		if (fireChance > 0) {
			if (blockSupplier.get() == Blocks.NETHERRACK) {
				if (!level.getBlockState(pos.above()).canOcclude() || level.getBlockState(pos.above()).getMaterial().isReplaceable()) {
					if (level.random.nextInt(fireChance) <= 1) {
						level.setBlockAndUpdate(pos.above(), Blocks.FIRE.defaultBlockState().setValue(FireBlock.AGE, level.random.nextInt(15)));
					}
				}
			}
		}
	}

	public Supplier<Block> getLiquifiedBlock() {
		return blockSupplier;
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entityIn) {
		super.entityInside(state, level, pos, entityIn);
		if (entityIn instanceof LivingEntity entity) {
			if (state.getMaterial() == Material.WATER) {
				if (LiquidConfig.COMMON.liquidCausesNausea.get()) {
					entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 1, false, false));
				}
				if (LiquidConfig.COMMON.liquidCausesSlowness.get()) {
					entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 1, false, false));
				}
			}
		}
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new LiquidBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		return createLiquidTicker(level, blockEntityType, LiquidRegistry.LIQUID_BLOCK_ENTITY.get());
	}

	@Nullable
	protected static <T extends BlockEntity> BlockEntityTicker<T> createLiquidTicker(Level level, BlockEntityType<T> blockEntityType, BlockEntityType<? extends LiquidBlockEntity> blockEntityType1) {
		return level.isClientSide ? null : createTickerHelper(blockEntityType, blockEntityType1, LiquidBlockEntity::serverTick);
	}

	@Nullable
	protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
		return p_152134_ == p_152133_ ? (BlockEntityTicker<A>) p_152135_ : null;
	}
}
