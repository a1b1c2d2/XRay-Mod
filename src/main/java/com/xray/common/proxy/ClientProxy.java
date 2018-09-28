// Forge proxy for the client side.
package com.xray.common.proxy;

import com.xray.client.KeyBindingHandler;
import com.xray.client.gui.GuiOverlay;
import com.xray.client.xray.XrayController;
import com.xray.client.xray.XrayEventHandler;
import com.xray.common.XRay;
import com.xray.common.config.ConfigHandler;
import com.xray.common.reference.OreInfo;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

//		ConfigHandler.init( event.getSuggestedConfigurationFile() );

		// Setup Keybindings
		XRay.keyBind_keys = new KeyBinding[ XRay.keyBind_descriptions.length ];
		for(int i = 0; i < XRay.keyBind_descriptions.length; ++i )
		{
			XRay.keyBind_keys[i] = new KeyBinding( XRay.keyBind_descriptions[i], XRay.keyBind_keyValues[i], "X-Ray" );
			ClientRegistry.registerKeyBinding( XRay.keyBind_keys[i] );
		}

		MinecraftForge.EVENT_BUS.register( new KeyBindingHandler() );
		MinecraftForge.EVENT_BUS.register( new XrayEventHandler() );
		MinecraftForge.EVENT_BUS.register( new GuiOverlay() );
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);

		// Block list new?
//		ForgeRegistries.BLOCKS.getValuesCollection().stream().flatMap( block -> block.getBlockState().getValidStates() ).collect(Collectors.toList());

		List<IBlockState> blocksList = new ArrayList<>();
		Collection<Block> blocks = ForgeRegistries.BLOCKS.getValuesCollection();

		blocks.forEach( block -> blocksList.add(block.getDefaultState()) );

//		blocks.forEach( block -> block.getBlockState().getValidStates().forEach(iBlockState -> {
//			iBlockState.getProperties().forEach( (iProperty, comparable) -> {
//
//				IBlockState state;
//				if( !iProperty.getName().equals("variant") )
//					state = iBlockState.getBlock().getBlockState().getBaseState();
//				else {
//					state = iBlockState.getBlock().get;
//					iProperty.getAllowedValues().forEach( consum -> {
//					    System.out.println(consum.toString());
//                        IBlockState tmpState = applyPropertyValue(state, iProperty, consum.toString());
//
//                        if( blocksList.contains(tmpState) )
//                            return;
//
//                        blocksList.add( tmpState );
//                    } );
//				}
//
//				if( blocksList.contains(state) )
//					return;
//
//				blocksList.add( state );
//			} );
//		} ));

        blocksList.forEach(System.out::println);
		XRay.guiBlockList.addAll(blocksList);

//		ConfigHandler.setup(); // Read the config file and setup environment.

//		for ( Block block : ForgeRegistries.BLOCKS ) {
//			NonNullList<ItemStack> subBlocks = NonNullList.create();
//			block.getSubBlocks( block.getCreativeTabToDisplayOn(), subBlocks );
//			if ( Blocks.AIR.equals( block ) )
//				continue; // avoids troubles
//
//			for( ItemStack subBlock : subBlocks ) {
//				if( subBlock.getItem() == Items.AIR )
//				    continue;
//
//				XRay.guiBlockList.add( subBlock.isEmpty() ? new ItemStack(block) : subBlock );
//			}
//		}
	}

	@Override
	public void onExit(FMLServerStoppingEvent event)
	{
		XrayController.shutdownExecutor(); // Make sure threads don't lock the JVM
	}

	<T extends Comparable<T>> IBlockState applyPropertyValue(IBlockState state, IProperty<T> property, String rawValue) {
		return property.parseValue(rawValue).transform(v -> state.withProperty(property, v)).or(state);
	}
}