package com.xray.client.gui;

import com.xray.client.xray.XrayController;
import com.xray.common.XRay;
import com.xray.common.reference.BlockData;
import com.xray.common.reference.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class GuiOverlay {

    private Minecraft mc = Minecraft.getMinecraft();

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void RenderGameOverlayEvent(RenderGameOverlayEvent.Post event) {
        onRenderBlockOverlay();
        // Draw Indicator
        if(!XrayController.drawOres() || !XRay.showOverlay)
            return;

        GlStateManager.enableAlpha();
        GlStateManager.color(0, 255, 0, 30);
        mc.renderEngine.bindTexture(new ResourceLocation(Reference.PREFIX_GUI + "circle.png"));
        Gui.drawModalRectWithCustomSizedTexture(5, 5, 0f, 0f, 5, 5, 5, 5);
        GlStateManager.disableAlpha();

        this.mc.fontRenderer.drawStringWithShadow(I18n.format("xray.overlay"), 15, 4, Color.getHSBColor(0f, 0f, 1f).getRGB() + (30 << 24));
    }

    public void onRenderBlockOverlay() {

        int baseX = 10;
        int i = 0, i2 = 0, spaceCount= 0;


        for (Map.Entry<ResourceLocation, List<BlockData>> items:
                XrayController.blockStore.blockRef.entrySet()) {

            i2 = 0;
            for (BlockData data:
                    items.getValue()) {
                this.mc.fontRenderer.drawStringWithShadow(String.format("%d", Block.getStateId(data.getState())), 15 + (100 * i), 30 + (baseX * i2), Color.white.getRGB());
                spaceCount += 10;
                i2++;
            }

            this.mc.fontRenderer.drawStringWithShadow(items.getKey().getResourcePath(), 15 + (100 * i), 20, Color.white.getRGB());
            i++;

        }

        RayTraceResult ray = mc.player.rayTrace(100, 20);
        if( ray != null && ray.typeOfHit == RayTraceResult.Type.BLOCK ) {
            IBlockState state = mc.world.getBlockState(ray.getBlockPos());

            if (XrayController.blockStore.blockRef.containsKey(state.getBlock().getRegistryName())) {
                // We have a matching key, lets find the block,
                for (BlockData data :
                        XrayController.blockStore.blockRef.get(state.getBlock().getRegistryName())) {
                    if (data.getState() != state)
                        continue;

                    RenderHelper.enableGUIStandardItemLighting();
                    this.mc.getRenderItem().renderItemAndEffectIntoGUI(state.getBlock().getPickBlock(state, ray, mc.world, ray.getBlockPos(), mc.player), 50, 50); // Blocks with no stack will display an empty image. TODO GLDraw image?
                    RenderHelper.disableStandardItemLighting();
                }
            }
        }
    }

//    private <T extends Comparable<T>> IBlockState applyPropertyValue(IBlockState state, IProperty<T> property, String rawValue) {
//        return property.parseValue(rawValue).transform(v -> state.withProperty(property, v)).or(state);
//    }

}
