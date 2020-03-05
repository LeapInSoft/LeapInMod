package eu.l34p.leapinmod.proxy;

import eu.l34p.leapinmod.LeapBlocks;
import eu.l34p.leapinmod.blocks.FirstBlock;
import eu.l34p.leapinmod.blocks.FirstDoubleSlab;
import eu.l34p.leapinmod.blocks.FirstHalfSlab;
import eu.l34p.leapinmod.items.FirstItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CommonProxy {
    public void preInit(FMLPreInitializationEvent fmlEvent) {
    }

    public void init(FMLInitializationEvent fmlEvent) {
    }

    public void postInit(FMLPostInitializationEvent fmlEvent) {
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new FirstBlock());
        event.getRegistry().register(new FirstHalfSlab("first_slab", Material.ROCK));
        event.getRegistry().register(new FirstDoubleSlab("first_double_slab", Material.ROCK));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(LeapBlocks.firstBlock).setRegistryName(LeapBlocks.firstBlock.getRegistryName()));
        event.getRegistry().register(new ItemSlab(LeapBlocks.firstHalfSlab, LeapBlocks.firstHalfSlab, LeapBlocks.firstDoubleSlab).setRegistryName(LeapBlocks.firstHalfSlab.getRegistryName()));
        event.getRegistry().register(new FirstItem());
    }
}