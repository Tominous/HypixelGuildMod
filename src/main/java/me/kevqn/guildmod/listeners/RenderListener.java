package me.kevqn.guildmod.listeners;

import net.minecraft.util.*;
import net.minecraftforge.client.event.*;
import me.kevqn.guildmod.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;

public class RenderListener
{
    private ResourceLocation alert;
    
    public RenderListener() {
        this.alert = new ResourceLocation("nerd_alert", "textures/alert.png");
    }
    
    @SubscribeEvent
    public void onGameRenderOverlayPost(final RenderGameOverlayEvent.Post event) {
        if (GuildMod.isFriendAlertEnabled() && ChatListener.shouldStart()) {
            this.render();
        }
    }
    
    private void render() {
        GlStateManager.pushMatrix();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.alert);
        Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(GuildMod.getX(), GuildMod.getY(), 0, 0, 256, 256);
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
