package eu.l34p.leapinmod;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import eu.l34p.leapinmod.proxy.CommonProxy;

@Mod(modid = LeapInMod.MODID, name = LeapInMod.NAME, version = LeapInMod.VERSION)
public class LeapInMod
{
    public static final String MODID = "leapinmod";
    public static final String NAME = "Leap In Mod";
    public static final String VERSION = "0.0.1";

    @SidedProxy(clientSide = "eu.l34p.leapinmod.proxy.ClientProxy", serverSide = "mcjty.modtut.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static LeapInMod instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent fmlEvent)
    {
        logger = fmlEvent.getModLog();
        proxy.preInit(fmlEvent);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent fmlEvent)
    {
        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        logger.info("Impact Enchant >> {}", LeapEnchantments.impact.getRegistryName());
        proxy.init(fmlEvent);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent fmlEvent) {
        proxy.postInit(fmlEvent);
    }
}
