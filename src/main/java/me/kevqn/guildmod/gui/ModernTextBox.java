package me.kevqn.guildmod.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.MathHelper;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class ModernTextBox extends Gui {

    private String alertMessage = "";

    private final int id;
    private final FontRenderer fontRendererInstance;
    public int xPosition;
    public int yPosition;
    /** The width of this text field. */
    public int width;
    public int height;
    /** Has the current text being edited on the textbox. */
    private String text = "";
    private int maxStringLength = 16;
    private int cursorCounter;
    private boolean enableBackgroundDrawing = true;
    /** if true the textbox can lose focus by clicking elsewhere on the screen */
    private boolean canLoseFocus = true;
    /** If this value is true along with isEnabled, keyTyped will process the keys. */
    private boolean isFocused = false;
    /** If this value is true along with isFocused, keyTyped will process the keys. */
    private boolean isEnabled = true;
    /** The current character index that should be used as start of the rendered text. */
    private int lineScrollOffset;
    private int cursorPosition;
    /** other selection position, maybe the same as the cursor */
    private int selectionEnd;
    private int enabledColor = 14737632;
    private int disabledColor = 7368816;
    /** True if this textbox is visible */
    private boolean visible = true;
    private GuiPageButtonList.GuiResponder guiResponder;

    private boolean onlyAllowNumbers = false;
    private String noTextMessage = "Write here!";

    private boolean running = false;

    public ModernTextBox(int componentId, int x, int y, int width, int height) {
        this(componentId, x, y, width, height, false);
    }

    public ModernTextBox(int componentId, int x, int y, int width, int height, boolean onlyAllowNumbers) {
        this(componentId, x, y, width, height, "Write Here!", onlyAllowNumbers);
    }

    public ModernTextBox(int componentId, int x, int y, int width, int height, String noTextMessage, boolean onlyAllowNumbers) {
        this.id = componentId;
        this.fontRendererInstance = Minecraft.getMinecraft().fontRendererObj;
        this.xPosition = x;
        this.yPosition = y;
        this.width = width;
        this.height = height;
        this.noTextMessage = noTextMessage;
        this.onlyAllowNumbers = onlyAllowNumbers;
    }

    public void setGuiResponder(GuiPageButtonList.GuiResponder guiResponder) {
        this.guiResponder = guiResponder;
    }

    /**
     * Increments the cursor counter
     */
    public void updateCursorCounter() {
        ++this.cursorCounter;
    }

    /**
     * Sets the text of the textbox
     */
    public void setText(String text) {
        if (text == null) {
            this.text = "";
            this.setCursorPositionEnd();
            return;
        }
        
        if (format(text).length() > this.maxStringLength) {
            this.text = format(text).substring(0, this.maxStringLength);
        } else {
            this.text = format(text);
        }
        this.setCursorPositionEnd();
    }

    /**
     * Returns the contents of the textbox
     */
    public String getText() {
        return this.text;
    }

    /**
     * returns the text between the cursor and selectionEnd
     */
    public String getSelectedText() {
        int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition: this.selectionEnd;
        int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd: this.cursorPosition;
        return this.text.substring(i, j);
    }

    /**
     * replaces selected text, or inserts text at the position on the cursor
     */
    public void writeText(String text) {
        String s = "";
        String s1 = ChatAllowedCharacters.filterAllowedCharacters(text);
        int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition: this.selectionEnd;
        int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd: this.cursorPosition;
        int k = this.maxStringLength - this.text.length() - (i - j);
        int l;

        if (this.text.length() > 0) {
            s = s + this.text.substring(0, i);
        }

        if (k < s1.length()) {
            s = s + s1.substring(0, k);
            l = k;
        } else {
            s = s + s1;
            l = s1.length();
        }

        if (this.text.length() > 0 && j < this.text.length()) {
            s = s + this.text.substring(j);
        }

        this.text = s;
        this.moveCursorBy(i - this.selectionEnd + l);

        if (this.guiResponder != null) {
            this.guiResponder.func_175319_a(this.id, this.text);
        }
    }

    /**
     * Deletes the specified number of words starting at the cursor position. Negative numbers will delete words left of
     * the cursor.
     */
    public void deleteWords(int words) {
        if (this.text.length() != 0) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText("");
            } else {
                this.deleteFromCursor(this.getNthWordFromCursor(words) - this.cursorPosition);
            }
        }
    }

    /**
     * delete the selected text, otherwsie deletes characters from either side of the cursor. params: delete num
     */
    public void deleteFromCursor(int num) {
        if (this.text.length() != 0) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText("");
            } else {
                boolean flag = num < 0;
                int i = flag ? this.cursorPosition + num: this.cursorPosition;
                int j = flag ? this.cursorPosition: this.cursorPosition + num;
                String s = "";

                if (i >= 0) {
                    s = this.text.substring(0, i);
                }

                if (j < this.text.length()) {
                    s = s + this.text.substring(j);
                }

                this.text = s;

                if (flag) {
                    this.moveCursorBy(num);
                }

                if (this.guiResponder != null) {
                    this.guiResponder.func_175319_a(this.id, this.text);
                }
            }
        }
    }

    public int getId() {
        return this.id;
    }

    /**
     * see @getNthNextWordFromPos() params: N, position
     */
    public int getNthWordFromCursor(int position) {
        return this.getNthWordFromPos(position, this.getCursorPosition());
    }

    /**
     * gets the position of the nth word. N may be negative, then it looks backwards. params: N, position
     */
    public int getNthWordFromPos(int number, int position) {
        return this.getNthWordFromPos(number, position, true);
    }

    public int getNthWordFromPos(int number, int position, boolean something) {
        int i = position;
        boolean flag = number < 0;
        int j = Math.abs(number);

        for (int k = 0; k < j; ++k) {
            if (!flag) {
                int l = this.text.length();
                i = this.text.indexOf(32, i);

                if (i == -1) {
                    i = l;
                } else {
                    while (something && i < l && this.text.charAt(i) == 32) {
                        ++i;
                    }
                }
            } else {
                while (something && i > 0 && this.text.charAt(i -1) == 32) {
                    --i;
                }

                while (i > 0 && this.text.charAt(i -1) != 32) {
                    --i;
                }
            }
        }

        return i;
    }

    /**
     * Moves the text cursor by a specified number of characters and clears the selection
     */
    public void moveCursorBy(int moveBy) {
        this.setCursorPosition(this.selectionEnd + moveBy);
    }

    /**
     * sets the position of the cursor to the provided index
     */
    public void setCursorPosition(int position) {
        this.cursorPosition = position;
        int i = this.text.length();
        this.cursorPosition = MathHelper.clamp_int(this.cursorPosition, 0, i);
        this.setSelectionPos(this.cursorPosition);
    }

    /**
     * sets the cursors position to the beginning
     */
    public void setCursorPositionZero() {
        this.setCursorPosition(0);
    }

    /**
     * sets the cursors position to after the text
     */
    public void setCursorPositionEnd() {
        this.setCursorPosition(this.text.length());
    }

    /**
     * Call this method from your GuiScreen to process the keys into the textbox
     */
    public boolean textboxKeyTyped(char c, int keyCode) {
        if (!this.isFocused || this.running) {
            return false;
        } else if (GuiScreen.isKeyComboCtrlA(keyCode)) {
            this.setCursorPositionEnd();
            this.setSelectionPos(0);
            return true;
        } else if (GuiScreen.isKeyComboCtrlC(keyCode)) {
            GuiScreen.setClipboardString(this.getSelectedText());
            return true;
        } else if (GuiScreen.isKeyComboCtrlV(keyCode)) {
            if (this.isEnabled) {
                this.writeText(format(GuiScreen.getClipboardString()));
            }

            return true;
        } else if (GuiScreen.isKeyComboCtrlX(keyCode)) {
            GuiScreen.setClipboardString(this.getSelectedText());

            if (this.isEnabled) {
                this.writeText("");
            }
            return true;
        } else {
            switch (keyCode) {
                case 14:
                    if (GuiScreen.isCtrlKeyDown()) {
                        if (this.isEnabled) {
                            this.deleteWords( -1);
                        }
                    }
                    else if (this.isEnabled) {
                        this.deleteFromCursor( -1);
                    }

                    return true;
                case 199:
                    if (GuiScreen.isShiftKeyDown()) {
                        this.setSelectionPos(0);
                    } else {
                        this.setCursorPositionZero();
                    }
                    return true;
                case 203:
                    if (GuiScreen.isShiftKeyDown()) {
                        if (GuiScreen.isCtrlKeyDown()) {
                            this.setSelectionPos(this.getNthWordFromPos( -1, this.getSelectionEnd()));
                        }
                        else {
                            this.setSelectionPos(this.getSelectionEnd() -1);
                        }
                    }
                    else if (GuiScreen.isCtrlKeyDown()) {
                        this.setCursorPosition(this.getNthWordFromCursor( -1));
                    }
                    else {
                        this.moveCursorBy( -1);
                    }

                    return true;
                case 205:
                    if (GuiScreen.isShiftKeyDown()) {
                        if (GuiScreen.isCtrlKeyDown()) {
                            this.setSelectionPos(this.getNthWordFromPos(1, this.getSelectionEnd()));
                        } else {
                            this.setSelectionPos(this.getSelectionEnd() + 1);
                        }
                    } else if (GuiScreen.isCtrlKeyDown()) {
                        this.setCursorPosition(this.getNthWordFromCursor(1));
                    } else {
                        this.moveCursorBy(1);
                    }
                    return true;
                case 207:
                    if (GuiScreen.isShiftKeyDown()) {
                        this.setSelectionPos(this.text.length());
                    } else {
                        this.setCursorPositionEnd();
                    }
                    return true;
                case 211:
                    if (GuiScreen.isCtrlKeyDown()) {
                        if (this.isEnabled) {
                            this.deleteWords(1);
                        }
                    } else if (this.isEnabled) {
                        this.deleteFromCursor(1);
                    }
                    return true;
                default:
                    if (ChatAllowedCharacters.isAllowedCharacter(c)) {
                        if (onlyAllowNumbers) {
                            if (Character.isDigit(c)) {
                                if (this.isEnabled) {
                                    this.writeText(Character.toString(c));
                                }
                            } else if (!running) {
                                alert("Only numbers can be used!", 1250);
                            }
                        } else {
                            if (this.isEnabled) {
                                this.writeText(Character.toString(c));
                            }
                        }
                        return true;
                    } else {
                        return false;
                    }
            }
        }
    }

    /**
     * Args: x, y, buttonClicked
     */
    public void mouseClicked(int x, int y, int buttonClicked) {
        boolean flag = x >= this.xPosition && x < this.xPosition + this.width && y >= this.yPosition && y < this.yPosition + this.height;

        if (this.canLoseFocus && this.isEnabled && !this.running) {
            this.setFocused(flag);
        }

        if (this.isFocused && flag && buttonClicked == 0) {
            int i = x - this.xPosition;

            if (this.enableBackgroundDrawing) {
                i -= 4;
            }

            String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
            this.setCursorPosition(this.fontRendererInstance.trimStringToWidth(s, i).length() + this.lineScrollOffset);
        }
    }

    /**
     * Draws the textbox
     */
    public void drawTextBox() {
        if (this.getVisible()) {
            if (this.getEnableBackgroundDrawing()) {
                if (this.isEnabled) {
                    drawRect(this.xPosition, this.yPosition, this.xPosition + width, this.yPosition + height, new Color(0, 148, 255, 75).getRGB());
                } else {
                    drawRect(this.xPosition, this.yPosition, this.xPosition + width, this.yPosition + height,  new Color(0, 125, 215, 75).getRGB());
                }
            }

            int i = this.isEnabled ? this.enabledColor : this.disabledColor;
            int j = this.cursorPosition - this.lineScrollOffset;
            int k = this.selectionEnd - this.lineScrollOffset;
            String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
            boolean flag = j >= 0 && j <= s.length();
            boolean flag1 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && flag;
            int l = this.enableBackgroundDrawing ? this.xPosition + 4 : this.xPosition;
            int i1 = this.enableBackgroundDrawing ? this.yPosition + (this.height - 8) / 2 : this.yPosition;
            int j1 = l;

            if (k > s.length()) {
                k = s.length();
            }

            if (!alertMessage.isEmpty()) {
                this.fontRendererInstance.drawString(alertMessage, ((this.xPosition + this.width / 2) - fontRendererInstance.getStringWidth(alertMessage) / 2), this.yPosition + this.height / 2 - 4, enabledColor, false);
                return;
            }

            if (s.isEmpty() && !isFocused && isEnabled) {
                this.fontRendererInstance.drawString(noTextMessage, ((this.xPosition + this.width / 2) - fontRendererInstance.getStringWidth(noTextMessage) / 2), this.yPosition + this.height / 2 - 4, i, false);
                return;
            }

            if (s.length() > 0) {
                String s1 = flag ? s.substring(0, j) : s;
                j1 = this.fontRendererInstance.drawString(s1, (float) l, (float) i1, i, false);
            }

            boolean flag2 = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
            int k1 = j1;

            if (!flag) {
                k1 = j > 0 ? l + this.width: l;
            } else if (flag2) {
                k1 = j1 -1; --j1;
            }

            if (s.length() > 0 && flag && j < s.length()) {
                this.fontRendererInstance.drawString(s.substring(j), (float) j1, (float) i1, i, false);
            }

            if (flag1) {
                if (flag2) {
                    Gui.drawRect(k1, i1 -1, k1 + 1, i1 + 1 + this.fontRendererInstance.FONT_HEIGHT, -3092272);
                }
                else {
                    this.fontRendererInstance.drawString("_", (float) k1, (float) i1, i, false);
                }
            }

            if (k != j) {
                int l1 = l + this.fontRendererInstance.getStringWidth(s.substring(0, k));
                this.drawCursorVertical(k1, i1 -1, l1 -1, i1 + 1 + this.fontRendererInstance.FONT_HEIGHT);
            }
        }
    }

    /**
     * draws the vertical line cursor in the textbox
     */
    private void drawCursorVertical(int p_146188_1_, int p_146188_2_, int p_146188_3_, int p_146188_4_) {
        if (p_146188_1_ < p_146188_3_) {
            int i = p_146188_1_;
            p_146188_1_ = p_146188_3_;
            p_146188_3_ = i;
        }

        if (p_146188_2_ < p_146188_4_) {
            int j = p_146188_2_;
            p_146188_2_ = p_146188_4_;
            p_146188_4_ = j;
        }

        if (p_146188_3_ > this.xPosition + this.width) {
            p_146188_3_ = this.xPosition + this.width;
        }

        if (p_146188_1_ > this.xPosition + this.width) {
            p_146188_1_ = this.xPosition + this.width;
        }

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.color(0.0F, 0.0F, 255.0F, 255.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.enableColorLogic();
        GlStateManager.colorLogicOp(5387);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos((double) p_146188_1_, (double) p_146188_4_, 0.0D).endVertex();
        worldrenderer.pos((double) p_146188_3_, (double) p_146188_4_, 0.0D).endVertex();
        worldrenderer.pos((double) p_146188_3_, (double) p_146188_2_, 0.0D).endVertex();
        worldrenderer.pos((double) p_146188_1_, (double) p_146188_2_, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.disableColorLogic();
        GlStateManager.enableTexture2D();
    }

    public void setMaxStringLength(int length) {
        this.maxStringLength = length;

        if (this.text.length() > length) {
            this.text = this.text.substring(0, length);
        }
    }

    /**
     * returns the maximum number of character that can be contained in this textbox
     */
    public int getMaxStringLength() {
        return this.maxStringLength;
    }

    /**
     * returns the current position of the cursor
     */
    public int getCursorPosition() {
        return this.cursorPosition;
    }

    /**
     * get enable drawing background and outline
     */
    public boolean getEnableBackgroundDrawing() {
        return this.enableBackgroundDrawing;
    }

    /**
     * enable drawing background and outline
     */
    public void setEnableBackgroundDrawing(boolean enableBackgroundDrawing) {
        this.enableBackgroundDrawing = enableBackgroundDrawing;
    }

    /**
     * Sets the text colour for this textbox (disabled text will not use this colour)
     */
    public void setTextColor(int color) {
        this.enabledColor = color;
    }

    public void setDisabledTextColour(int color) {
        this.disabledColor = color;
    }

    /**
     * Sets focus to this gui element
     */
    public void setFocused(boolean isFocused) {
        if (isFocused && !this.isFocused) {
            this.cursorCounter = 0;
        }
        this.isFocused = isFocused;
    }

    /**
     * Getter for the focused field
     */
    public boolean isFocused() {
        return this.isFocused;
    }

    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    /**
     * the side of the selection that is not the cursor, may be the same as the cursor
     */
    public int getSelectionEnd() {
        return this.selectionEnd;
    }

    /**
     * returns the width of the textbox depending on if background drawing is enabled
     */
    public int getWidth() {
        return this.getEnableBackgroundDrawing() ? this.width - 8 : this.width;
    }

    /**
     * Sets the position of the selection anchor (i.e. position the selection was started at)
     */
    public void setSelectionPos(int pos) {
        int i = this.text.length();

        if (pos > i) {
            pos = i;
        }
        if (pos < 0) {
            pos = 0;
        }

        this.selectionEnd = pos;

        if (this.fontRendererInstance != null) {
            if (this.lineScrollOffset > i) {
                this.lineScrollOffset = i;
            }

            int j = this.getWidth();
            String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), j);
            int k = s.length() + this.lineScrollOffset;

            if (pos == this.lineScrollOffset) {
                this.lineScrollOffset -= this.fontRendererInstance.trimStringToWidth(this.text, j, true).length();
            }

            if (pos > k) {
                this.lineScrollOffset += pos - k;
            } else if (pos <= this.lineScrollOffset) {
                this.lineScrollOffset -= this.lineScrollOffset - pos;
            }
            this.lineScrollOffset = MathHelper.clamp_int(this.lineScrollOffset, 0, i);
        }
    }

    /**
     * if true the textbox can lose focus by clicking elsewhere on the screen
     */
    public void setCanLoseFocus(boolean canLoseFocus) {
        this.canLoseFocus = canLoseFocus;
    }

    /**
     * returns true if this textbox is visible
     */
    public boolean getVisible() {
        return this.visible;
    }

    /**
     * Sets whether or not this textbox is visible
     */
    public void setVisible(boolean isVisible) {
        this.visible = isVisible;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void alert(String message, int time) {
        if (message.isEmpty()) return;

        running = true;
        alertMessage = message;

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                alertMessage = "";
                running = false;
            }
        }, time);
    }

    public void setOnlyAllowNumbers(boolean onlyAllowNumbers) {
        this.onlyAllowNumbers = onlyAllowNumbers;
    }

    public boolean onlyAllowNumbers() {
        return this.onlyAllowNumbers;
    }

    private String format(String input) {
        if (this.onlyAllowNumbers) {
            StringBuilder builder = new StringBuilder();
            for (char c : input.toCharArray()) {
                if (Character.isDigit(c)) {
                    builder.append(c);
                }
            }
            return builder.toString();
        } else {
            return input;
        }
    }
}
