package eu.l34p.leapinmod.enchantments;

import com.google.common.collect.ImmutableList;

import eu.l34p.leapinmod.LeapEnchantments;
import eu.l34p.leapinmod.LeapInMod;
import eu.l34p.leapinmod.ToolHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.ForgeHooks;
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

    public ImmutableList<BlockPos> getAOEBlocks(ItemStack stack, World world, EntityPlayer player, BlockPos origin) {
        return ToolHelper.calcAOEBlocks(stack, world, player, origin, 3, 3, 1);
    }

    @SubscribeEvent
    public static void onBreakBlock(BlockEvent.BreakEvent breakEvent) {
        EntityPlayer player = breakEvent.getPlayer();
        ItemStack stack = player.getHeldItemMainhand();

        int impactLevel = EnchantmentHelper.getEnchantmentLevel(LeapEnchantments.impact, stack);
        
        if (impactLevel < 1) return;
        World world = breakEvent.getWorld();
        BlockPos blockPos = breakEvent.getPos();
        IBlockState blockState = breakEvent.getState();

        
        if(!world.isRemote && ForgeHooks.isToolEffective(world, blockPos, stack)) {
            System.out.println("Leap -- I'm in !");
            ImmutableList<BlockPos> blocksPos = ToolHelper.calcAOEBlocks(stack, world, player, blockPos, 3, 3, 1);

            for (BlockPos targetBlockPos : blocksPos) {
                Block targetBlock = world.getBlockState(targetBlockPos).getBlock();
                

                if (ForgeHooks.isToolEffective(world, targetBlockPos, stack)
                    && ForgeHooks.canHarvestBlock(targetBlock, player, world, targetBlockPos)) {
                    
                    System.out.println("Leap -- Can havest !");
                    stack.onBlockDestroyed(world, blockState, blockPos, player);
                    ((EntityPlayerMP)player).interactionManager.tryHarvestBlock(targetBlockPos);
                }
            }
        }
    }
}