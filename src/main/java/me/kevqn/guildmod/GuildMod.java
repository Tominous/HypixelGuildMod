package me.kevqn.guildmod;

import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraft.client.*;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.ICommand;
import net.minecraft.util.*;
import net.minecraft.launchwrapper.*;
import org.apache.commons.lang3.*;
import com.google.common.eventbus.EventBus;
import me.kevqn.guildmod.commands.CommandNameHistory;
import me.kevqn.guildmod.commands.FACommand;
import me.kevqn.guildmod.commands.MainCommand;
import me.kevqn.guildmod.commands.bb;
import me.kevqn.guildmod.commands.bsg;
import me.kevqn.guildmod.commands.bw;
import me.kevqn.guildmod.commands.cg;
import me.kevqn.guildmod.commands.cw;
import me.kevqn.guildmod.commands.mm;
import me.kevqn.guildmod.commands.mw;
import me.kevqn.guildmod.commands.play;
import me.kevqn.guildmod.commands.sw;
import me.kevqn.guildmod.commands.tnt;
import me.kevqn.guildmod.listeners.ChatListener;
import me.kevqn.guildmod.listeners.RenderListener;
import me.kevqn.guildmod.listeners.chat;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.*;
import java.io.*;
import java.util.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;
import net.minecraftforge.common.config.*;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

@Mod(modid = "GM", version = "v0.5", acceptedMinecraftVersions = "[1.8.9]")
public class GuildMod
{	
	private static Configuration config;
	public static int X, Y;
    public static boolean a,b,c,e,f,g,h;
	public static boolean running;
    public static Minecraft m = Minecraft.getMinecraft();
	
    public static void addMsg(final String a) {
        m.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(a));
    }
    
    public static void sendMsg(final String a) {
        m.thePlayer.sendChatMessage(a);
    }
	
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
       (GuildMod.config = new Configuration(new File("config/friend-alert.cfg"))).load();
       	GuildMod.h = getConfig().get("Enabled", "Enabled", true).getBoolean();
       	GuildMod.X = getConfig().get("X", "X", 0).getInt();
       	GuildMod.Y = getConfig().get("Y", "Y", 0).getInt();
       	GuildMod.config.save();
    	ClientCommandHandler.instance.registerCommand(new MainCommand(this));
    	ClientCommandHandler.instance.registerCommand(new CommandNameHistory());
        ClientCommandHandler.instance.registerCommand((ICommand)new bw());
        ClientCommandHandler.instance.registerCommand((ICommand)new sw());
        ClientCommandHandler.instance.registerCommand((ICommand)new bsg());
        ClientCommandHandler.instance.registerCommand((ICommand)new bb());
        ClientCommandHandler.instance.registerCommand((ICommand)new cw());
        ClientCommandHandler.instance.registerCommand((ICommand)new mw());
        ClientCommandHandler.instance.registerCommand((ICommand)new tnt());
        ClientCommandHandler.instance.registerCommand((ICommand)new mm());
        ClientCommandHandler.instance.registerCommand((ICommand)new cg());
        ClientCommandHandler.instance.registerCommand((ICommand)new play());
        ClientCommandHandler.instance.registerCommand((ICommand)new FACommand());
        MinecraftForge.EVENT_BUS.register((Object)new chat());
        MinecraftForge.EVENT_BUS.register((Object)new ChatListener());
        MinecraftForge.EVENT_BUS.register((Object)new RenderListener());
        final Configuration Config = new Configuration(new File("config/ProPixel.cfg"));
        Config.load();
        GuildMod.a = Config.get("settings", "AutoGlHf Enabled", true).getBoolean();
        GuildMod.b = Config.get("settings", "AutoTip Enabled", true).getBoolean();
        GuildMod.c = Config.get("settings", "PlayCommands Enabled", true).getBoolean();
        GuildMod.h = Config.get("settings", "FriendAlert Enabled", true).getBoolean();
        GuildMod.e = Config.get("settings", "AdBlock Enabled", true).getBoolean();
        GuildMod.f = Config.get("settings", "AutoGG Enabled", true).getBoolean();
        GuildMod.g = Config.get("settings", "QuickLeave Enabled", true).getBoolean();
        Config.save();
    }
    
    public static Configuration getConfig() {
        return GuildMod.config;
    }
    
    public static void setX(final int x) {
    	GuildMod.X = x;
    }
    
    public static void setY(final int y) {
    	GuildMod.Y = y;
    }
    
    public static int getX() {
    	return GuildMod.X;
    }
    
    public static int getY() {
    	return GuildMod.Y;
    }
    
    public static boolean isFriendAlertEnabled() {
    	return GuildMod.h;
    }
    
    public static boolean isQuickLeaveEnabled() {
    	return GuildMod.g;
    }

    public static boolean isAutoGGEnabled() {
    	return GuildMod.f;
    }
    
    public static boolean isAdBlockEnabled() {
    	return GuildMod.e;
    }
    
    public static boolean isPlayCommandsEnabled()  {
    	return GuildMod.c;
    }
    
    public static boolean isAutoFriendEnabled() {
        return GuildMod.a;
    }
    
    public static boolean isAutoTipEnabled() {
        return GuildMod.b;
    }
    
    static {
    	GuildMod.X = 0;
    	GuildMod.Y = 0;
    }
}
