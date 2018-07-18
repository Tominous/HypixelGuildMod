package me.kevqn.guildmod.listeners;

import net.minecraftforge.client.event.*;
import me.kevqn.guildmod.utils.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ChatListener
{
    private static boolean start;
    
    @SubscribeEvent
    public void onClientChatReceived(final ClientChatReceivedEvent event) {
        final String message = event.message.getFormattedText();
        final ThreadTask task = new ThreadTask();
        for (final String nerd : NerdUtils.getNerds()) {
            if (message.contains(ChatColor.YELLOW + nerd + " joined")) {
                new Timer().schedule(task, 1000L);
            }
        }
    }
    
    static boolean shouldStart() {
        return ChatListener.start;
    }
    
    public static void setShouldStart(final boolean shouldStart) {
        ChatListener.start = shouldStart;
    }
    
    static {
        ChatListener.start = false;
    }
}