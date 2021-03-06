package com.xray.client;

import com.xray.client.gui.GuiList;
import com.xray.client.xray.XrayController;
import com.xray.common.XRay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyBindingHandler
{
	private Minecraft mc = Minecraft.getMinecraft();

	@SubscribeEvent
	public void onKeyInput( KeyInputEvent event )
    {
		if( (!FMLClientHandler.instance().isGUIOpen( GuiChat.class )) && (mc.currentScreen == null) && (mc.world != null) )
        {
			if( XRay.keyBind_keys[ XRay.keyIndex_toggleXray ].isPressed() )
			{
				XrayController.toggleDrawOres();
			}
			else if( XRay.keyBind_keys[ XRay.keyIndex_showXrayMenu ].isPressed() )
			{
				mc.displayGuiScreen( new GuiList() );
			}
		}
	}
}
