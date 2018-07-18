package me.kevqn.guildmod.utils;

import net.minecraft.client.*;
import net.minecraft.util.*;

public class CmdHandler
{
    static String mode;
    static String lobby;
    
    public static void invalidArgs(final String cmd) {
        Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText("Invalid arguments! Correct usage: /" + cmd + " [mode]"));
    }
    
    public static void cmd(final String cmd, final String[] args) {
        if (cmd == "play") {
            if (args[0].equalsIgnoreCase("bw_solo")) {
                CmdHandler.mode = "bedwars_eight_one";
            }
            if (args[0].equalsIgnoreCase("bw_doubles")) {
                CmdHandler.mode = "bedwars_eight_two";
            }
            if (args[0].equalsIgnoreCase("bw_three")) {
                CmdHandler.mode = "bedwars_four_three";
            }
            if (args[0].equalsIgnoreCase("bw_four")) {
                CmdHandler.mode = "bedwars_four_four";
            }
            if (args[0].equalsIgnoreCase("sw_solo")) {
                CmdHandler.mode = "solo_normal";
            }
            if (args[0].equalsIgnoreCase("sw_teams")) {
                CmdHandler.mode = "teams_normal";
            }
            if (args[0].equalsIgnoreCase("sw_solo_insane")) {
                CmdHandler.mode = "solo_insane";
            }
            if (args[0].equalsIgnoreCase("sw_teams_insane")) {
                CmdHandler.mode = "teams_insane";
            }
            if (args[0].equalsIgnoreCase("sw_solo_ranked")) {
                CmdHandler.mode = "ranked_normal";
            }
            if (args[0].equalsIgnoreCase("bsg_solo")) {
                CmdHandler.mode = "blitz_solo_normal";
            }
            if (args[0].equalsIgnoreCase("bsg_teams")) {
                CmdHandler.mode = "blitz_teams_normal";
            }
            if (args[0].equalsIgnoreCase("bsg_solo_nokits")) {
                CmdHandler.mode = "blitz_solo_nokits";
            }
            if (args[0].equalsIgnoreCase("bb_solo")) {
                CmdHandler.mode = "build_battle_solo_normal";
            }
            if (args[0].equalsIgnoreCase("bb_teams")) {
                CmdHandler.mode = "build_battle_teams_normal";
            }
            if (args[0].equalsIgnoreCase("bb_solo_pro")) {
                CmdHandler.mode = "build_battle_solo_pro";
            }
            if (args[0].equalsIgnoreCase("bb_solo_guess")) {
                CmdHandler.mode = "build_battle_guess_the_build";
            }
            if (args[0].equalsIgnoreCase("cw_solo")) {
                CmdHandler.mode = "crazy_walls_solo";
            }
            if (args[0].equalsIgnoreCase("cw_teams")) {
                CmdHandler.mode = "crazy_walls_team";
            }
            if (args[0].equalsIgnoreCase("cw_solo_lucky")) {
                CmdHandler.mode = "crazy_walls_solo_chaos";
            }
            if (args[0].equalsIgnoreCase("cw_teams_lucky")) {
                CmdHandler.mode = "crazy_walls_team_chaos";
            }
            if (args[0].equalsIgnoreCase("mw_standard")) {
                CmdHandler.mode = "mw_standard";
            }
            if (args[0].equalsIgnoreCase("mw_faceoff")) {
                CmdHandler.mode = "mw_faceoff";
            }
            if (args[0].equalsIgnoreCase("tnt_tntrun")) {
                CmdHandler.mode = "tnt_tntrun";
            }
            if (args[0].equalsIgnoreCase("tnt_pvprun")) {
                CmdHandler.mode = "tnt_pvprun";
            }
            if (args[0].equalsIgnoreCase("tnt_bowspleef")) {
                CmdHandler.mode = "tnt_bowspleef";
            }
            if (args[0].equalsIgnoreCase("tnt_tntag")) {
                CmdHandler.mode = "tnt_tntag";
            }
            if (args[0].equalsIgnoreCase("tnt_tntwizards")) {
                CmdHandler.mode = "tnt_capture";
            }
            if (args[0].equalsIgnoreCase("mm")) {
                CmdHandler.mode = "murder_classic";
            }
            if (args[0].equalsIgnoreCase("mm_assassins")) {
                CmdHandler.mode = "murder_assassins";
            }
            if (args[0].equalsIgnoreCase("mm_showdown")) {
                CmdHandler.mode = "murder_showdown";
            }
            if (args[0].equalsIgnoreCase("mm_infection")) {
                CmdHandler.mode = "murder_infection";
            }
        }
        if (cmd == "bw") {
            if (args[0].equalsIgnoreCase("1")) {
                CmdHandler.mode = "bedwars_eight_one";
            }
            if (args[0].equalsIgnoreCase("2")) {
                CmdHandler.mode = "bedwars_eight_two";
            }
            if (args[0].equalsIgnoreCase("3")) {
                CmdHandler.mode = "bedwars_four_three";
            }
            if (args[0].equalsIgnoreCase("4")) {
                CmdHandler.mode = "bedwars_four_four";
            }
        }
        if (cmd == "sw") {
            if (args[0].equalsIgnoreCase("s")) {
                CmdHandler.mode = "solo_normal";
            }
            if (args[0].equalsIgnoreCase("t")) {
                CmdHandler.mode = "teams_normal";
            }
            if (args[0].equalsIgnoreCase("si")) {
                CmdHandler.mode = "solo_insane";
            }
            if (args[0].equalsIgnoreCase("ti")) {
                CmdHandler.mode = "teams_insane";
            }
            if (args[0].equalsIgnoreCase("r")) {
                CmdHandler.mode = "ranked_normal";
            }
            if (args[0].equalsIgnoreCase("m")) {
                CmdHandler.mode = "mega_normal";
            }
            if (args[0].equalsIgnoreCase("md")) {
                CmdHandler.mode = "mega_doubles";
            }
        }
        if (cmd == "bsg") {
            if (args[0].equalsIgnoreCase("s")) {
                CmdHandler.mode = "blitz_solo_normal";
            }
            if (args[0].equalsIgnoreCase("snk")) {
                CmdHandler.mode = "blitz_solo_nokits";
            }
            if (args[0].equalsIgnoreCase("t")) {
                CmdHandler.mode = "blitz_teams_normal";
            }
        }
        if (cmd == "bb") {
            if (args[0].equalsIgnoreCase("s")) {
                CmdHandler.mode = "build_battle_solo_normal";
            }
            if (args[0].equalsIgnoreCase("t")) {
                CmdHandler.mode = "build_battle_teams_normal";
            }
            if (args[0].equalsIgnoreCase("p")) {
                CmdHandler.mode = "build_battle_solo_pro";
            }
            if (args[0].equalsIgnoreCase("g")) {
                CmdHandler.mode = "build_battle_guess_the_build";
            }
        }
        if (cmd == "cw") {
            if (args[0].equalsIgnoreCase("s")) {
                CmdHandler.mode = "crazy_walls_solo";
            }
            if (args[0].equalsIgnoreCase("t")) {
                CmdHandler.mode = "crazy_walls_team";
            }
            if (args[0].equalsIgnoreCase("sl")) {
                CmdHandler.mode = "crazy_walls_solo_chaos";
            }
            if (args[0].equalsIgnoreCase("tl")) {
                CmdHandler.mode = "crazy_walls_team_chaos";
            }
        }
        if (cmd == "mw") {
            if (args[0].equalsIgnoreCase("s")) {
                CmdHandler.mode = "mw_standard";
            }
            if (args[0].equalsIgnoreCase("f")) {
                CmdHandler.mode = "mw_faceoff";
            }
        }
        if (cmd == "tnt") {
            if (args[0].equalsIgnoreCase("tr")) {
                CmdHandler.mode = "tnt_tntrun";
            }
            if (args[0].equalsIgnoreCase("pr")) {
                CmdHandler.mode = "tnt_pvprun";
            }
            if (args[0].equalsIgnoreCase("bs")) {
                CmdHandler.mode = "tnt_bowspleef";
            }
            if (args[0].equalsIgnoreCase("tt")) {
                CmdHandler.mode = "tnt_tntag";
            }
            if (args[0].equalsIgnoreCase("tw")) {
                CmdHandler.mode = "tnt_capture";
            }
        }
        if (cmd == "mm") {
            if (args[0].equalsIgnoreCase("n")) {
                CmdHandler.mode = "murder_classic";
            }
            if (args[0].equalsIgnoreCase("a")) {
                CmdHandler.mode = "murder_assassins";
            }
            if (args[0].equalsIgnoreCase("s")) {
                CmdHandler.mode = "murder_showdown";
            }
            if (args[0].equalsIgnoreCase("i")) {
                CmdHandler.mode = "murder_infection";
            }
        }
        if (args.length > -1 & args.length < 2) {
            if (args.length == 1) {
                if (CmdHandler.mode != "") {
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/play " + CmdHandler.mode);
                }
                else {
                    invalidArgs(cmd);
                }
            }
            else if (CmdHandler.lobby != "") {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/l " + CmdHandler.lobby);
            }
            else if (cmd == "play") {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play " + args[0]);
            }
            else {
                invalidArgs(cmd);
            }
        }
        else {
            invalidArgs(cmd);
        }
    }
    
    static {
        CmdHandler.mode = "";
        CmdHandler.lobby = "";
    }
}
