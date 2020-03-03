package eu.l34p.leapinmod;

import eu.l34p.leapinmod.items.FirstItem;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LeapItems {

    @GameRegistry.ObjectHolder("leapinmod:firstitem")
    public static FirstItem firstItem;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        firstItem.initModel();
    }
}
