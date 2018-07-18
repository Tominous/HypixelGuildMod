package me.kevqn.guildmod.gui;

import com.google.common.collect.Lists;

import me.kevqn.guildmod.utils.ChatColor;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;

import java.util.List;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public abstract class ModernGui extends GuiScreen {

    protected final Minecraft mc = Minecraft.getMinecraft();

    protected List<ModernTextBox> textList = Lists.newArrayList();

    private GuiButton selectedButton;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (GuiButton button : this.buttonList) {
            button.drawButton(this.mc, mouseX, mouseY);
        }

        for (GuiLabel label : this.labelList) {
            label.drawLabel(this.mc, mouseX, mouseY);
        }

        for (ModernTextBox text : this.textList) {
            text.drawTextBox();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);

            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        } else {
            for (ModernTextBox text : textList) {
                text.textboxKeyTyped(typedChar, keyCode);
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            for (int i = 0; i < this.buttonList.size(); ++i) {
                GuiButton guibutton = this.buttonList.get(i);

                if (guibutton.mousePressed(this.mc, mouseX, mouseY)) {
                    net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre event = new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre(this, guibutton, this.buttonList);
                    if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) break;
                    guibutton = event.button;
                    this.selectedButton = guibutton;
                    guibutton.playPressSound(this.mc.getSoundHandler());
                    this.actionPerformed(guibutton);
                    if (this.equals(this.mc.currentScreen))
                        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Post(this, event.button, this.buttonList));
                }
            }
        }
        
        // Renders all textboxes
        for (ModernTextBox text : this.textList) {
            text.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (this.selectedButton != null && state == 0) {
            this.selectedButton.mouseReleased(mouseX, mouseY);
            this.selectedButton = null;
        }
    }

    @Override
    public void drawDefaultBackground() {
        Gui.drawRect(0, 0, this.width, this.height, new Color(2, 2, 2, 120).getRGB());
    }

    @Override
    public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawString(text, (float) (x - fontRendererIn.getStringWidth(text) / 2), (float) y, color, false);
    }

    public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color, boolean shadow) {
        fontRendererIn.drawString(text, (float) (x - fontRendererIn.getStringWidth(text) / 2), (float) y, color, shadow);
    }

    @Override
    public void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawString(text, (float) x, (float) y, color, false);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button instanceof ModernButton) {
            buttonPressed((ModernButton) button);
        }
    }

    @Override
    public void sendChatMessage(String msg) {
        this.mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(ChatColor.AQUA + "SkinChanger" + ChatColor.GOLD + " > " + ChatColor.GRAY + msg));
    }
    
    public final void display() {
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public final void onTick(TickEvent.ClientTickEvent event) {
        MinecraftForge.EVENT_BUS.unregister(this);
        Minecraft.getMinecraft().displayGuiScreen(this);
    }
    
    public void buttonPressed(ModernButton button) {
    }
    
    /**
     * Code used to draw an entity on screen, stolen from the Inventory code
     *
     * @param posX the x position of the entity
     * @param posY the y position of the entity
     * @param scale the scale the entity should be rendered at
     * @param mouseX the x location of the mouse
     * @param mouseY the y location of the mouse
     * @param entity the entity to render on screen
     * @param previewCape true if the entities cape is being previewed
     */
    protected void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase entity, boolean previewCape) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) posX, (float) posY, 50.0F);
        GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F , 1.0F);
        if (previewCape) {
            GlStateManager.rotate(180F, 0F, 360F, 0F);
        }
        float prevYawOffset = entity.renderYawOffset;
        float prevYaw = entity.rotationYaw;
        float prevPitch = entity.rotationPitch;
        float prevYawRotation = entity.prevRotationYawHead;
        float prevHeadRotation = entity.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float) Math.atan((double) (mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        entity.renderYawOffset = previewCape ? -(float) Math.atan((double) (mouseX / 40.0F)) * 20.0F : (float) Math.atan((double) (mouseX / 40.0F)) * 20.0F;
        entity.rotationYaw = previewCape ? -(float) Math.atan((double) (mouseX / 40.0F)) * 40.0F : (float) Math.atan((double) (mouseX / 40.0F)) * 40.0F;
        entity.rotationPitch = -((float) Math.atan((double) (mouseY / 40.0F))) * 20.0F;
        entity.rotationYawHead = entity.rotationYaw;
        entity.prevRotationYawHead = entity.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(previewCape ? 180.0F : 0.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.doRenderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, true);
        rendermanager.setRenderShadow(true);
        entity.renderYawOffset = prevYawOffset;
        entity.rotationYaw = prevYaw;
        entity.rotationPitch = prevPitch;
        entity.prevRotationYawHead = prevYawRotation;
        entity.rotationYawHead = prevHeadRotation;

        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
}