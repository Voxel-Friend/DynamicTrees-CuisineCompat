package com.github.voxelfriend.dyntreescuisine.blocks;

import com.ferreusveritas.dynamictrees.DynamicTrees;

import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

public class BlockCitrusLog extends BlockLog {
	
	public static final String name = "ironlog";
	
	public BlockCitrusLog() {
		this(name);
		setCreativeTab(DynamicTrees.dynamicTreesTab);
	}
	
	public BlockCitrusLog(String name) {
		this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
		setDefaultState(this.blockState.getBaseState());
		setRegistryName(name);
		setUnlocalizedName(getRegistryName().toString());
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {LOG_AXIS});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		
		IBlockState iblockstate = this.getDefaultState();
		
		switch (meta & 12) {
			case 0: iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);  break;
			case 4: iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);  break;
			case 8: iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);  break;
			default: iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
		}
		
		return iblockstate;
	}
	
	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		
		switch (state.getValue(LOG_AXIS)) {
			case X:  i |= 4; break;
			case Z:  i |= 8; break;
			case NONE: i |= 12; break;
			default: break;
		}
		
		return i;
	}
	
}
