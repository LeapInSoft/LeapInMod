package eu.l34p.leapinmod.blocks;

import net.minecraft.block.material.Material;

public class FirstHalfSlab extends FirstSlab {
    public FirstHalfSlab(String name, Material material) {
        super(name, material);
    }

    public boolean isDouble()
    {
        return false;
    }
}