package com.xray.common;

import com.xray.client.xray.XrayController;
import com.xray.common.proxy.CommonProxy;
import com.xray.common.reference.BlockData;
import com.xray.common.reference.BlockId;
import com.xray.common.reference.OreInfo;
import com.xray.common.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid= Reference.MOD_ID, name= Reference.MOD_NAME, version=Reference.MOD_VERSION /*guiFactory = Reference.GUI_FACTORY*/)
public class XRay
{
	public static Set<BlockId> lst = new HashSet();

	public static ArrayList<OreInfo> blockList = new ArrayList<>();
	private static Minecraft mc = Minecraft.getMinecraft();

	// Config settings
	public static Configuration config;
	public static float outlineThickness = 1f;
	public static float outlineOpacity = 1f;
	public static boolean showOverlay = true;

    // Radius +/- around the player to search. So 8 is 8 on left and right of player plus under the player. So 17x17 area.
    public static final int[] distNumbers = new int[] {8, 16, 32, 48, 64, 80, 128, 256};

    // Keybindings
	public static final int keyIndex_toggleXray = 0;
	public static final int keyIndex_showXrayMenu = 1;
	public static final int[] keyBind_keyValues = { Keyboard.KEY_BACKSLASH, Keyboard.KEY_Z };
	public static final String[] keyBind_descriptions = { I18n.format("xray.config.toggle"), I18n.format("xray.config.open")};
	public static KeyBinding[] keyBind_keys = null;

	public static Logger logger;

	// The instance of your mod that Forge uses.
	@Instance(Reference.MOD_ID)
	public static XRay instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide="com.xray.common.proxy.ClientProxy", serverSide="com.xray.common.proxy.ServerProxy")
	private static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();

		logger.debug(I18n.format("xray.debug.init"));
		proxy.preInit( event );
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init( event );
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit( event );
	}

	@EventHandler
	public void onExit(FMLServerStoppingEvent event)
	{
		proxy.onExit(event);
	}
}
