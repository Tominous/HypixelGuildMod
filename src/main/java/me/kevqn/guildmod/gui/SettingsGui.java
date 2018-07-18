package me.kevqn.guildmod.gui;

import org.apache.commons.io.FileUtils;

import me.kevqn.guildmod.GuildMod;
import me.kevqn.guildmod.utils.ChatColor;

import java.awt.*;
import java.io.File;
import java.util.Arrays;

public class SettingsGui extends ModernGui {
    
    private final GuildMod mod;
    
    public SettingsGui(GuildMod mod) {
        this.mod = mod;
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(new ModernButton(0, this.width / 2 - 75, this.height / 2 - 82, 150, 20, "AutoGG: " + (GuildMod.isAutoGGEnabled() ? ChatColor.GREEN + "On" : ChatColor.RED + "Off")));
        this.buttonList.add(new ModernButton(1, this.width /2 - 75, this.height / 2 + -58, 150, 20, "AutoTip: " + (GuildMod.isAutoTipEnabled() ? ChatColor.GREEN + "On" : ChatColor.RED + "Off")));
        this.buttonList.add(new ModernButton(2, this.width /2 - 75, this.height / 2 + -34, 150,20, "AdBlock: " + (GuildMod.isAdBlockEnabled() ? ChatColor.GREEN + "On" : ChatColor.RED + "Off")));
        this.buttonList.add(new ModernButton(3, this.width/2 - 75, this.height /2 + -10, 150, 20, "AutoGlHf: " + (GuildMod.isAutoFriendEnabled() ? ChatColor.GREEN + "On" : ChatColor.RED + "Off")));
        this.buttonList.add(new ModernButton(4, this.width/2 - 75, this.height /2 + 15, 150,20, "Play Commands: " + (GuildMod.isPlayCommandsEnabled() ? ChatColor.GREEN + "On" : ChatColor.RED + "Off")));
        this.buttonList.add(new ModernButton(5, this.width/2 - 75, this.height /2 + 40, 150,20, "FriendAlert: " + (GuildMod.isFriendAlertEnabled() ? ChatColor.GREEN + "On" : ChatColor.RED + "Off")));
        this.buttonList.add(new ModernButton(6, this.width/2 - 75, this.height /2 + 65, 150, 20, "Quick Leave: " + (GuildMod.isQuickLeaveEnabled() ? ChatColor.GREEN + "On" : ChatColor.RED + "Off")));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void buttonPressed(ModernButton button) {
        switch (button.id) {
            case 0:
                if(GuildMod.isAutoGGEnabled()) GuildMod.f = false; else GuildMod.f = true;
                button.setText("AutoGG: " + (GuildMod.isAutoGGEnabled() ? ChatColor.GREEN + "On" : ChatColor.RED + "Off"));
                break;
            case 1:
            	if(GuildMod.isAutoTipEnabled()) GuildMod.b = false; else GuildMod.b = true;
                button.setText("AutoTip: " + (GuildMod.isAutoTipEnabled() ? ChatColor.GREEN + "On" : ChatColor.RED + "Off"));
                break;
            case 2:
            	if(GuildMod.isAdBlockEnabled()) GuildMod.e = false; else GuildMod.e = true;
                button.setText("AdBlock: " + (GuildMod.isAdBlockEnabled() ? ChatColor.GREEN + "On" : ChatColor.RED + "Off"));
                break;
            case 3:
            	if(GuildMod.isAutoFriendEnabled()) GuildMod.a = false; else GuildMod.a = true;
                button.setText("AutoGlHf: " + (GuildMod.isAutoFriendEnabled() ? ChatColor.GREEN + "On" : ChatColor.RED + "Off"));
                break;
            case 4:
            	if(GuildMod.isPlayCommandsEnabled()) GuildMod.c = false; else GuildMod.c = true;
                button.setText("Play Commands: " + (GuildMod.isPlayCommandsEnabled() ? ChatColor.GREEN + "On" : ChatColor.RED + "Off"));
                break;
            case 5:
            	if(GuildMod.isFriendAlertEnabled()) GuildMod.h = false; else GuildMod.h = true;
                button.setText("FriendAlert: " + (GuildMod.isFriendAlertEnabled() ? ChatColor.GREEN + "On" : ChatColor.RED + "Off"));
                break;
            case 6:
            	if(GuildMod.isQuickLeaveEnabled()) GuildMod.g = false; else GuildMod.g = true;
            	button.setText("Quick Leave: " + (GuildMod.isQuickLeaveEnabled() ? ChatColor.GREEN + "On" : ChatColor.RED + "Off"));
            	break;
        }
    }
}
