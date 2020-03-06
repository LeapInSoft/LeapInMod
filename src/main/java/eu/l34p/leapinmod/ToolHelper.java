package eu.l34p.leapinmod;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.event.ForgeEventFactory;
/*import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.TinkerNetwork;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.events.TinkerToolEvent;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tinkering.TinkersItem;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.tools.ranged.IProjectile;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.common.network.ToolBreakAnimationPacket;
import slimeknights.tconstruct.tools.modifiers.ModReinforced;*/

import java.util.List;
import java.util.function.Predicate;

public final class ToolHelper {

    private ToolHelper() {
    }

    /**
     * Returns true if the tool is effective for harvesting the given block.
     */
    public static boolean isToolEffective(ItemStack stack, IBlockState state) {
        // check material
        for (String type : stack.getItem().getToolClasses(stack)) {
            if (state.getBlock().isToolEffective(type, state)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if an item has the right harvest level of the correct type for the
     * block.
     */
    public static boolean canHarvest(ItemStack stack, IBlockState state) {
        Block block = state.getBlock();

        // doesn't require a tool
        if (state.getMaterial().isToolNotRequired()) {
            return true;
        }

        String type = block.getHarvestTool(state);
        int level = block.getHarvestLevel(state);

        return stack.getItem().getHarvestLevel(stack, type, null, state) >= level;
    }

    /* Harvesting */

    public static ImmutableList<BlockPos> calcAOEBlocks(ItemStack stack, World world, EntityPlayer player,
            BlockPos origin, int width, int height, int depth) {
        return calcAOEBlocks(stack, world, player, origin, width, height, depth, -1);
    }

    public static RayTraceResult rayTrace(World worldIn, EntityPlayer playerIn, boolean useLiquids)
    {
        float f = playerIn.rotationPitch;
        float f1 = playerIn.rotationYaw;
        double d0 = playerIn.posX;
        double d1 = playerIn.posY + (double)playerIn.getEyeHeight();
        double d2 = playerIn.posZ;
        Vec3d vec3d = new Vec3d(d0, d1, d2);
        float f2 = MathHelper.cos(-f1 * 0.017453292F - (float)Math.PI);
        float f3 = MathHelper.sin(-f1 * 0.017453292F - (float)Math.PI);
        float f4 = -MathHelper.cos(-f * 0.017453292F);
        float f5 = MathHelper.sin(-f * 0.017453292F);
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d3 = playerIn.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
        Vec3d vec3d1 = vec3d.addVector((double)f6 * d3, (double)f5 * d3, (double)f7 * d3);
        return worldIn.rayTraceBlocks(vec3d, vec3d1, useLiquids, !useLiquids, false);
    }
    
    public static ImmutableList<BlockPos> calcAOEBlocks(ItemStack stack, World world, EntityPlayer player,
            BlockPos origin, int width, int height, int depth, int distance) {
        // only works with toolcore because we need the raytrace call
        if (stack.isEmpty()) {
            return ImmutableList.of();
        }

        // find out where the player is hitting the block
        IBlockState state = world.getBlockState(origin);

        if (!isToolEffective(stack, state)) {
            return ImmutableList.of();
        }

        if (state.getMaterial() == Material.AIR) {
            // what are you DOING?
            return ImmutableList.of();
        }

        // we know the block and we know which side of the block we're hitting. time to
        // calculate the depth along the different axes
        int x, y, z;
        BlockPos start = origin;
        
        RayTraceResult mop = rayTrace(world, player, true);

        if(mop == null || !origin.equals(mop.getBlockPos())) {
            return ImmutableList.of();
        }

        switch (mop.sideHit) {
            case DOWN:
            case UP:
                System.out.println("Leap -- DOWN/UP !");
                // x y depends on the angle we look?
                Vec3i vec = player.getHorizontalFacing().getDirectionVec();
                x = vec.getX() * height + vec.getZ() * width;
                y = mop.sideHit.getAxisDirection().getOffset() * -depth;
                z = vec.getX() * width + vec.getZ() * height;
                start = start.add(-x / 2, 0, -z / 2);
                if (x % 2 == 0) {
                    if (x > 0 && mop.hitVec.x - mop.getBlockPos().getX() > 0.5d) {
                        start = start.add(1, 0, 0);
                    } else if (x < 0 && mop.hitVec.x - mop.getBlockPos().getX() < 0.5d) {
                        start = start.add(-1, 0, 0);
                    }
                }
                if (z % 2 == 0) {
                    if (z > 0 && mop.hitVec.z - mop.getBlockPos().getZ() > 0.5d) {
                        start = start.add(0, 0, 1);
                    } else if (z < 0 && mop.hitVec.z - mop.getBlockPos().getZ() < 0.5d) {
                        start = start.add(0, 0, -1);
                    }
                }
                break;
            case NORTH:
            case SOUTH:
                System.out.println("Leap -- NORTH/SOUTH !");
                x = width;
                y = height;
                z = mop.sideHit.getAxisDirection().getOffset() * -depth;
                start = start.add(-x / 2, -y / 2, 0);
                if (x % 2 == 0 && mop.hitVec.x - mop.getBlockPos().getX() > 0.5d) {
                    start = start.add(1, 0, 0);
                }
                if (y % 2 == 0 && mop.hitVec.y - mop.getBlockPos().getY() > 0.5d) {
                    start = start.add(0, 1, 0);
                }
                break;
            case WEST:
            case EAST:
                x = mop.sideHit.getAxisDirection().getOffset() * -depth;
                y = height;
                z = width;
                start = start.add(-0, -y / 2, -z / 2);
                if (y % 2 == 0 && mop.hitVec.y - mop.getBlockPos().getY() > 0.5d) {
                    start = start.add(0, 1, 0);
                }
                if (z % 2 == 0 && mop.hitVec.z - mop.getBlockPos().getZ() > 0.5d) {
                    start = start.add(0, 0, 1);
                }
                break;
            default:
                x = y = z = 0;
        }

        ImmutableList.Builder<BlockPos> builder = ImmutableList.builder();
        for (int xp = start.getX(); xp != start.getX() + x; xp += x / MathHelper.abs(x)) {
            for (int yp = start.getY(); yp != start.getY() + y; yp += y / MathHelper.abs(y)) {
                for (int zp = start.getZ(); zp != start.getZ() + z; zp += z / MathHelper.abs(z)) {
                    // don't add the origin block
                    if (xp == origin.getX() && yp == origin.getY() && zp == origin.getZ()) {
                        continue;
                    }
                    if (distance > 0 && MathHelper.abs(xp - origin.getX()) + MathHelper.abs(yp - origin.getY())
                            + MathHelper.abs(zp - origin.getZ()) > distance) {
                        continue;
                    }
                    BlockPos pos = new BlockPos(xp, yp, zp);
                    if (isToolEffective(stack, world.getBlockState(pos))) {
                        builder.add(pos);
                    }
                }
            }
        }

        return builder.build();
    }
}