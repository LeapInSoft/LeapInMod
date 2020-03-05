package eu.l34p.leapinmod.blocks;

import eu.l34p.leapinmod.LeapInMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FirstBlock extends Block {

    public FirstBlock() {
        super(Material.ROCK);
        setUnlocalizedName(LeapInMod.MODID + ".firstblock");
        setRegistryName("firstblock");
        setCreativeTab(CreativeTabs.REDSTONE);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        //ModelResourceLocation modelResourceLocation = new ModelResourceLocation(getRegistryName(), "inventory");
        ModelResourceLocation modelResourceLocation = new ModelResourceLocation("dirt", "inventory");
        
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, modelResourceLocation);
    }
}