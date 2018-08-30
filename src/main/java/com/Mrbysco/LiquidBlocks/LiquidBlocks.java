package com.Mrbysco.LiquidBlocks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.Mrbysco.LiquidBlocks.config.LiquidConfigGen;
import com.Mrbysco.LiquidBlocks.init.LiquidTab;
import com.Mrbysco.LiquidBlocks.init.LiquidTiles;
import com.Mrbysco.LiquidBlocks.proxy.CommonProxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = LiquidReference.MOD_ID, 
	name = LiquidReference.MOD_NAME, 
	version = LiquidReference.VERSION, 
	acceptedMinecraftVersions = LiquidReference.ACCEPTED_VERSIONS,
	dependencies = LiquidReference.DEPENDENCIES)

public class LiquidBlocks {
	@Instance(LiquidReference.MOD_ID)
	public static LiquidBlocks instance;
	
	@SidedProxy(clientSide = LiquidReference.CLIENT_PROXY_CLASS, serverSide = LiquidReference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public static final Logger logger = LogManager.getLogger(LiquidReference.MOD_ID);
		
	public static LiquidTab liquidTab = new LiquidTab();

	static {
		FluidRegistry.enableUniversalBucket();
	}

	@EventHandler
	public void PreInit(FMLPreInitializationEvent event)
	{	
		logger.info("Registering config");
		MinecraftForge.EVENT_BUS.register(new LiquidConfigGen());

		logger.info("Register Tileentity");
		LiquidTiles.register();
		
		proxy.Preinit();
	}
	
	@EventHandler
    public void init(FMLInitializationEvent event)
	{
		proxy.Init();
    }
	
	@EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
		
    }
}
