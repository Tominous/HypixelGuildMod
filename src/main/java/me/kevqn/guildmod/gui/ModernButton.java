package me.kevqn.guildmod.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class ModernButton extends GuiButton {

    private Color backgroundColorEnabled = null;
    private Color backgroundColorDisabled = null;

    public int id;
    public int width;
    public int height;
    public int xPosition;
    public int yPosition;
    public boolean enabled;
    public boolean visible;
    public boolean hovered;
    public String displayString;

    public ModernButton(int buttonId, int x, int y, String buttonText)
    {
        this(buttonId, x, y, 200, 20, buttonText);
    }

    public ModernButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText != null ? buttonText : "");
        this.width = 200;
        this.height = 20;
        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.width = widthIn;
        this.height = heightIn;
        this.displayString = buttonText != null ? buttonText : "";
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    @Override
    protected int getHoverState(boolean mouseOver) {
        int i = 1;

        if (!this.enabled) {
            i = 0;
        } else if (mouseOver) {
            i = 2;
        }
        return i;
    }

    /**
     * Draws this button to the screen.
     */
    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            FontRenderer fontrenderer = mc.fontRendererObj;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int i = this.getHoverState(this.hovered);

            if (this.enabled) {
                drawRect(this.xPosition, this.yPosition, this.xPosition + width, this.yPosition + height, this.backgroundColorEnabled == null ? new Color(255, 255, 255, 75).getRGB() : this.backgroundColorEnabled.getRGB());
            } else {
                drawRect(this.xPosition, this.yPosition, this.xPosition + width, this.yPosition + height, this.backgroundColorDisabled == null ? new Color(100, 100, 100, 75).getRGB() : this.backgroundColorDisabled.getRGB());
            }

            this.mouseDragged(mc, mouseX, mouseY);
            int j = 14737632;

            if (!this.enabled) {
                j = Color.WHITE.getRGB();
            } else if (this.hovered) {
                j = 16777120;
            }
            fontrenderer.drawString(this.displayString, (this.xPosition + this.width / 2 - fontrenderer.getStringWidth(this.displayString) / 2), this.yPosition + (this.height - 8) / 2, j, false);
        }
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

    /**
     * Whether the mouse cursor is currently over the button.
     */
    @Override
    public boolean isMouseOver() {
        return this.hovered;
    }

    @Override
    public void playPressSound(SoundHandler soundHandlerIn) {
        soundHandlerIn.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
    }

    @Override
    public int getButtonWidth()
    {
        return this.width;
    }

    @Override
    public void setWidth(int width)
    {
        this.width = width;
    }

    public String getText() {
        return this.displayString;
    }

    public void setText(String text) {
        this.displayString = text != null ? text : "";
    }

    public void setBackEnabled(Color color) {
        this.backgroundColorEnabled = color;
    }

    public void setBackDisabled(Color color) {
        this.backgroundColorDisabled = color;
    }
}
