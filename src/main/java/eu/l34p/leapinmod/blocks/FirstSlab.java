package eu.l34p.leapinmod.blocks;

import eu.l34p.leapinmod.LeapInMod;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public abstract class FirstSlab extends BlockSlab {
    public static final PropertyEnum<FirstSlab.EnumType> VARIANT = PropertyEnum.<FirstSlab.EnumType>create("variant", FirstSlab.EnumType.class);

    public FirstSlab(String name, Material material) {
        super(material);
		this.setRegistryName(name);
        this.setUnlocalizedName(LeapInMod.MODID + "." + name);
        setCreativeTab(CreativeTabs.REDSTONE);
		
		IBlockState iblockstate = this.blockState.getBaseState().withProperty(VARIANT, EnumType.DEFAULT);
		
		if(!this.isDouble()) {
			iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
		}
		
		this.setDefaultState(iblockstate);
		this.useNeighborBrightness = !this.isDouble();
    }

    @Override
    public String getUnlocalizedName(int meta) {
        return super.getUnlocalizedName();
    }

    @Override
    public IProperty<?> getVariantProperty() {
        return VARIANT;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack) {
        return EnumType.DEFAULT;
    }

    @Override
	public final IBlockState getStateFromMeta(final int meta) {
		IBlockState blockstate = this.blockState.getBaseState().withProperty(VARIANT, EnumType.DEFAULT);
		
		if(!this.isDouble()) {
			blockstate = blockstate.withProperty(HALF, ((meta&8)!=0)?EnumBlockHalf.TOP:EnumBlockHalf.BOTTOM);
		}
		
		return blockstate;
	}
	
	@Override
	public final int getMetaFromState(final IBlockState state) {
		int meta = 0;
		
		if(!this.isDouble()&& state.getValue(HALF)==EnumBlockHalf.TOP) {
			meta |= 8;
		}
		
		return meta;
    }
    
    @Override
	protected BlockStateContainer createBlockState() {
		if(!this.isDouble()){
			return new BlockStateContainer(this, new IProperty[] {VARIANT, HALF});
		}
		return new BlockStateContainer(this, new IProperty[] {VARIANT});
	}

    public static enum EnumType implements IStringSerializable
	{
		DEFAULT;

		@Override
		public String getName() {
			return "default";
		}
	}
}