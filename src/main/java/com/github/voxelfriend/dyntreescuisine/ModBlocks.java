package com.github.voxelfriend.dyntreescuisine;

import java.util.ArrayList;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.blocks.LeavesProperties;
import com.github.voxelfriend.dyntreescuisine.blocks.BlockCitrusLog;
import com.github.voxelfriend.dyntreescuisine.blocks.BlockCitrusFruit;
import com.github.voxelfriend.dyntreescuisine.blocks.BlockIronLog;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = ModConstants.MODID)
public class ModBlocks {

	public static BlockCitrusLog citrusLog;
	public static BlockCitrusFruit blockFruit;

	public static LeavesProperties citrusLeavesProperties;
	public static LeavesProperties[] exampleLeavesProperties;
	
	public static void preInit() {
		
		Block blockLime = new BlockCitrusFruit("lime") {
            public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
                if(state.getValue(AGE) >= 3) {
                    Item limeItem = Item.REGISTRY.getObject(new ResourceLocation("cuisine:food"));
                    ItemStack limeStack = new ItemStack(limeItem, 1, 15);
                    drops.add(limeStack);
                }
            };
        };
        Block blockOrange = new BlockCitrusFruit("orange") {
            public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
                if(state.getValue(AGE) >= 3) {
                    Item limeItem = Item.REGISTRY.getObject(new ResourceLocation("cuisine:food"));
                    ItemStack limeStack = new ItemStack(limeItem, 1, 14);
                    drops.add(limeStack);
                }
            };
        };
		
		citrusLog = new BlockCitrusLog();
		
		//Set up primitive leaves. This controls what is dropped on shearing, leaves replacement, etc.
		citrusLeavesProperties = new LeavesProperties(Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.SILVER)) {
			@Override
			@SideOnly(Side.CLIENT)
			public int foliageColorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos) {
				int hashmap = (32 & ((pos.getX() * 2536123) ^ (pos.getY() * 642361431 ) ^ (pos.getZ() * 86547653)));
				int r = 150 + (32 & hashmap) ;   //173
				int g = 56 + (16 & (hashmap * 763621));
				int b = 24;
				
				return r << 16 | g << 8 | b;
			}
		};
		
		//For this demonstration it is vital that these are never reordered.  If a leaves properties is removed from the
		//mod then there should be a LeavesProperties.NULLPROPERTIES used as a placeholder.
		exampleLeavesProperties = new LeavesProperties[] {
				citrusLeavesProperties
		};
		
		int seq = 0;
		
		for(LeavesProperties lp : exampleLeavesProperties) {
			TreeHelper.getLeavesBlockForSequence(ModConstants.MODID, seq++, lp);
		}
	}
	
	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		final IForgeRegistry<Block> registry = event.getRegistry();
						
		ArrayList<Block> treeBlocks = new ArrayList<>();
		ModTrees.citrusTrees.forEach(tree -> tree.getRegisterableBlocks(treeBlocks));
		
		treeBlocks.addAll(TreeHelper.getLeavesMapForModId(ModConstants.MODID).values());
		
		registry.register(citrusLog);
		registry.registerAll(treeBlocks.toArray(new Block[0]));
	}
	
}
