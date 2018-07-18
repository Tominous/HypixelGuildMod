package me.kevqn.guildmod.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class PlayerNames {
    private static ArrayList<String> values;
    private static NumberedHashMap<String, String> nameHistory;
    private static int startPos;
    private static Minecraft mc;
    
    public static void startFetchingHistory(final String playerName, final boolean isFacing) {
        final Thread historyFetcher = new Thread() {
            @Override
            public void run() {
                if (isFacing) {
                    if (PlayerFacing.isFacingPlayer()) {
                        final String name = PlayerFacing.getFacingPlayerName();
                        final NumberedHashMap<String, String> history = fetchNameHistory(name);
                        PlayerNames.displayPlayerNameHistory(history);
                    }
                }
                else {
                    PlayerNames.displayPlayerNameHistory(fetchNameHistory(playerName));
                }
            }
        };
        historyFetcher.start();
    }
    
    public static void displayPlayerNameHistory(final NumberedHashMap history) {
        if (history == null || history.isEmpty()) {
            return;
        }
        PlayerNames.mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(""));
        PlayerNames.mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GOLD + "" + history.getKey(history.size() - 1) + "'s " + EnumChatFormatting.GREEN + "previous name history:"));
        PlayerNames.mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.AQUA + "----------------------------------------"));
        if (history.size() > 1) {
            for (int i = 0; i < history.size(); ++i) {
                PlayerNames.mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GOLD + "" + history.getKey(i) + " : " + EnumChatFormatting.GREEN + history.getValue(i)));
            }
        }
        else {
            PlayerNames.mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GOLD + "" + history.getKey(history.size() - 1) + EnumChatFormatting.GREEN + " has not changed their name."));
        }
        PlayerNames.mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.AQUA + "----------------------------------------"));
        PlayerNames.mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(""));
    }
    
    private static NumberedHashMap<String, String> fetchNameHistory(final String playerName) {
        if (playerName.equals(null)) {
            return null;
        }
        PlayerNames.values.clear();
        PlayerNames.nameHistory.clear();
        PlayerNames.startPos = 0;
        try {
            final URL uuidFetcher = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName);
            final InputStreamReader uuidReader = new InputStreamReader(uuidFetcher.openStream());
            final BufferedReader getUuid = new BufferedReader(uuidReader);
            final String testUUID = getUuid.readLine();
            String uuid = null;
            if (testUUID != null) {
                uuid = testUUID.substring(7, 39);
            }
            getUuid.close();
            if (uuid != null) {
                final URL nameFetcher = new URL("https://api.mojang.com/user/profiles/" + uuid + "/names");
                final InputStreamReader nameReader = new InputStreamReader(nameFetcher.openStream());
                final BufferedReader getName = new BufferedReader(nameReader);
                String names = getName.readLine();
                getName.close();
                names = names.replace("[", "").replace("{", "").replace("}", "").replace("\"", "");
                getOriginalName(names);
                if (names.charAt(PlayerNames.startPos) == ']') {
                    return PlayerNames.nameHistory;
                }
                while (names.charAt(PlayerNames.startPos) != ']') {
                    names = names.substring(PlayerNames.startPos + 1);
                    PlayerNames.values.add(splitValues(names));
                }
                while (!PlayerNames.values.isEmpty()) {
                    final Date changeDate = new Date(Long.valueOf(PlayerNames.values.get(1)));
                    PlayerNames.nameHistory.put(PlayerNames.values.get(0), changeDate.toString());
                    PlayerNames.values.remove(0);
                    PlayerNames.values.remove(0);
                }
                return PlayerNames.nameHistory;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private static void getOriginalName(final String input) {
        for (int i = 0; i < input.length(); ++i) {
            if (input.charAt(i) == ':') {
                PlayerNames.startPos = i + 1;
            }
            if (input.charAt(i) == ']' || input.charAt(i) == ',') {
                PlayerNames.nameHistory.put(input.substring(PlayerNames.startPos, i), "Original");
                PlayerNames.startPos = i;
                break;
            }
        }
    }
    
    private static String splitValues(final String input) {
        for (int i = 0; i < input.length(); ++i) {
            if (input.charAt(i) == ':') {
                PlayerNames.startPos = i + 1;
            }
            if (input.charAt(i) == ']' || input.charAt(i) == ',') {
                final String value = input.substring(PlayerNames.startPos, i);
                PlayerNames.startPos = i;
                return value;
            }
        }
        return null;
    }
    
    static {
        PlayerNames.values = new ArrayList<String>();
        PlayerNames.nameHistory = new NumberedHashMap<String, String>();
        PlayerNames.startPos = 0;
        PlayerNames.mc = Minecraft.getMinecraft();
    }
}
