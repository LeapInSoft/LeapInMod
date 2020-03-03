package eu.l34p.leapinmod;

import eu.l34p.leapinmod.blocks.FirstBlock;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LeapBlocks {

    @GameRegistry.ObjectHolder("leapinmod:firstblock")
    public static FirstBlock firstBlock;

    @GameRegistry.ObjectHolder("minecraft:stone_block")
    public static Block stoneBlock;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
    }

    @SideOnly(Side.CLIENT)
    public static void initItemModels() {
    }
}