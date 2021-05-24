package com.mrbysco.liquidblocks.blocks;

import com.mrbysco.liquidblocks.config.LiquidConfig;
import com.mrbysco.liquidblocks.tile.TileLiquidBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class LiquidBlockBlock extends FlowingFluidBlock {
	private final Supplier<Block> blockSupplier;

	public LiquidBlockBlock(Block.Properties properties, Supplier<? extends FlowingFluid> supplier, Supplier<Block> blockSupplier) {
		super(supplier, properties);
		this.blockSupplier = blockSupplier;
	}

	public void convertBlock(World world, BlockPos pos) {
		world.removeTileEntity(pos);
		world.setBlockState(pos, blockSupplier.get().getDefaultState());
		int fireChance = LiquidConfig.COMMON.netherrackFireChance.get();
		if(fireChance > 0) {
			if(blockSupplier.get() == Blocks.NETHERRACK) {
				if(!world.getBlockState(pos.up()).isSolid() || world.getBlockState(pos.up()).getMaterial().isReplaceable()) {
					if(world.rand.nextInt(fireChance) <= 1) {
						world.setBlockState(pos.up(), Blocks.FIRE.getDefaultState().with(FireBlock.AGE, world.rand.nextInt(15)));
					}
				}
			}
		}
	}

	public Supplier<Block> getLiquifiedBlock() {
		return blockSupplier;
	}

	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		super.onEntityCollision(state, worldIn, pos, entityIn);
		if (entityIn instanceof LivingEntity) {
			LivingEntity entity = (LivingEntity) entityIn;
			if (state.getMaterial() == Material.WATER) {
				if(LiquidConfig.COMMON.liquidCausesNausea.get()) {
					entity.addPotionEffect(new EffectInstance(Effects.NAUSEA, 200, 1, false, false));
				}
				if(LiquidConfig.COMMON.liquidCausesSlowness.get()) {
					entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 200, 1, false, false));
				}
			}
		}
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileLiquidBlock();
	}
}
