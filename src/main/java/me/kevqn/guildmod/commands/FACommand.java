package me.kevqn.guildmod.commands;

import me.kevqn.guildmod.*;
import me.kevqn.guildmod.utils.*;
import java.util.*;
import net.minecraft.command.*;
import net.minecraft.util.*;

public class FACommand implements ICommand
{
    private final ThreadTask task;
    
    public FACommand() {
        this.task = new ThreadTask();
    }
    
    public String getCommandName() {
        return "friendalert";
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "/fa <add | remove | list > <name>";
    }
    
    public List<String> getCommandAliases() {
        final List<String> aliases = new ArrayList<String>();
        aliases.add("fa");
        return aliases;
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        switch (args.length) {
            case 0: {
                break;
            }
            case 1: {
                if (args[0].equalsIgnoreCase("list")) {
                    GuildMod.addMsg(Reference.PREFIX + ChatColor.BLUE + "Current Friends:");
                    if (NerdUtils.getNerds().size() != 0) {
                        for (final String nerd : NerdUtils.getNerds()) {
                            GuildMod.addMsg(ChatColor.GRAY + "- " + ChatColor.GREEN + nerd);
                        }
                    }
                    else {
                        GuildMod.addMsg(ChatColor.RED + "None! Add some with " + ChatColor.YELLOW + "/fa add <name>");
                    }
                }
                break;
            }
            case 2: {
                if (args[0].equalsIgnoreCase("add")) {
                    NerdUtils.addNerd(args[1]);
                    GuildMod.addMsg(Reference.PREFIX + ChatColor.BLUE + args[1] + ChatColor.GREEN + " has been added!");
                }
                if (args[0].equalsIgnoreCase("remove")) {
                    NerdUtils.removeNerd(args[1]);
                    GuildMod.addMsg(Reference.PREFIX + ChatColor.BLUE + args[1] + ChatColor.RED + " is no longer a friend");
                }
                break;
            }
        }
    }
    
    public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return true;
    }
    
    public List<String> addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
        final List<String> autocomplete = new ArrayList<String>();
        autocomplete.add("fa");
        return autocomplete;
    }
    
    public boolean isUsernameIndex(final String[] args, final int index) {
        return true;
    }
    
    public int compareTo(final ICommand o) {
        return 0;
    }
}