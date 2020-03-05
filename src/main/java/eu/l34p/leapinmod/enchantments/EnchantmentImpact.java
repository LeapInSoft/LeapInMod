package eu.l34p.leapinmod.enchantments;

import eu.l34p.leapinmod.LeapInMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = LeapInMod.MODID)
public class EnchantmentImpact extends Enchantment {

    public EnchantmentImpact() {
        super(Rarity.UNCOMMON, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND });
        this.setRegistryName("impact");
        this.setName("impact");
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 30;
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return super.getMinEnchantability(par1) + 10;
    }

    @SubscribeEvent
    public static void onBreakBlock(BlockEvent.BreakEvent breakEvent) {
        EntityPlayer player = breakEvent.getPlayer();
        ItemStack stack = player.getHeldItemMainhand();
        EnchantmentHelper.getEnchantmentLevel(EnchantmentImpact., stack)
        stack.getEnchantmentTagList().
    }
}