package com.Mrbysco.LiquidBlocks.blocks;

import javax.annotation.Nonnull;

import com.Mrbysco.LiquidBlocks.LiquidBlocks;
import com.Mrbysco.LiquidBlocks.config.LiquidConfigGen;
import com.Mrbysco.LiquidBlocks.init.LiquidRegistry;
import com.Mrbysco.LiquidBlocks.tile.TileLiquidBlock;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class BlockLiquidBlock extends BlockFluidClassic implements ITileEntityProvider{

	private final IBlockState block;
	private final int solidTime;
	
	public BlockLiquidBlock(Fluid fluid, Material material, IBlockState block, int solidTime) {
		super(fluid, material);
		
		this.block = block;
		this.solidTime = solidTime;
		
		this.setCreativeTab(LiquidBlocks.liquidTab);
	}

	@Nonnull
	@Override
	public String getUnlocalizedName() {
		Fluid fluid = FluidRegistry.getFluid(fluidName);
	    if(fluid != null) {
	    	return fluid.getUnlocalizedName();
	    }
	    return super.getUnlocalizedName();
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}
	
	@Override
	public boolean canDisplace(IBlockAccess world, BlockPos pos) {
		return super.canDisplace(world, pos);
	}
	
	@Override
	public boolean displaceIfPossible(World world, BlockPos pos) {
		return super.displaceIfPossible(world, pos);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		worldIn.removeTileEntity(pos);
	}
	
	public void convertBlock(World world, BlockPos pos)
    {
        world.removeTileEntity(pos);
        world.setBlockState(pos, this.block);
    }
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
		if (entityIn instanceof EntityLivingBase)
        {
            EntityLivingBase entity = (EntityLivingBase) entityIn;
            if(getFluid() == LiquidRegistry.liquidDirt || getFluid() == LiquidRegistry.liquidCoarseDirt || getFluid() == LiquidRegistry.liquidPodzol)
            {
            	if(LiquidConfigGen.liquid.dirtCausesNausea)
                {
                	entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 1));
                }
                if(LiquidConfigGen.liquid.dirtCausesSlowness)
                {
                	entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 1));
                }
            }
        }
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileLiquidBlock(solidTime);
	}
}
