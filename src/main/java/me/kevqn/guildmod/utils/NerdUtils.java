package me.kevqn.guildmod.utils;

import me.kevqn.guildmod.*;
import java.util.*;

public class NerdUtils
{
    public static void setEnabled(boolean flag) {
        flag = GuildMod.isFriendAlertEnabled();
    }
    
    public static List<String> getNerds() {
        final String nerdsString = GuildMod.getConfig().get("Nerds", "Nerds", "").getString().trim();
        final String[] array = nerdsString.split(" ");
        final List<String> nerds = new ArrayList<String>();
        Collections.addAll(nerds, array);
        return nerds;
    }
    
    public static void addNerd(final String nerd) {
        final StringBuilder sb = new StringBuilder();
        for (final String s : getNerds()) {
            sb.append(" ").append(s);
            GuildMod.getConfig().get("Nerds", "Nerds", "").set(sb.toString() + " " + nerd);
        }
        getNerds().add(nerd);
        GuildMod.getConfig().save();
    }
    
    private static String getNerdsString() {
        final StringBuilder sb = new StringBuilder();
        for (final String s : getNerds()) {
            sb.append(" ").append(s);
        }
        return sb.toString().trim();
    }
    
    public static void removeNerd(final String nerd) {
        if (!isNerd(nerd)) {
            return;
        }
        getNerds().remove(nerd);
        GuildMod.getConfig().get("Nerds", "Nerds", "").set(getNerdsString().replace(" " + nerd, ""));
        GuildMod.getConfig().save();
    }
    
    private static boolean isNerd(final String nerd) {
        return getNerds().contains(nerd);
    }
    
    public static void setX(final int x) {
        GuildMod.setX(x);
        GuildMod.getConfig().get("X", "X", 0).set(x);
        GuildMod.getConfig().save();
    }
    
    public static void setY(final int y) {
    	GuildMod.setY(y);
    	GuildMod.getConfig().get("Y", "Y", 0).set(y);
    	GuildMod.getConfig().save();
    }
}
