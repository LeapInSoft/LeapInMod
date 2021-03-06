package eu.l34p.leapinmod.proxy;

import eu.l34p.leapinmod.LeapBlocks;
import eu.l34p.leapinmod.LeapItems;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent fmlEvent) {
        super.preInit(fmlEvent);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        LeapItems.initModels();
        LeapBlocks.initModels();
    }
}