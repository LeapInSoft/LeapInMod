package eu.l34p.leapinmod;

import eu.l34p.leapinmod.blocks.FirstBlock;
import eu.l34p.leapinmod.blocks.FirstHalfSlab;
import eu.l34p.leapinmod.blocks.FirstDoubleSlab;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LeapBlocks {

    @GameRegistry.ObjectHolder("leapinmod:firstblock")
    public static FirstBlock firstBlock;

    @GameRegistry.ObjectHolder("leapinmod:first_slab")
    public static FirstHalfSlab firstHalfSlab;

    @GameRegistry.ObjectHolder("leapinmod:first_double_slab")
    public static FirstDoubleSlab firstDoubleSlab;

    @GameRegistry.ObjectHolder("minecraft:stone_block")
    public static Block stoneBlock;
    
    @SideOnly(Side.CLIENT)
    public static void initModels() {
        firstBlock.initModel();
    }

    @SideOnly(Side.CLIENT)
    public static void initItemModels() {
    }
}