package me.kevqn.guildmod.listeners;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import me.kevqn.guildmod.GuildMod;
import me.kevqn.guildmod.utils.ChatColor;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class chat {
	private static String unformattedMessage;
	private Random r;
    int d;
    int e;
    
    public chat() {
    	this.r = new Random();
        this.d = 0;
        this.e = 0;
    }
    
    @SubscribeEvent
    public void onChat(final ClientChatReceivedEvent e) {	
    	if(GuildMod.isAutoFriendEnabled()) {
    		final String raw = e.message.getUnformattedText();
    		if(raw.contains("The game starts in 2 seconds!")) GuildMod.sendMsg("/ac gl hf");
    	}
    	if (GuildMod.isQuickLeaveEnabled()) {
    		final String raw = e.message.getUnformattedText();
    		if (raw.contains("Teleporting you to the") && raw.contains("lobby") && raw.contains("Right-click") && raw.contains("cancel")) GuildMod.sendMsg("/l");
    	}
    	if(GuildMod.isAutoGGEnabled()) {
    		final String raw = e.message.getUnformattedText();
    		final String lower = e.message.getUnformattedText().toLowerCase();
    		if (raw.contains("gg") || raw.contains("good game")|| lower.contains("winner")||raw.contains("you died") || raw.contains("Winner")||raw.contains("1st Killer -") || raw.contains("1st Place -")||raw.contains("Winner:") || raw.contains("- Damage Dealt -")||raw.contains("Winning Team -") || raw.contains("1st -")||raw.contains("Winners:") || raw.contains("Winner:")||raw.contains("Winning Team:") || raw.contains(" won the game")||raw.contains("Top Seeker:") || raw.contains("1st Place:")||raw.contains("1st Place") || raw.contains("Last team standing!")||raw.contains("Winner #1 (") || raw.contains("Top survivors") || raw.contains("Winners -")) GuildMod.sendMsg("/ac gg");

    	}	
    	if(GuildMod.isAdBlockEnabled()) {
    		final String raw = e.message.getUnformattedText();
    		if (raw.contains(ChatColor.STRIKETHROUGH + "---------")) e.setCanceled(true);
    		if (raw.contains("game is available to join!") && raw.contains("Tournament") && raw.contains("CLICK HERE to join!")) e.setCanceled(true);
    		if (raw.equals("You can only chat once every 3 seconds! Ranked users bypass this restriction!")) e.setCanceled(true);
    		if (raw.contains("Cages open in:") && raw.contains("seconds")) e.setCanceled(true);
    		if (raw.contains("the game starts in") && raw.contains("seconds")) e.setCanceled(true);
    		if (raw.contains("You are not permitted to") && raw.contains("area")) e.setCanceled(true);
    		if (raw.contains("You can't activate Gadgets") && raw.contains("Gadgets") && raw.contains("area")) e.setCanceled(true);
    		if (raw.contains("You cannot use") && raw.contains("gadgets") && raw.contains("now")) e.setCanceled(true);
    		if (raw.contains("You must wait another") && raw.contains("before using this")) e.setCanceled(true);
    		if (raw.contains("You must wait") && raw.contains("before uses")) e.setCanceled(true);
            if (raw.startsWith("\u00e2\u017e²") && raw.contains("Your game was") && raw.contains("boosted") && raw.contains("coins")) e.setCanceled(true);
            if (raw.startsWith("\u00e2\u017e²") && raw.contains("activated") && raw.contains("coins")) e.setCanceled(true);
            if (raw.endsWith("https://store.hypixel.net") && raw.contains("Buy") && raw.contains("Network") && raw.contains("Boosters")) e.setCanceled(true);
            if (raw.contains("found") && (raw.contains("Mystery") & raw.contains("Box"))) e.setCanceled(true);
            if (raw.contains("joined") && raw.contains("lobby")) e.setCanceled(true);
            if (raw.startsWith(">>") && raw.contains("joined") && raw.contains("lobby")) e.setCanceled(true);
            if (raw.contains("WATCHDOG") && raw.contains("banned")) e.setCanceled(true);
            if (raw.startsWith("Wathcdog") && raw.contains("banned")) e.setCanceled(true);
            if (raw.startsWith("Staff") && raw.contains("banned")) e.setCanceled(true);
            if (raw.startsWith("Blacklisted") && raw.contains("modifications") && raw.contains("bannable")) e.setCanceled(true);
            if (raw.startsWith("Saving") && raw.contains("house")) e.setCanceled(true);
            if (raw.startsWith("Housing") && raw.contains("currently") && raw.contains("BETA")) e.setCanceled(true);
            if (raw.startsWith("If you happen to come across") && raw.contains("bugs") && raw.endsWith("http://www.hypixel.net")) e.setCanceled(true);
            if (raw.endsWith("Soul Well") && raw.contains("found")) e.setCanceled(true);
            if (raw.startsWith("+") && raw.contains("coins")) e.setCanceled(true);
            if (raw.startsWith("+") && raw.contains("Karma")) e.setCanceled(true);
            if (raw.contains("Cages opened") && raw.contains("FIGHT")) e.setCanceled(true);
            if (raw.equals("Select one of your kits by clicking on it")) e.setCanceled(true);
            if (raw.contains("You've selected the") && raw.contains("kit")) e.setCanceled(true);
            if (raw.contains("Teaming is not allowed on") && raw.contains("mode")) e.setCanceled(true);
            if (raw.equals("Cross Teaming / Teaming with other teams is not allowed!")) e.setCanceled(true);
            if (raw.contains("Couldn't connect you to that server, so you were put in the") && raw.contains("lobby")) e.setCanceled(true);
            if (raw.equals("Please don't spam the command")) e.setCanceled(true);
            if (raw.equals("Queued! Use the ed to return to the lobby!")) e.setCanceled(true);
            if (raw.contains("Click to view the stats of your") && raw.contains("game")) e.setCanceled(true);
            if (raw.contains("Sending you to")) e.setCanceled(true);
            if (raw.contains("You were teleported to")) e.setCanceled(true);
            if (raw.contains("This game has already started!")) e.setCanceled(true);
            if (raw.contains("activated") && raw.contains("coins for this game! Gain XP and coins by")) e.setCanceled(true);
            if (raw.contains("Network Boosters") && raw.contains("https;//store.hypixel.net")) e.setCanceled(true);
            if (raw.contains("Rate this map by clicking")) e.setCanceled(true);
            if (raw.equals("Chat is disabled in Ranked Skywars!")) e.setCanceled(true);
            if (raw.contains("Gather resources and equipment on your island")) e.setCanceled(true);
            if (raw.contains("in order to eliminate every other player")) e.setCanceled(true);
            if (raw.contains("with special items")) e.setCanceled(true);
            if (raw.contains("has found") && raw.contains("in the Soul Well")) e.setCanceled(true);
            if (raw.contains("You found") && raw.contains("in the well")) e.setCanceled(true);
            if (raw.equals("You need at least 10 souls to use the soul well!")) e.setCanceled(true);
            if (raw.contains("You cannot say the same message twice")) e.setCanceled(true);
    	}
    }
    
    @SubscribeEvent
    public void b(final TickEvent.ClientTickEvent a) {
        if (GuildMod.isAutoTipEnabled()) {
            ++this.e;
            if (this.e == 6000) {
                this.e = 0;
                GuildMod.sendMsg("/tipall");
                GuildMod.addMsg(ChatColor.GRAY + "[GuildMod] " + ChatColor.GOLD + "Tipped all players!");
            }
        }
    }
}
