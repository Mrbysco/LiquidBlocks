package com.Mrbysco.LiquidBlocks.blocks;

import java.util.Random;

import javax.annotation.Nonnull;

import com.Mrbysco.LiquidBlocks.LiquidBlocks;
import com.Mrbysco.LiquidBlocks.config.LiquidConfigGen;
import com.Mrbysco.LiquidBlocks.init.LiquidRegistry;
import com.Mrbysco.LiquidBlocks.tile.TileLiquidBlock;

import net.minecraft.block.BlockFire;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
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
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (!isSourceBlock(world, pos) && ForgeEventFactory.canCreateFluidSource(world, pos, state, false))
        {
            int adjacentSourceBlocks =
                    (isSourceBlock(world, pos.north()) ? 1 : 0) +
                    (isSourceBlock(world, pos.south()) ? 1 : 0) +
                    (isSourceBlock(world, pos.east()) ? 1 : 0) +
                    (isSourceBlock(world, pos.west()) ? 1 : 0);
            if (adjacentSourceBlocks >= 2 && (world.getBlockState(pos.up(densityDir)).getMaterial().isSolid() || isSourceBlock(world, pos.up(densityDir))))
                world.setBlockState(pos, state.withProperty(LEVEL, 0));
        }

        int quantaRemaining = quantaPerBlock - state.getValue(LEVEL);
        int expQuanta = -101;

        // check adjacent block levels if non-source
        if (quantaRemaining < quantaPerBlock)
        {
            if (world.getBlockState(pos.add( 0, -densityDir,  0)).getBlock() == this ||
                world.getBlockState(pos.add(-1, -densityDir,  0)).getBlock() == this ||
                world.getBlockState(pos.add( 1, -densityDir,  0)).getBlock() == this ||
                world.getBlockState(pos.add( 0, -densityDir, -1)).getBlock() == this ||
                world.getBlockState(pos.add( 0, -densityDir,  1)).getBlock() == this)
            {
                expQuanta = quantaPerBlock - 1;
            }
            else
            {
                int maxQuanta = -100;
                maxQuanta = getLargerQuanta(world, pos.add(-1, 0,  0), maxQuanta);
                maxQuanta = getLargerQuanta(world, pos.add( 1, 0,  0), maxQuanta);
                maxQuanta = getLargerQuanta(world, pos.add( 0, 0, -1), maxQuanta);
                maxQuanta = getLargerQuanta(world, pos.add( 0, 0,  1), maxQuanta);

                expQuanta = maxQuanta - 1;
            }

            // decay calculation
            if (expQuanta != quantaRemaining)
            {
                quantaRemaining = expQuanta;

                if (expQuanta <= 0)
                {
                	if(LiquidConfigGen.general.completelyFill)
                	{
                		if (rand.nextInt(5) == 1)
                		{
                        	convertBlock(world, pos);
                		}
                	}
                	else
                	{
                        world.setBlockToAir(pos);
                	}	
                }
                else
                {
                    world.setBlockState(pos, state.withProperty(LEVEL, quantaPerBlock - expQuanta), 2);
                    world.scheduleUpdate(pos, this, tickRate);
                    world.notifyNeighborsOfStateChange(pos, this, false);
                }
            }
        }
        // This is a "source" block, set meta to zero, and send a server only update
        else if (quantaRemaining >= quantaPerBlock)
        {
            world.setBlockState(pos, this.getDefaultState(), 2);
        }

        // Flow vertically if possible
        if (canDisplace(world, pos.up(densityDir)))
        {
            flowIntoBlock(world, pos.up(densityDir), 1);
            return;
        }

        // Flow outward if possible
        int flowMeta = quantaPerBlock - quantaRemaining + 1;
        if (flowMeta >= quantaPerBlock)
        {
            return;
        }

        if (isSourceBlock(world, pos) || !isFlowingVertically(world, pos))
        {
            if (world.getBlockState(pos.down(densityDir)).getBlock() == this)
            {
                flowMeta = 1;
            }
            boolean flowTo[] = getOptimalFlowDirections(world, pos);

            if (flowTo[0]) flowIntoBlock(world, pos.add(-1, 0,  0), flowMeta);
            if (flowTo[1]) flowIntoBlock(world, pos.add( 1, 0,  0), flowMeta);
            if (flowTo[2]) flowIntoBlock(world, pos.add( 0, 0, -1), flowMeta);
            if (flowTo[3]) flowIntoBlock(world, pos.add( 0, 0,  1), flowMeta);
        }
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
        
        if(LiquidConfigGen.general.netherrackFireChance > 0)
        {
        	if(this.block.getBlock() == Blocks.NETHERRACK)
            {
            	if(!world.getBlockState(pos.up()).isFullCube() || world.getBlockState(pos.up()).getMaterial().isReplaceable())
            	{
            		if(world.rand.nextInt(LiquidConfigGen.general.netherrackFireChance) <= 1)
            		{
            	        world.setBlockState(pos.up(), Blocks.FIRE.getDefaultState().withProperty(BlockFire.AGE, world.rand.nextInt(15)));
            		}
            	}
            }
        }
    }
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
		if (entityIn instanceof EntityLivingBase)
        {
            EntityLivingBase entity = (EntityLivingBase) entityIn;
            if(getFluid() == LiquidRegistry.liquidDirt || getFluid() == LiquidRegistry.liquidCoarseDirt || getFluid() == LiquidRegistry.liquidPodzol)
            {
            	if(LiquidConfigGen.general.dirtCausesNausea)
                {
                	entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 1));
                }
                if(LiquidConfigGen.general.dirtCausesSlowness)
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
